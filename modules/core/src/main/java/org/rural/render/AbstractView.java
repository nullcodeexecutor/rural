package org.rural.render;

import org.rural.ui.Model;
import org.rural.exception.RuralException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yuantao on 2014/8/10.
 */
public abstract class AbstractView implements View{

    @Override
    public final void render(String render, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException, ServletException {
        if("json".equals(render)){
            this.forward("", request, response, model);
            return;
        }
        //example: redirect:/index.jsp
        String[] str = render.split(":", -1);
        if(2 == str.length){
            if("redirect".equals(str[0])){
                String contextPath = request.getContextPath();
                if("/".equals(contextPath)){
                    response.sendRedirect(str[1]);
                }else{
                    response.sendRedirect(contextPath+str[1]);
                }
                return;
            }else if("forward".equals(str[0])){
                this.forward(str[1], request, response, model);
                return;
            }
        }
        throw new RuralException(render+" is error");
    }

    public abstract void forward(String viewName, HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException;

}
