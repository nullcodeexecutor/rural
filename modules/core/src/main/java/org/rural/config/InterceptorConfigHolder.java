package org.rural.config;

import org.rural.interceptor.Interceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuantao on 2015/3/15.
 */
public class InterceptorConfigHolder {
    private Map<String, Class<Interceptor>> interceptorConfigs;

    public InterceptorConfigHolder() {
        this.interceptorConfigs = new HashMap<String, Class<Interceptor>>();
    }

    public void addConfig(String path, Class<Interceptor> clazz) {
    }

}
