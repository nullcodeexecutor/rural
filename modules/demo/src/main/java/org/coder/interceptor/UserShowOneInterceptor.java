package org.coder.interceptor;

import org.rural.interceptor.Interceptor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yuantao on 2014/10/4.
 */
@Component
public class UserShowOneInterceptor extends Interceptor{
    @Override
    public int order() {
        return 2;
    }

    @Override
    public String pattern() {
        return "/user/showOne";
    }

    @Override
    public boolean before(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("showOne before");
        return false;
    }

    @Override
    public boolean after(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("showOne after");
        return true;
    }
}
