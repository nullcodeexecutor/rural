package org.rural.config;

import org.rural.context.RuralConfigBean;
import org.rural.context.SpringContainer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by yuantao on 2015/3/15.
 */
public class DefaultRuralConfig implements ApplicationContextAware {
    private RuralConfigBean ruralConfig;

    public void setContextConfig(ContextConfigHolder contextConfigHolder) {
    }

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
