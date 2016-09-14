package fast.mvc.classHandler;

import java.lang.invoke.MethodHandle;
import java.util.HashMap;

/**
 * Created by lmm on 2016/6/17.
 */
public class DataHandler {

    /**
     * action列表
     * */
    public static HashMap<String,MethodHandle> actionList = new HashMap<>();

    /**
     * 静态文件目录列表
     * */
    public static String[] staticFilePathList = null;
}