package fast.mvc.context;

import fast.mvc.annotation.Controller;
import fast.mvc.annotation.RequestMapping;
import fast.mvc.classHandler.DataHandler;
import fast.mvc.model.enumModel.RequestMethod;
import fast.mvc.utils.ScannerClass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.HashSet;

/**
 * Created by lmm on 2016/6/17.
 */
public class ApplicationContext {

    private static boolean isInited = false;

    /**
     * mvc初始化<br>
     * author:刘明明<br>
     * updateTime:2016年6月17日16:04:13
     *
     * @param controllerBasePackageName 控制器所在的包名
     * @param staticFilePathList 静态文件目录列表
     *
     * */
    public static void init(String controllerBasePackageName,String... staticFilePathList){
        if(isInited){
            return;
        }
        if(staticFilePathList != null){
            DataHandler.staticFilePathList = staticFilePathList;
        }else{
            DataHandler.staticFilePathList = new String[0];
        }
        isInited = true;
        long start = System.currentTimeMillis();
        HashSet<Class<?>> classList = ScannerClass.getClassList(controllerBasePackageName);
        System.out.println(String.format("扫描class文件用时：%d毫秒",System.currentTimeMillis()-start));
        //扫描注解
        for (Class<?> item : classList){
            if(!item.isAnnotationPresent(Controller.class)){
                continue;
            }
            if(item.isAnnotationPresent(RequestMapping.class)){
                RequestMapping requestMapping = item.getAnnotation(RequestMapping.class);
                if(requestMapping == null){
                    continue;
                }
                if(requestMapping.method().length > 1){
                    System.out.println("Controller RequestMapping 注解 method属性不生效");
                }
                reflectMethod(item,requestMapping.value());
            }
        }
        for (String url:DataHandler.actionList.keySet()) {
            System.out.println(String.format("mapping url:%s",url));
        }
    }

    /**
     * 反射方法<br>
     * author:刘明明<br>
     * updateTime:2016年6月17日11:35:18
     *
     * @param baseUrl 路径
     * @param classItem controller类
     * */
    private static void reflectMethod(Class<?> classItem,String baseUrl){
        if(baseUrl == null || baseUrl.equals("/")){
            baseUrl = "";
        }else if(baseUrl.endsWith("/")){
            baseUrl = baseUrl.substring(0,baseUrl.lastIndexOf("/"));
        }
        Method[] methodList = classItem.getMethods();
        RequestMapping requestMapping;
        MethodHandle action = null;
        String url;
        for (Method item : methodList){
            if(!item.isAnnotationPresent(RequestMapping.class)){
                continue;
            }
            requestMapping = item.getAnnotation(RequestMapping.class);
            if(requestMapping == null){
                continue;
            }
            if(requestMapping.value() == null){
                System.out.println(String.format("无效mapping,路径：%s",item.getName()));
                continue;
            }
            url = requestMapping.value();
            if(url.length() > 0 && !url.startsWith("/")){
                url = String.format("/%s",url);
            }
            url = String.format("%s%s",baseUrl,url);
            MethodType mt = MethodType.methodType(void.class, HttpServletRequest.class, HttpServletResponse.class);
            try{
                action = MethodHandles.lookup().findVirtual(classItem, item.getName(), mt).bindTo(classItem.newInstance());
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }
            if(requestMapping.method().length == 0){
                DataHandler.actionList.put(String.format("%s_%s",url,RequestMethod.GET.toString()),action);
                DataHandler.actionList.put(String.format("%s_%s",url,RequestMethod.HEAD.toString()),action);
                DataHandler.actionList.put(String.format("%s_%s",url,RequestMethod.POST.toString()),action);
                DataHandler.actionList.put(String.format("%s_%s",url,RequestMethod.PUT.toString()),action);
                DataHandler.actionList.put(String.format("%s_%s",url,RequestMethod.PATCH.toString()),action);
                DataHandler.actionList.put(String.format("%s_%s",url,RequestMethod.DELETE.toString()),action);
                DataHandler.actionList.put(String.format("%s_%s",url,RequestMethod.OPTIONS.toString()),action);
                DataHandler.actionList.put(String.format("%s_%s",url,RequestMethod.TRACE.toString()),action);
                continue;
            }
            for (RequestMethod method: requestMapping.method()) {
                DataHandler.actionList.put(String.format("%s_%s",url,method.toString()),action);
            }
        }
    }
}