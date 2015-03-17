package org.coder.config;

import org.coder.interceptor.UserFindInterceptor;
import org.coder.interceptor.UserInterceptor;
import org.coder.interceptor.UserShowOneInterceptor;
import org.rural.config.ContextConfigHolder;
import org.rural.config.DefaultRuralConfig;
import org.rural.config.InterceptorConfigHolder;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by yuantao on 2015/3/16.
 */
@Component
public class RuralConfig extends DefaultRuralConfig {
    @Override
    public void setInterceptorConfig(InterceptorConfigHolder interceptorConfigHolder) {
        super.setInterceptorConfig(interceptorConfigHolder);
        interceptorConfigHolder.add("/user/findOne", UserFindInterceptor.class);
        interceptorConfigHolder.add("/user/showOne", UserShowOneInterceptor.class);
        interceptorConfigHolder.add("/user/showOne", UserInterceptor.class);
        interceptorConfigHolder.add("/user/*", UserInterceptor.class);
    }

    @Override
    public void setContextConfig(ContextConfigHolder contextConfigHolder) {
        super.setContextConfig(contextConfigHolder);
        contextConfigHolder.add(TEMPLATE, "fm");
    }
}
