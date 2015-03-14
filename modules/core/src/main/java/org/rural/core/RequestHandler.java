package org.rural.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rural.context.RuralContext;
import org.rural.ui.Model;
import org.rural.exception.RuralException;
import org.rural.utils.StringUtil;
import org.rural.utils.TypeUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuantao on 2014/8/9.
 */
public class RequestHandler {
    private final static Log log = LogFactory.getLog(RequestHandler.class);

    public void dispatcher() throws IOException, ServletException {
        HttpServletRequest request = ActionContext.context().getRequest();
        HttpServletResponse response = ActionContext.context().getResponse();
        String resource =getResource(request);
        Action action = RuralContext.context().getAction(resource);
        if(null == action){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        this.handle(action, request, response);
    }

    private void handle(Action action, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Object[] parameters = generateParameters(action, request, response);
        ActionInvoker invoker = new ActionInvoker(action, parameters, request, response);
        invoker.invoke();
    }

    private Object[] generateParameters(Action action, HttpServletRequest request, HttpServletResponse response){
        List<Object> args = new ArrayList<Object>();
        for(int i=0; i<action.getArgTypes().length; i++) {
            try {
                args.add(generateParameter(action.getArgTypes()[i], action.getArgNames()[i], request, response));
            } catch (NumberFormatException e) {
                throw new RuralException(e);
            }
        }
        return args.toArray();
    }

    private String getResource(HttpServletRequest request){
        String contextPath = request.getContextPath();
        String resource = "";
        if("/".equals(contextPath)) {
            resource = request.getRequestURI();
        }else {
            resource = StringUtil.cutPrefix(request.getRequestURI(), contextPath);
        }
        return resource;
    }

    private Object generateParameter(Class<?> type, String name, HttpServletRequest request, HttpServletResponse response){
        if(type.equals(Model.class)){
            return new Model<String, Object>();
        }
        if(type.equals(ServletContext.class)){
            return ActionContext.context().getServletContext();
        }
        if(type.equals(HttpServletRequest.class)){
            return request;
        }
        if(type.equals(HttpServletResponse.class)){
            return response;
        }
        if(type.equals(HttpSession.class)){
            return request.getSession(true);
        }
        String value = request.getParameter(name);
        if(null == value){
            return null;
        }
        return TypeUtil.typeCast(type, value);
    }

}
