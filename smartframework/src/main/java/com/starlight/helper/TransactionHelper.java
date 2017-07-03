package com.starlight.helper;

import com.starlight.annotation.Transaction;
import com.starlight.utils.ArrayUtil;
import com.starlight.utils.CollectionUtil;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by huangxinguang on 2017/7/3 下午5:11.
 */
public class TransactionHelper {

    static {
        Set<Class<?>> serviceClassSet = ClassHelper.getServiceClassSet();
        if(CollectionUtil.isNotEmpty(serviceClassSet)) {
            //遍历这些controller类
            for(Class<?> serviceClass : serviceClassSet) {
                Method[] methods = serviceClass.getDeclaredMethods();
                if(ArrayUtil.isNotEmpty(methods)) {
                    for(Method method : methods) {
                        if(method.isAnnotationPresent(Transaction.class)) {

                        }
                    }
                }
            }
        }
    }
}
