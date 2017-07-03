package com.starlight.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by huangxinguang on 2017/7/3 上午11:06.
 */
public class HelloProxy implements InvocationHandler {

    private Object target ;

    public HelloProxy(Object target) {
        this.target = target;
    }

    public <T> T getProxy() {
        return (T)Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        long start = System.currentTimeMillis();

        method.invoke(target,args);

        Thread.sleep(1000);

        long end = System.currentTimeMillis();

        System.out.println("need time :" + (end - start));

        return null;
    }
}
