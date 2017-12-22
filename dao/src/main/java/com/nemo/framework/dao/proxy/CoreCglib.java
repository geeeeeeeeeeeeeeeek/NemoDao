/* 
 * All rights Reserved, Designed By 农金圈
 * 2017/12/22 14:19
 */
package com.nemo.framework.dao.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by Nemo on 2017/12/22.
 */
public class CoreCglib implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return null;
    }

}
