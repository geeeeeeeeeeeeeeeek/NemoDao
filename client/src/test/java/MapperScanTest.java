/* 
 * All rights Reserved, Designed By 微迈科技
 * 2017/12/20 17:06
 */

import com.nemo.dao.UserDao;
import com.nemo.dao.core.SqlContext;
import com.nemo.dao.core.SqlSession;
import com.nemo.dao.core.SqlSessionFactory;
import com.nemo.dao.scan.MapperScaner;
import com.nemo.dao.scan.bean.MapperBean;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Nemo on 2017/12/20.
 */
public class MapperScanTest {

    @Test
    public void scan() throws IOException, DocumentException, NoSuchMethodException, SQLException {
        MapperScaner.scan();

        SqlContext context = SqlContext.instance();
        Map<String, MapperBean> mappers = context.getMappers();
        Set<String> keySet = mappers.keySet();
        for(String key : keySet){
            System.out.println(key);
        }
    }

    @Test
    public void exec() throws NoSuchMethodException, SQLException, DocumentException, IOException {
        scan();
        SqlSession session = SqlSessionFactory.getSession();
        Map<String,String> params = new HashMap<String, String>();
        params.put("name","Nemo");
        session.exec(UserDao.class,UserDao.class.getMethod("select"),params);
    }

}
