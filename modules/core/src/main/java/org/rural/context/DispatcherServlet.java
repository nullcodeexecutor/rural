package org.rural.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rural.core.ActionContext;
import org.rural.core.RequestHandler;
import org.rural.context.RuralContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yuantao on 2014/8/7.
 */
public class DispatcherServlet extends HttpServlet {
    private static final Log log = LogFactory.getLog(RuralContext.class);
    private RuralContext ruralContext = null;
    private String charset = null;
    private RequestHandler requestHandler = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        log.info("init ...");
        ServletContext servletContext = config.getServletContext();
        ruralContext = RuralContext.initRuralContext(servletContext);
        this.charset = ruralContext.getRuralConfigBean().getCharset();
        requestHandler = new RequestHandler();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.dispatcher(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.dispatcher(request, response);
    }

    private void dispatcher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(this.charset);
        response.setCharacterEncoding(this.charset);
        log.debug("request:"+request.getRequestURI());
        ActionContext.setContext(request, response);
        requestHandler.dispatcher();
    }

}
