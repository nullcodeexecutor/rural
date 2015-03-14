package org.rural.core;

import org.rural.exception.RuralException;
import org.rural.interceptor.Interceptor;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by yuantao on 2014/8/23.
 */
public final class Action {
    private Object instance;
    private Method method;
    //controller中方法的参数类型
    private Class<?>[] argTypes;
    //controller中方法的参数名
    private String[] argNames;

    private Object[] argValues;

    private String resource;

    private List<Interceptor> interceptors;

    public Action(Object instance, Method method, Class<?>[] argTypes, String[] argNames, String resource) {
        this.instance = instance;
        this.method = method;
        this.argTypes = argTypes;
        this.argNames = argNames;
        this.resource = resource;
        if(this.argNames.length != this.argTypes.length){
            throw new RuralException("args exception:"+instance.getClass().getName()+"."+this.method.getName());
        }
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?>[] getArgTypes() {
        return argTypes;
    }

    public void setArgTypes(Class<?>[] argTypes) {
        this.argTypes = argTypes;
    }

    public String[] getArgNames() {
        return argNames;
    }

    public void setArgNames(String[] argNames) {
        this.argNames = argNames;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof  Action)){
            return false;
        }
        Action o = (Action)obj;
        return this.resource.equals(o.getResource());
    }

    @Override
    public int hashCode(){
        return this.resource.hashCode();
    }

}
