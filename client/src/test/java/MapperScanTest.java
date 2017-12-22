/* 
 * All rights Reserved, Designed By 微迈科技
 * 2017/12/20 17:06
 */

import com.nemo.bean.ResultBean;
import com.nemo.dao.UserDao;
import com.nemo.framework.dao.core.Context;
import com.nemo.framework.dao.core.SqlSession;
import com.nemo.framework.dao.core.SqlSessionFactory;
import com.nemo.framework.dao.scan.MapperScaner;
import com.nemo.framework.dao.scan.bean.MapperBean;
import com.nemo.framework.dao.utils.LogUtils;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Mapper扫描相关测试
 * Created by Nemo on 2017/12/20.
 */
public class MapperScanTest {

    LogUtils log = LogUtils.getLog(MapperScanTest.class);

    /**
     * 扫描测试
     * @throws IOException
     * @throws DocumentException
     * @throws NoSuchMethodException
     * @throws SQLException
     */
    @Test
    public void scan() throws IOException, DocumentException, NoSuchMethodException, SQLException {
        MapperScaner.scan();

        Context context = Context.instance();
        Map<String, MapperBean> mappers = context.getMappers();
        Set<String> keySet = mappers.keySet();
        for(String key : keySet){
            System.out.println(key);
        }
    }

    /**
     * 测试扫描后查询
     * @throws Exception
     */
    @Test
    public void exec() throws Exception {
        scan();
        SqlSession session = SqlSessionFactory.getSession();
        Map<String,String> params = new HashMap<String, String>();
        params.put("name","Nemo");
        List<ResultBean> resultBeanList = (List<ResultBean>) session.exec(UserDao.class, UserDao.class.getMethod("select"), params);
        for(ResultBean result : resultBeanList){
            log.debug(result.getName() + result.getAge());
        }
    }

}
