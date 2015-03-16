package org.rural.config;

import org.rural.context.RuralConfigBean;
import org.rural.context.SpringContainer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by yuantao on 2015/3/15.
 */
public class DefaultRuralConfig implements ApplicationContextAware, RuralConfig {

    private RuralConfigBean ruralConfig;

    @Override
    public void setContextConfig(ContextConfigHolder contextConfigHolder) {
        contextConfigHolder.add(CHARSET, "UTF-8");
        contextConfigHolder.add(TEMPLATE, "fm");
        contextConfigHolder.add(PAGE_LOCATION, "/WEB-INF/page/");
        contextConfigHolder.add(CONTROLLER_LOCATION, "");
        contextConfigHolder.add(INTERCEPTOR_LOCATION, "");
        contextConfigHolder.add(RESOURCES_LOCATION, "/resources");
    }

    @Override
    public void setInterceptorConfig(InterceptorConfigHolder interceptorConfigHolder) {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContainer.SPRING_CONTEXT = applicationContext;
    }

    public RuralConfigBean getRurlConfig(){
        return ruralConfig;
    }
}
