/* 
 * All rights Reserved, Designed By 微迈科技
 * 2017/12/20 19:34
 */
package com.nemo.dao.core;

import com.nemo.dao.data.DataHelper;
import com.nemo.dao.xml.bean.MapperBean;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Sql会话
 */
public class SqlSession{

    private static SqlSession sqlSession;

    private SqlSession(){

    }

    /**
     * 提供实例化
     * @return
     */
    protected static SqlSession instance(){
        if(sqlSession==null){
            sqlSession = new SqlSession();
        }
        return sqlSession;
    }

    /**
     * 根据类+方法 + 参数执行
     * @param clss
     * @param method
     * @param params
     * @return
     */
    public Object exec(Class<?> clss, Method method, Object params) throws SQLException {
        String mapperKey = clss.getName()+"."+method.getName();
        MapperBean mapper = SqlContext.instance().getMapper(mapperKey);
        if(mapper == null){
            throw new RuntimeException("抱歉，找不到对应的mapper信息："+mapperKey);
        }
        String sql = mapper.getSql();
        String type = mapper.getType();
        //TODO
        if(type.equals("select")){
            List<Map<String, Object>> modeResult = DataHelper.instance().findModeResult(sql, new ArrayList<Object>());
            System.out.println(modeResult);
        }else if(type.equals("insert")){
            DataHelper.instance().insert(sql,null);
        }else {
            DataHelper.instance().update(sql,null);
        }
        return null;
    }

}
