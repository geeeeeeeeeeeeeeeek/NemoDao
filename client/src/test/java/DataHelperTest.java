/* 
 * All rights Reserved, Designed By 微迈科技
 * 2017/12/19 15:59
 */

import com.nemo.framework.dao.data.DataHelper;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据操作工具相关测试
 * Created by Nemo on 2017/12/19.
 */
public class DataHelperTest {

    public static Logger log = Logger.getLogger(DataHelperTest.class);

    private DataHelper dataHelper = DataHelper.instance();

    @Test
    public void testGet() throws SQLException {
        String sql = "select * from t_user where id = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(1);
        Map<String, Object> modeResult = dataHelper.findSimpleResult(sql, params);
        modeResult = dataHelper.findSimpleResult(sql, params);
        log.debug("fdsadsasdfsa");
        Assert.assertNotNull(modeResult);
    }

    @Test
    public void testQuery() throws SQLException {
        String sql = "select * from t_user";
        List<Object> params = new ArrayList<Object>();
        List<Map<String, Object>> modeResult = dataHelper.findModeResult(sql, params);
        Assert.assertNotNull(modeResult);
    }

    @Test
    public void testInsert() throws SQLException {
        String sql = "insert into t_user(name,age) values(?,?)";
        List<Object> params = new ArrayList<Object>();
        params.add("Nemo");
        params.add(100);
        Integer key = dataHelper.insert(sql,params);
        Assert.assertNotNull(key);
    }

    @Test
    public void testUpdate() throws SQLException {
        String sql = "update t_user set name=?,age=? where id=?";
        List<Object> params = new ArrayList<Object>();
        params.add("Leo");
        params.add(99);
        params.add(1);
        boolean result = dataHelper.update(sql,params);
        Assert.assertFalse(!result);
    }

}
