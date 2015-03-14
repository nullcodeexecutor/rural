package org.rural.render;

import org.rural.ui.Model;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yuantao on 2014/10/3.
 */
public class Render {

    public static void render(Object invokeResult, HttpServletRequest request, HttpServletResponse response, Model<String, Object> model) throws IOException, ServletException {
        if (null != invokeResult){
            String result = invokeResult.toString();
            View view = RenderFactory.view(result);
            view.render(result, request, response, model);
        }
    }

}
