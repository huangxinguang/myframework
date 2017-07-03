package com.starlight.jdkproxy.Impl;

import com.starlight.jdkproxy.HelloService;

/**
 * Created by huangxinguang on 2017/7/3 上午11:06.
 */
public class HelloServiceImpl implements HelloService {

    public void sayHello(String name) {
        System.out.println("hello :" + name);
    }
}
