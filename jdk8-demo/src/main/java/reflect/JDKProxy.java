package reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author Allenzsy
 * @Date 2022/10/26 23:27
 * @Description:
 */
public class JDKProxy {
    public static void main(String[] args) {
        // JDK1.8及以前的版本
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // JDK1.8以后的版本
        // System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");

        PrintServiceImpl target = new PrintServiceImpl();
        PrintService proxyInstance = (PrintService) Proxy.newProxyInstance(PrintServiceImpl.class.getClassLoader(),
                PrintServiceImpl.class.getInterfaces(), new MyInvocationHandler(target));
        target.myPrint("执行普通对象的方法");
        System.out.println("=====================");
        proxyInstance.myPrint("执行代理对象方法");
    }
}

class MyInvocationHandler implements InvocationHandler {

    /**
     * 被代理的真实对象
     */
    private final Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("执行业务方法"+method.getName()+"前...");
        // 执行业务方法
        Object res = method.invoke(target, args);
        System.out.println("执行业务方法"+method.getName()+"后...");
        return null;
    }
}

interface PrintService {
    String myPrint(String str);
}

class PrintServiceImpl implements PrintService {
    @Override
    public String myPrint(String str) {
        System.out.println("我的打印实现类,"+str);
        return "打印成功";
    }
}