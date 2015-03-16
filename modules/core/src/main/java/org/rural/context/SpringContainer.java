package org.rural.context;

import org.rural.config.ContextConfigHolder;
import org.rural.config.InterceptorConfigHolder;
import org.rural.config.RuralConfig;
import org.rural.core.BeanFilter;
import org.rural.exception.RuralException;
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

    static RuralConfig getRuralConfig() {
        RuralConfig config =  SPRING_CONTEXT.getBean(RuralConfig.class);
        if (null == config) {
            throw new RuralException("Not found RuralConfig");
        }
        return config;
    }

    static <T> T getBean(Class<T> clazz) {
        return SPRING_CONTEXT.getBean(clazz);
    }

}
