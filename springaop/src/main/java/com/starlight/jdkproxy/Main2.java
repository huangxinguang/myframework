package com.starlight.jdkproxy;

import com.starlight.jdkproxy.Impl.HelloServiceImpl;

/**
 * Created by huangxinguang on 2017/7/3 上午11:19.
 */
public class Main2 {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        HelloProxy helloProxy = new HelloProxy(helloService);
        HelloService helloServiceProxy = helloProxy.getProxy();
        helloServiceProxy.sayHello("man");
    }
}
