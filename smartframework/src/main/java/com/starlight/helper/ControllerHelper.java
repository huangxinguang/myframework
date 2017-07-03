package com.starlight.helper;

import com.starlight.annotation.Action;
import com.starlight.bean.Handler;
import com.starlight.bean.Request;
import com.starlight.utils.ArrayUtil;
import com.starlight.utils.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by huangxinguang on 2017/6/30 下午4:05.
 */
public class ControllerHelper {
    private static final Map<Request,Handler> ACTION_MAP = new HashMap<>();

    static {
        Set<Class<?>> contollerClassSet = ClassHelper.getControllerClassSet();
        if(CollectionUtil.isNotEmpty(contollerClassSet)) {
            //遍历这些controller类
            for(Class<?> controllerClass : contollerClassSet) {
                Method[] methods = controllerClass.getDeclaredMethods();
                if(ArrayUtil.isNotEmpty(methods)) {
                    for(Method method : methods) {
                        if(method.isAnnotationPresent(Action.class)) {
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            if(mapping.matches("\\w+:/\\w*")) {
                                String[] array = mapping.split(":");
                                if(ArrayUtil.isNotEmpty(array) && array.length == 2) {
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod,requestPath);
                                    Handler handler = new Handler(controllerClass,method);
                                    ACTION_MAP.put(request,handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取handler
     * @param requestMethod 请求方法
     * @param requestPath 请求路径
     * @return
     */
    public static Handler getHandler(String requestMethod,String requestPath) {
        Request request = new Request(requestMethod,requestPath);
        return ACTION_MAP.get(request);
    }
}
