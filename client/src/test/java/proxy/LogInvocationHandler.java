/* 
 * All rights Reserved, Designed By 农金圈
 * 2017/12/22 14:43
 */
package proxy;

import com.nemo.framework.dao.utils.LogUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 打印日志的切面
 * Created by Nemo on 2017/12/22.
 */
public class LogInvocationHandler implements InvocationHandler {

    LogUtils log = LogUtils.getLog(LogInvocationHandler.class);

    private Object target; //目标对象

    LogInvocationHandler(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        log.debug("方法执行前记录日志。");

        //执行原有逻辑
        Object rev = method.invoke(target, args);

        log.debug("方法执行后记录日志。");

        return rev;
    }
}
