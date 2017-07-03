package com.starlight.jdkproxy;

import com.starlight.jdkproxy.Impl.HelloServiceImpl;

import java.lang.reflect.Proxy;

/**
 * Created by huangxinguang on 2017/7/3 上午11:11.
 */
public class Main {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        HelloProxy helloProxy = new HelloProxy(helloService);
        HelloService proxy = (HelloService) Proxy.newProxyInstance(helloProxy.getClass().getClassLoader(),helloService.getClass().getInterfaces(),helloProxy);
        proxy.sayHello("jay");
    }
}
