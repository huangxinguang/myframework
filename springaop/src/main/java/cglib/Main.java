package cglib;

import com.starlight.jdkproxy.HelloService;
import com.starlight.jdkproxy.Impl.HelloServiceImpl;

/**
 * Created by huangxinguang on 2017/7/3 上午11:40.
 */
public class Main {
    public static void main(String[] args) {
        HelloService helloServiceProxy = CGLibProxy.getInstance().getProxy(HelloServiceImpl.class);
        helloServiceProxy.sayHello("jar");
    }
}
