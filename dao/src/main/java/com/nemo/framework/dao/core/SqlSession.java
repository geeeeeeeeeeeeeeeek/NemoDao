/* 
 * All rights Reserved, Designed By 微迈科技
 * 2017/12/20 19:34
 */
package com.nemo.framework.dao.core;

import com.nemo.framework.dao.data.DataHelper;
import com.nemo.framework.dao.utils.BeanUtils;
import com.nemo.framework.dao.utils.CollectionsUtils;
import com.nemo.framework.dao.utils.StringUtils;
import com.nemo.framework.dao.scan.bean.MapperBean;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Sql会话
 */
public class SqlSession{

    private static SqlSession sqlSession;

    private String mapperKey;

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
    public Object exec(Class<?> clss, Method method, Object params) throws Exception {
        mapperKey = clss.getName()+"."+method.getName();
        MapperBean mapper = Context.instance().getMapper(mapperKey);
        if(mapper == null){
            throw new RuntimeException("抱歉，找不到对应的mapper信息："+mapperKey);
        }
        String sql = mapper.getSql();
        String type = mapper.getType();
        String resultType = mapper.getResultType();

        //从SQL中获取参数列表
        List<Object> param = getParams(sql,params);
        //处理SQL
        sql = dealSql(sql);

        //开始执行
        if(type.equals("select")){
            Class resultClass = Class.forName(resultType);
            return DataHelper.instance().findMoreRefResult(sql, param,resultClass);
        }else if(type.equals("insert")){
            return DataHelper.instance().insert(sql,param);
        }else {
            return DataHelper.instance().update(sql,param);
        }
    }

    /**
     * 预处理sql
     * @param sql
     * @return
     */
    private String dealSql(String sql){
        if(StringUtils.isEmpty(sql)){
            throw new RuntimeException("抱歉，需要执行的sql语句为空。Mapper:"+mapperKey);
        }
        return sql.replaceAll("\\$\\{(\\w+)\\}"," ? ");
    }

    /**
     * 得到参数列表
     * @param sql
     * @param params
     * @return
     */
    private static List<Object> getParams(String sql,Object params){
        if(params == null){
            params = new Object();
        }
        Map<String,Object> paramsMap = BeanUtils.transBean2Map(params);

        List<String> paramNames = getParamNamesFromSql(sql);
        List<Object> result = new ArrayList<Object>();
        if(CollectionsUtils.isNotEmpty(paramNames)){
            for(String paramName : paramNames){
                result.add(paramsMap.get(paramName));
            }
        }
        return result;
    }

    /**
     * 从Sql中获取参数名称列表
     * @param sql
     * @return
     */
    private static List<String> getParamNamesFromSql(String sql){
        List<String> names=new ArrayList<String>();
        Pattern pattern = Pattern.compile("\\$\\{(\\w+)\\}");
        Matcher matcher = pattern.matcher(sql);
        while(matcher.find()) {
            names.add(matcher.group(1));
        }
        return names;
    }

}
