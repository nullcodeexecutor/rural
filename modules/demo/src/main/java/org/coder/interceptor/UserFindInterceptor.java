package org.coder.interceptor;

import org.rural.interceptor.Interceptor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yuantao on 2014/10/4.
 */
@Component
public class UserFindInterceptor extends Interceptor {
    @Override
    public int order() {
        return 1;
    }

    @Override
    public String pattern() {
        return "/user/find*";
    }

    @Override
    public boolean before(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("find before");
        return true;
    }

    @Override
    public boolean after(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("find after");
        return false;
    }
}
