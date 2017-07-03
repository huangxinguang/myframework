package cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by huangxinguang on 2017/7/3 上午11:33.
 */
public class CGLibProxy implements MethodInterceptor {

    private static CGLibProxy cgLibProxy = null;

    private CGLibProxy() {}

    public static CGLibProxy getInstance() {
        if(cgLibProxy == null) {
            cgLibProxy = new CGLibProxy();
        }
        return cgLibProxy;
    }

    public <T> T getProxy(Class<T> cls) {
        return (T)Enhancer.create(cls,this);
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = methodProxy.invokeSuper(o,objects);

        Thread.sleep(1000);
        long end = System.currentTimeMillis();

        System.out.println(end-start);

        return result;
    }
}
