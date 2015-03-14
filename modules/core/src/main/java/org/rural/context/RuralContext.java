package org.rural.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rural.core.*;
import org.rural.exception.RuralException;
import org.rural.interceptor.Interceptor;
import org.rural.utils.ReflectUtil;
import org.rural.utils.StringUtil;
import org.springframework.util.AntPathMatcher;

import javax.servlet.ServletContext;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by yuantao on 2014/8/7.
 */
public class RuralContext {
    private static final Log log = LogFactory.getLog(RuralContext.class);
    private static RuralContext me = null;
    private RurlConfig rurlConfig = null;
    private Map<String, Action> mappingCache = null;
    private String controllerSuffix = "Controller";

    public synchronized  static RuralContext initRuralContext(ServletContext servletContext){
        if(null != me){
            throw new RuralException("Rural has been initialized");
        }
        if(null == me){
            me = new RuralContext(servletContext);
        }
        return me;
    }


    public static RuralContext context() {
        if(null == me){
            throw new RuralException("Rural has not been initialized");
        }
        return me;
    }

    private RuralContext(ServletContext servletContext){
        List<Object> controllers = SpringContainer.getRuralBean(new ControllerBeanFilter());
        List<Object> interceptors = SpringContainer.getRuralBean(new InterceptorBeanFilter());
        initCache(controllers, interceptorOrder(interceptors));
    }

    private void initCache(List<Object> controllers, List<Interceptor> interceptors){
        if(null == mappingCache){
            mappingCache = new HashMap<String, Action>();
        }
        AntPathMatcher matcher = new AntPathMatcher();
        for(Object bean : controllers){
            Class<?> clazz = bean.getClass();
            List<Method> methods = ReflectUtil.getPublicMethods(clazz);
            String mapping = StringUtil.firstCharToLowerCase(StringUtil.cutSuffix(clazz.getSimpleName(), this.controllerSuffix));
            log.info(clazz.getName());
            for(Method method : methods){
                String returnType = method.getReturnType().getName();
                if(!(void.class.getName().equals(returnType) || String.class.getName().equals(returnType))){
                    continue;
                }
                String resource = "/"+mapping+"/"+method.getName();
                String[] argNames = ReflectUtil.getMethodArgNames(clazz.getName(), method.getName());
                Action action = new Action(bean, method, method.getParameterTypes(), argNames, resource);
                action.setInterceptors(getInterceptors(resource, interceptors, matcher));
                mappingCache.put(resource, action);
                log.info(resource);
            }
        }
    }

    private List<Interceptor> interceptorOrder(List<Object> interceptors){
        List<Interceptor> interceptorList = new ArrayList<Interceptor>();
        for (Object obj : interceptors) {
            if (obj instanceof Interceptor) {
                Interceptor interceptor = (Interceptor)obj;
                log.info(interceptor.getClass().getName() + ": " + interceptor.pattern());
                interceptorList.add(interceptor);
            }
        }
        //按照order升序排序
        Collections.sort(interceptorList, new Comparator<Interceptor>() {
            @Override
            public int compare(Interceptor interceptor1, Interceptor interceptor2) {
                return interceptor1.order() - interceptor2.order();
            }
        });
        return interceptorList;
    }

    private List<Interceptor> getInterceptors(String resource, List<Interceptor> interceptors, AntPathMatcher matcher){
        List<Interceptor> list = new ArrayList<Interceptor>();
        for (Interceptor interceptor : interceptors) {
            if (matcher.match(interceptor.pattern(), resource)) {
                list.add(interceptor);
            }
        }
        return list;
    }

    public Action getAction(String resource){
        return mappingCache.get(resource);
    }

    public RurlConfig getRurlConfig(){
        if(null == rurlConfig){
            rurlConfig = SpringContainer.getRurlConfig();
        }
        return rurlConfig;
    }

}
