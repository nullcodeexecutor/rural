package org.rural.render;

import org.rural.ui.Model;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yuantao on 2014/8/10.
 */
public interface View {

    void render(String view, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException, ServletException;

}
