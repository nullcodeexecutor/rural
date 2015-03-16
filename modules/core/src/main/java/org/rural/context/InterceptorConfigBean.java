package org.rural.context;

import org.rural.config.InterceptorConfigHolder;
import org.rural.interceptor.Interceptor;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuantao on 2015/3/16.
 */
public class InterceptorConfigBean {
    private List<InterceptorConfigHolder.InterceptorConfig> interceptorConfigs;

    public InterceptorConfigBean(List<InterceptorConfigHolder.InterceptorConfig> interceptorConfigs) {
        this.interceptorConfigs = interceptorConfigs;
    }

    public List<Interceptor> getInterceptors(String path, AntPathMatcher matcher) {
        List<Interceptor> interceptors = new ArrayList<Interceptor>();
        for (InterceptorConfigHolder.InterceptorConfig interceptorConfig : interceptorConfigs) {
            if (matcher.match(interceptorConfig.getPath(), path)) {
                interceptors.add(SpringContainer.getBean(interceptorConfig.getClazz()));
            }
        }
        return interceptors;
    }


}
