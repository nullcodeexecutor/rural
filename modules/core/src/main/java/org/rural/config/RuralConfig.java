package org.rural.config;

/**
 * Created by yuantao on 2015/3/16.
 */
public interface RuralConfig {
    String CHARSET = "charset";
    String TEMPLATE = "template";
    String PAGE_LOCATION = "pageLocation";
    String CONTROLLER_LOCATION = "controllerLocation";
    String INTERCEPTOR_LOCATION = "interceptorLocation";
    String RESOURCES_LOCATION = "resourcesLocation";

    void setContextConfig(ContextConfigHolder contextConfigHolder);

    void setInterceptorConfig(InterceptorConfigHolder interceptorConfigHolder);

}
