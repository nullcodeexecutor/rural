package org.rural.core;

/**
 * Created by yuantao on 2014/8/9.
 * 过滤spring管理的bean，过滤后的bean交给rural管理。
 */
public interface BeanFilter {
    boolean filter(Object bean);
}
