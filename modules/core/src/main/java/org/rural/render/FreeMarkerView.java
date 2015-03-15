package org.rural.render;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rural.core.ActionContext;
import org.rural.context.RuralContext;
import org.rural.exception.RuralException;
import org.rural.ui.Model;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by yuantao on 2014/8/31.
 */
public class FreeMarkerView extends AbstractView {
    private final static Log log = LogFactory.getLog(FreeMarkerView.class);

    private FreeMarkerView(){
        initConfiguration();
    }
    private static View view = null;

    private Configuration cfg = null;

    static {
        view = new FreeMarkerView();
    }

    public static View me(){
        return view;
    }

    private void initConfiguration(){
        ServletContext servletContext = ActionContext.context().getServletContext();
        cfg = new Configuration();
        try {
            File loadPath = new File(servletContext.getRealPath("") + RuralContext.context().getRurlConfig().getPageLocation());
            log.info("freemarker load path: " + loadPath.getAbsolutePath());
            cfg.setDirectoryForTemplateLoading(loadPath);
        } catch (IOException e) {
            throw new RuralException(e);
        }
        cfg.setObjectWrapper(new DefaultObjectWrapper());
    }

    @Override
    public void forward(String viewName, HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {
        Template template = cfg.getTemplate(viewName);
        PrintWriter out = response.getWriter();
        try {
            template.process(model, out);
        } catch (TemplateException e) {
            throw new RuralException(e);
        }
        out.close();
    }
}
