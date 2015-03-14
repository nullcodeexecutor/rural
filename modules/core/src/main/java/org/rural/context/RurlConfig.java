package org.rural.context;

import org.rural.context.SpringContainer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.UnsupportedEncodingException;

/**
 * Created by yuantao on 2014/8/11.
 */
public class RurlConfig implements ApplicationContextAware {
    private String charset;
    private String template;
    private String pageLocation;
    private String controllerLocation;
    private String interceptorLocation;
    private String resourcesLocation;

    public String getResourcesLocation() {
        return resourcesLocation;
    }

    public void setResourcesLocation(String resourcesLocation) {
        if (null == this.resourcesLocation) {
            this.resourcesLocation = "/resources";
        }
        this.resourcesLocation = resourcesLocation;
    }

    public String getCharset() {
        if (null == charset) {
            charset = "UTF-8";
        }
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getTemplate() {
        if (null == template) {
            template = "fm";
        }
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getPageLocation() {
        return pageLocation;
    }

    public void setPageLocation(String pageLocation) {
        this.pageLocation = pageLocation;
    }

    public String getControllerLocation() {
        return controllerLocation;
    }

    public void setControllerLocation(String controllerLocation) {
        this.controllerLocation = controllerLocation;
    }

    public String getInterceptorLocation() {
        if (null == interceptorLocation) {
            interceptorLocation = "/WEB-INF/page/";
        }
        return interceptorLocation;
    }

    public void setInterceptorLocation(String interceptorLocation) {
        this.interceptorLocation = interceptorLocation;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContainer.SPRING_CONTEXT = applicationContext;
    }

}
