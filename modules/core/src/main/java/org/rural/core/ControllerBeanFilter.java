package org.rural.core;

/**
 * Created by yuantao on 2014/8/9.
 * 默认的过滤controller的实现
 */
public class ControllerBeanFilter implements BeanFilter {
    @Override
    public boolean filter(Object bean) {
        String name = bean.getClass().getName();
        if(name.endsWith("Controller")){
            return true;
        }
        return false;
    }
}
