/* 
 * All rights Reserved, Designed By 农金圈
 * 2017/12/22 14:04
 */

import com.nemo.dao.UserDao;
import com.nemo.framework.dao.annotation.Autowired;


/**
 * TODO dao自动注入相关测试
 * Created by Nemo on 2017/12/22.
 */
public class DaoAutowiredTest {

    @Autowired
    private UserDao userDao;

    public void select(){
        userDao.select();
    }


}
