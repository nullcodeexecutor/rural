package org.rural.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rural.interceptor.InterceptorChain;
import org.rural.render.Render;
import org.rural.ui.Model;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by yuantao on 2014/8/23.
 */
public class ActionInvoker {
    private static final Log LOGGER = LogFactory.getLog(ActionInvoker.class);
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Action action;
    private Object[] args;
    private Object result;

    public ActionInvoker(Action action, Object[] args, HttpServletRequest request, HttpServletResponse response) {
        this.action = action;
        this.args = args;
        this.request = request;
        this.response = response;
    }

    public void execute() throws IOException, ServletException {
        try {
            result = action.getMethod().invoke(action.getInstance(), this.args);
        } catch (IllegalAccessException e) {
            LOGGER.info(e.getMessage());
        } catch (InvocationTargetException e) {
            LOGGER.error(request.getRequestURI(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public void invoke() throws IOException, ServletException {
        InterceptorChain chain = new InterceptorChain(action.getInterceptors());
        chain.setActionInvoker(this);
        if (chain.invoke()) {
            Render.render(result, request, response, findModel(this.args));
        }
    }

    private Model findModel(Object[] parameters){
        String modelName = Model.class.getName();
        for(Object obj : parameters){
            if(obj.getClass().getName().equals(modelName)){
                return (Model)obj;
            }
        }
        return new Model();
    }

}
