package com.starlight.helper;

import com.starlight.annotation.Inject;
import com.starlight.utils.CollectionUtil;
import com.starlight.utils.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by huangxinguang on 2017/6/30 下午3:39.
 */
public final class IocHelper   {
    static {
        Map<Class<?>,Object> beanMap = BeanHelper.getBeanMap();
        if(CollectionUtil.isNotEmpty(beanMap)) {
            for(Map.Entry<Class<?>,Object> beanEntry : beanMap.entrySet()) {

                //从beanmap获取beanclass和bean实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();

                //获取bean的所有成员变量
                Field[] beanFields = beanClass.getDeclaredFields();

                //遍历field并找到Inject表达式，实例化注入实例
                for(Field beanField:beanFields) {
                    if(beanField.isAnnotationPresent(Inject.class)) {
                        Class<?> beanFieldClass = beanField.getType();
                        Object beanFieldInstance = beanMap.get(beanFieldClass);
                        if(beanFieldInstance != null) {
                            ReflectionUtil.setField(beanInstance,beanField,beanFieldInstance);
                        }
                    }
                }
            }
        }
    }
}
