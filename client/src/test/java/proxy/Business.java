/* 
 * All rights Reserved, Designed By 农金圈
 * 2017/12/22 14:44
 */
package proxy;

import com.nemo.framework.dao.utils.LogUtils;

/**
 * 业务实现类
 * Created by Nemo on 2017/12/22.
 */
public class Business implements IBusiness {

    LogUtils log = LogUtils.getLog(Business.class);

    public boolean doSomeThing() {
        log.debug("这是一个业务逻辑。");
        return true;
    }

}
