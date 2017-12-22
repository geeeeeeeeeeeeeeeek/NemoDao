/* 
 * All rights Reserved, Designed By 微迈科技
 * 2017/12/19 19:57
 */
package com.nemo.framework.dao.utils;

import java.lang.reflect.InvocationTargetException;

import static java.lang.Class.*;

/**
 * 日志相关操作工具
 * Created by Nemo on 2017/12/19.
 */
public class LogUtils {

    private static Class<?> clss;

    /**
     * 得到一个工具对象
     * @param c
     * @return
     */
    public static LogUtils getLog(Class<?> c) {
        clss = c;
        return new LogUtils();
    }

    /**
     * 得到一个工具真实对象
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private Object getLogger() {
        Object obj = null;
        try {
            obj = forName("org.apache.log4j.Logger").getMethod("getLogger",Class.class).invoke(null,clss);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 打印debug日志
     * @param outObj
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void debug(Object outObj) {
        Object obj = getLogger();
        try {
            obj.getClass().getMethod("debug",Object.class).invoke(obj,outObj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}
