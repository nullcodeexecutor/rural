package org.rural.interceptor;

import org.rural.core.ActionContext;
import org.rural.core.ActionInvoker;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by yuantao on 2014/9/9.
 * 此对象的构造不是初始化时，而是请求到来时，每一个请求都会构建一个此对象
 */
public class InterceptorChain {
    private List<Interceptor> interceptors = null;
    private int index = -1;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ActionInvoker actionInvoker;

    public InterceptorChain(List<Interceptor> interceptors){
        this.interceptors = interceptors;
    }

    public ActionInvoker getActionInvoker() {
        return actionInvoker;
    }

    public void setActionInvoker(ActionInvoker actionInvoker) {
        this.actionInvoker = actionInvoker;
    }

    public boolean invoke() throws IOException, ServletException {
        if(null == interceptors || interceptors.isEmpty()){
            invoke(this.actionInvoker);
            return true;
        }
        if(-1 == index){
            this.request = ActionContext.context().getRequest();
            this.response = ActionContext.context().getResponse();
        }
        index ++;
        if(index < interceptors.size()){
            Interceptor interceptor = interceptors.get(index);
            if(!interceptor.invoke(this, this.request, this.response)){
                return false;
            }
        }else{
            invoke(this.actionInvoker);
        }
        return true;
    }

    private void invoke(ActionInvoker invoker) throws IOException, ServletException {
        if (null == actionInvoker) {
            throw new NullPointerException("ActionInvoker is null");
        }
        this.actionInvoker.execute();
    }

}
