package org.rural.interceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yuantao on 2014/8/12.
 */
public abstract class Interceptor {

    protected boolean invoke(InterceptorChain chain, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(!before(request, response)){
            return false;
        }
        if(!chain.invoke()){
            return false;
        }
        if(!after(request, response)){
            return false;
        }
        return true;
    }

    /**
     * 前置拦截器，以此构建拦截器链
     */
    public abstract int order();

    /**
     * @return 该拦截器所拦截的目标资源
     */
    public abstract String pattern();

    public abstract boolean before(HttpServletRequest request, HttpServletResponse response);

    public abstract boolean after(HttpServletRequest request, HttpServletResponse response);

}
