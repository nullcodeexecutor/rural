package org.rural.render;

import org.rural.context.RuralContext;
import org.rural.ui.Model;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yuantao on 2014/8/10.
 */
public class JspView extends AbstractView {
    private JspView(){
    }
    private static View view ;
    static {
        view = new JspView();
    }
    public static View me(){
        return view;
    }
    @Override
    public void forward(String viewName, HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {
        for(String key : model.keySet()){
            request.setAttribute(key, model.get(key));
        }
        request.getRequestDispatcher(RuralContext.context().getRurlConfig().getPageLocation()+viewName).forward(request, response);
    }
}
