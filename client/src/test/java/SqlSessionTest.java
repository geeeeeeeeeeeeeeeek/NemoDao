/* 
 * All rights Reserved, Designed By 微迈科技
 * 2017/12/20 19:33
 */

import com.nemo.dao.UserDao;
import com.nemo.dao.core.SqlSession;
import com.nemo.dao.core.SqlSessionFactory;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by Nemo on 2017/12/20.
 */
public class SqlSessionTest {

    @Test
    public void exec() throws NoSuchMethodException, SQLException {
        SqlSession session = SqlSessionFactory.getSession();
        session.exec(UserDao.class,UserDao.class.getMethod("select"),null);
    }

}
