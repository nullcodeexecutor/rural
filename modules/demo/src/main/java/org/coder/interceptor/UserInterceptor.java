package org.coder.interceptor;

import org.rural.interceptor.Interceptor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yuantao on 2014/10/4.
 */
@Component
public class UserInterceptor extends Interceptor{

    @Override
    public boolean before(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("user before");
        return true;
    }

    @Override
    public boolean after(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("user after");
        return true;
    }
}
