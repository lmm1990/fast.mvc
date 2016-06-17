package fast.mvc.context;

import fast.mvc.classHandler.AppConfig;
import fast.mvc.classHandler.DataHandler;
import fast.mvc.model.Action;
import fast.mvc.model.enumModel.RequestMethod;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lmm on 2016/6/16.
 */
@WebFilter(filterName = "Filter0_GlobalFilter", value = "*")
public class GlobalFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        RequestMethod method = null;
        switch (request.getMethod()){
            case "GET":
                method = RequestMethod.GET;
                break;
            case "HEAD":
                method = RequestMethod.HEAD;
                break;
            case "POST":
                method = RequestMethod.POST;
                break;
            case "PUT":
                method = RequestMethod.PUT;
                break;
            case "PATCH":
                method = RequestMethod.PATCH;
                break;
            case "DELETE":
                method = RequestMethod.DELETE;
                break;
            case "OPTIONS":
                method = RequestMethod.OPTIONS;
                break;
            case "TRACE":
                method = RequestMethod.TRACE;
                break;
        }
        String url = request.getRequestURI();
        if(!DataHandler.actionList.containsKey(url)){
            if(AppConfig.notFoundPageUrl.isEmpty()){
                response.setStatus(404);
                response.getWriter().write("404 page not found");
                return;
            }
            request.getRequestDispatcher(AppConfig.notFoundPageUrl).forward(request,response);
            return;
        }
        Action action = DataHandler.actionList.get(url);
        if(!action.getMethodList().contains(method)){
            if(AppConfig.methodNotAllowedPageUrl.isEmpty()){
                response.setStatus(405);
                response.getWriter().write("405 method not allowed");
                return;
            }
            request.getRequestDispatcher(AppConfig.methodNotAllowedPageUrl).forward(request,response);
            return;
        }
        try{
            action.getAction().invoke(action.getController(),request,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }


}
