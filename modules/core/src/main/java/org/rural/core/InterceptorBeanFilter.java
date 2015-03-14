package org.rural.core;

import org.rural.interceptor.Interceptor;

/**
 * Created by yuantao on 2014/8/9.
 * 默认的Interceptor的过滤器
 */
public class InterceptorBeanFilter implements BeanFilter {

    @Override
    public boolean filter(Object bean) {
        Class<? super Object> clazz = (Class<? super Object>) bean.getClass().getSuperclass();
        if(clazz.equals(Interceptor.class)){
            return true;
        }
        return false;
    }

}
