package org.rural.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rural.config.ContextConfigHolder;
import org.rural.config.InterceptorConfigHolder;
import org.rural.config.RuralConfig;
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
    private RuralConfigBean ruralConfigBean = null;
    private InterceptorConfigBean interceptorConfigBean = null;
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

        RuralConfig ruralConfig = SpringContainer.getRuralConfig();
        initConfig(ruralConfig) ;

        initCache(controllers);
    }

    private void initConfig(RuralConfig ruralConfig) {
        ContextConfigHolder contextConfigHolder = new ContextConfigHolder();
        InterceptorConfigHolder interceptorConfigHolder = new InterceptorConfigHolder();
        ruralConfig.setContextConfig(contextConfigHolder);
        ruralConfig.setInterceptorConfig(interceptorConfigHolder);

        buildRuralConfigBean(contextConfigHolder);
        buildInterceptorConfig(interceptorConfigHolder);
    }

    private void buildInterceptorConfig(InterceptorConfigHolder interceptorConfigHolder) {
        this.interceptorConfigBean = new InterceptorConfigBean(interceptorConfigHolder.getInterceptorConfigs());
    }

    private void buildRuralConfigBean(ContextConfigHolder contextConfigHolder) {
        this.ruralConfigBean = new RuralConfigBean();
        this.ruralConfigBean.setCharset(contextConfigHolder.get(RuralConfig.CHARSET).toString());
        this.ruralConfigBean.setTemplate(contextConfigHolder.get(RuralConfig.TEMPLATE).toString());
        this.ruralConfigBean.setPageLocation(contextConfigHolder.get(RuralConfig.PAGE_LOCATION).toString());
        this.ruralConfigBean.setControllerLocation(contextConfigHolder.get(RuralConfig.CONTROLLER_LOCATION).toString());
        this.ruralConfigBean.setResourcesLocation(contextConfigHolder.get(RuralConfig.RESOURCES_LOCATION).toString());
    }

    private void initCache(List<Object> controllers){
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
                action.setInterceptors(this.interceptorConfigBean.getInterceptors(resource, matcher));
                mappingCache.put(resource, action);
                log.info(resource);
            }
        }
    }

    public Action getAction(String resource){
        return mappingCache.get(resource);
    }

    public RuralConfigBean getRuralConfigBean(){
        return ruralConfigBean;
    }

    public InterceptorConfigBean getInterceptorConfigBean(){
        return interceptorConfigBean;
    }

}
