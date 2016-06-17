package fast.mvc.context;

import fast.mvc.annotation.Controller;
import fast.mvc.annotation.RequestMapping;
import fast.mvc.classHandler.AppConfig;
import fast.mvc.classHandler.DataHandler;
import fast.mvc.model.Action;
import fast.mvc.model.enumModel.RequestMethod;
import fast.mvc.utils.ScannerClass;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by lmm on 2016/6/17.
 */
public class ApplicationContext {

    private static boolean isInited = false;

    /**
     * mvc初始化<br />
     * author:刘明明<br />
     * updateTime:2016年6月17日16:04:13
     * */
    public static void init(String controllerBasePackageName){
        if(isInited){
            return;
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
    }

    /**
     * 反射方法<br />
     * author:刘明明<br />
     * updateTime:2016年6月17日11:35:18
     * */
    private static void reflectMethod(Class<?> classItem,String baseUrl){
        if(baseUrl == null || baseUrl.equals("/")){
            baseUrl = "";
        }else if(baseUrl.endsWith("/")){
            baseUrl = baseUrl.substring(0,baseUrl.lastIndexOf("/"));
        }
        Method[] methodList = classItem.getMethods();
        RequestMapping requestMapping;
        Action action;
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
            if(!url.startsWith("/")){
                url = String.format("/%s",url);
            }
            url = String.format("%s%s",baseUrl,url);
            action = new Action();
            action.setAction(item);
            try{
                action.setController(classItem.newInstance());
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }
            if(requestMapping.method().length == 0){
                action.setMethodList(new HashSet<RequestMethod>(){{
                    add(RequestMethod.GET);
                    add(RequestMethod.HEAD);
                    add(RequestMethod.POST);
                    add(RequestMethod.PUT);
                    add(RequestMethod.PATCH);
                    add(RequestMethod.DELETE);
                    add(RequestMethod.OPTIONS);
                    add(RequestMethod.TRACE);
                }});
            }else{
                action.setMethodList(new HashSet<RequestMethod>(Arrays.asList(requestMapping.method())));
            }
            DataHandler.actionList.put(url,action);
        }
    }
}