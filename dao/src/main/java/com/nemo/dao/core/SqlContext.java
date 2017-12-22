/* 
 * All rights Reserved, Designed By 微迈科技
 * 2017/12/20 19:07
 */
package com.nemo.dao.core;

import com.nemo.dao.scan.bean.MapperBean;

import java.util.HashMap;
import java.util.Map;

/**
 * sql上下文工厂
 * Created by Nemo on 2017/12/20.
 */
public class SqlContext {

    /**
     * Mapper容器
     */
    private static Map<String,MapperBean> mappers = new HashMap<String, MapperBean>();

    private static SqlContext sqlContext;

    private SqlContext(){
    }

    /**
     * 得到实例化
     * @return
     */
    public static SqlContext instance(){
        if(sqlContext == null){
            sqlContext = new SqlContext();
        }
        return sqlContext;
    }

    /**
     * 添加mapper
     * @param key
     * @param mapper
     */
    public void addMapper(String key,MapperBean mapper){
        mappers.put(key,mapper);
    }

    /**
     * 得到Mapper
     * @param key
     * @return
     */
    public MapperBean getMapper(String key){
        return mappers.get(key);
    }

    /**
     * 判断mapper存在不存在
     * @param key
     * @return
     */
    public boolean mapperExits(String key){
        return !(mappers.get(key) == null);
    }

    /**
     * 得到所有的mapper
     * @return
     */
    public Map<String,MapperBean> getMappers(){
        return mappers;
    }

}
