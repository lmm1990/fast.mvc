package fast.mvc.context;

import fast.mvc.classHandler.AppConfig;
import fast.mvc.classHandler.DataHandler;
import fast.mvc.model.enumModel.RequestMethod;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lmm on 2016/6/16.
 */
public class RouteFilter{

    public static void invoke(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String url = request.getRequestURI();
        for (String item : DataHandler.staticFilePathList) {
            if(url.startsWith(item)){
                filterChain.doFilter(request, response);
                return;
            }
        }
        url = String.format("%s_%s",url,request.getMethod());
        if(!DataHandler.actionList.containsKey(url)){
            if(AppConfig.notFoundPageUrl.isEmpty()){
                response.setStatus(404);
                response.getWriter().write("404 page not found");
                return;
            }
            request.getRequestDispatcher(AppConfig.notFoundPageUrl).forward(request,response);
            return;
        }
        try{
            DataHandler.actionList.get(url).invokeExact(request,response);
        }catch (Throwable e){
            e.printStackTrace();
        }
    }
}