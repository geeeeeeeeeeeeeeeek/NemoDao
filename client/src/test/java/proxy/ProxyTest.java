/* 
 * All rights Reserved, Designed By 农金圈
 * 2017/12/22 14:43
 */
package proxy;

import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * 代理相关测试
 * Created by Nemo on 2017/12/22.
 */
public class ProxyTest {

    @Test
    public void test() {
        IBusiness business = (IBusiness) getProxyInstence(IBusiness.class,new Business());
        business.doSomeThing();
    }

    /**
     * 得到被代理的对象
     * @param clazz
     * @param instence
     * @return
     */
    private static Object getProxyInstence(Class clazz,Object instence){
        //需要代理的接口，被代理类实现的多个接口都必须在这里定义
        Class[] proxyInterface = new Class[] { clazz };
        //构建AOP的Advice，这里需要传入业务类的实例
        LogInvocationHandler handler = new LogInvocationHandler(instence);
        //生成代理类的字节码加载器
        ClassLoader classLoader = ProxyTest.class.getClassLoader();
        //织入器，织入代码并生成代理类
        Object proxyBusiness = Proxy.newProxyInstance(classLoader, proxyInterface, handler);
        //使用代理类的实例来调用方法。
        return proxyBusiness;
    }

}
