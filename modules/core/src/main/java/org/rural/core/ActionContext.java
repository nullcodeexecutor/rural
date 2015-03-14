package org.rural.core;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by yuantao on 2014/8/23.
 */
public class ActionContext {
    private static final ThreadLocal<ActionContext> context = new ThreadLocal<ActionContext>();
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private ServletContext servletContext;
    private String contextPath;

    private ActionContext(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public HttpSession getSession() {
        return this.request.getSession();
    }

    public ServletContext getServletContext() {
        return this.request.getSession().getServletContext();
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public String getContextPath() {
        return this.request.getContextPath();
    }

    static void setContext(HttpServletRequest request, HttpServletResponse response){
        ActionContext ruralContext = new ActionContext(request, response);
        context.set(ruralContext);
    }

    public static ActionContext context(){
        return context.get();
    }
}
