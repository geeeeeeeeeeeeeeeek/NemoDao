/* 
 * All rights Reserved, Designed By 微迈科技
 * 2017/12/19 19:33
 */

import com.nemo.framework.dao.utils.LogUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * 日志工具相关测试
 * Created by Nemo on 2017/12/19.
 */
public class LogTest {

    private LogUtils log = LogUtils.getLog(LogTest.class);

    @Test
    public void testPrint() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.debug("你好世界。");
    }
}
