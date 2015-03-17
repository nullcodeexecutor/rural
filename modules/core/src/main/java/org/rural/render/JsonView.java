package org.rural.render;

import org.rural.ui.Model;
import org.rural.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by yuantao on 2014/8/10.
 */
public class JsonView extends AbstractView {
    private JsonView(){
    }
    private static View view = null;

    static {
        view = new JsonView();
    }

    public static View me(){
        return view;
    }

    @Override
    public void forward(String viewName, HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {
        response.setHeader("content-type", "application/json");
        PrintWriter out = response.getWriter();
        out.print(JsonUtil.objectToString(model));
        out.flush();
        out.close();
    }
}
