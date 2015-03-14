package org.rural.context;

import org.rural.core.BeanFilter;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuantao on 2014/8/7.
 */
public class SpringContainer {
    public static ApplicationContext SPRING_CONTEXT;

    /**
     * 返回rural所关心的bean。比如controller,interceptor等
     */
    static List<Object> getRuralBean(BeanFilter beanFilter){
        List<Object> beans = new ArrayList<Object>();
        String[] beanNames = SPRING_CONTEXT.getBeanDefinitionNames();
        for(String beanName : beanNames){
            Object bean = SPRING_CONTEXT.getBean(beanName);
            if(beanFilter.filter(bean)){
                beans.add(bean);
            }
        }
        return beans;
    }

    static RurlConfig getRurlConfig(){
        return SPRING_CONTEXT.getBean(RurlConfig.class);
    }

}
