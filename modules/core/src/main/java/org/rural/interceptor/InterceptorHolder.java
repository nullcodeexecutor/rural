package org.rural.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuantao on 2015/3/15.
 */
public class InterceptorHolder {
    private Map<String, Interceptor> interceptors;

    public InterceptorHolder() {
        interceptors = new HashMap<String, Interceptor>();
    }

}
