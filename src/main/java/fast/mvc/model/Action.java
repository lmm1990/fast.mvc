package fast.mvc.model;

import fast.mvc.model.enumModel.RequestMethod;

import java.lang.reflect.Method;
import java.util.HashSet;

/**
 * Created by lmm on 2016/6/17.
 */
public class Action {
    /**
     * method列表
     * */
    private HashSet<RequestMethod> methodList;

    /**
     * action方法
     * */
    private Method action;

    /**
     * controller
     * */
    private Object controller;

    /**
     * 获得method列表
     * @return request允许的广告列表
     *
     * */
    public HashSet<RequestMethod> getMethodList() {
        return methodList;
    }

    /**
     * 设置method列表
     * @param methodList request允许的广告列表
     * */
    public void setMethodList(HashSet<RequestMethod> methodList) {
        this.methodList = methodList;
    }

    /**
     * 获得action方法
     * @return action方法
     * */
    public Method getAction() {
        return action;
    }

    /**
     * 设置action方法
     * @param action action方法
     *
     * */
    public void setAction(Method action) {
        this.action = action;
    }

    /**
     * 获得设置controller
     * @return controller类
     * */
    public Object getController() {
        return controller;
    }

    /**
     * 设置controller
     * @param controller 控制器
     * */
    public void setController(Object controller) {
        this.controller = controller;
    }
}