package org.rural.render;

import org.rural.context.RuralContext;
import org.rural.exception.RuralException;

/**
 * Created by yuantao on 2014/8/10.
 */
public class RenderFactory {
    private RenderFactory(){
    }

    public static View view(String temp){
        if("json".equals(temp)){
            return JsonView.me();
        }
        if("jsp".equals(RuralContext.context().getRurlConfig().getTemplate())){
            return JspView.me();
        }
        if("fm".equals(RuralContext.context().getRurlConfig().getTemplate())){
            return FreeMarkerView.me();
        }
        throw  new RuralException("No Templates Found");
    }

}
