package com.starlight.helper;

import com.starlight.utils.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by huangxinguang on 2017/6/30 下午3:30.
 */
public final class BeanHelper {

    private static final Map<Class<?>,Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for(Class<?> beanClass:beanClassSet) {
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass,obj);
        }
    }

    /**
     * 获取bean map
     * @return
     */
    public static Map<Class<?>,Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取bean
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<?> beanClass) {
        if(!BEAN_MAP.containsKey(beanClass)) {
            throw new RuntimeException("can not get bean class:"+beanClass);
        }
        return (T)BEAN_MAP.get(beanClass);
    }
}
