/* 
 * All rights Reserved, Designed By 微迈科技
 * 2017/12/20 19:07
 */
package com.nemo.framework.dao.core;

import com.nemo.framework.dao.scan.bean.MapperBean;

import java.util.HashMap;
import java.util.Map;

/**
 * sql上下文工厂
 * Created by Nemo on 2017/12/20.
 */
public class Context {

    /**
     * Mapper容器
     */
    private static Map<String,MapperBean> mappers = new HashMap<String, MapperBean>();

    /**
     * TODO Dao容器
     */
    private static Map<String,Class> daos = new HashMap<String, Class>();

    private static Context context;

    private Context(){
    }

    /**
     * 得到实例化
     * @return
     */
    public static Context instance(){
        if(context == null){
            context = new Context();
        }
        return context;
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

    /**
     * 添加dao
     * @param key
     * @param dao
     */
    public void addDao(String key,Class dao){
        daos.put(key,dao);
    }

    /**
     * 得到Dao
     * @param key
     * @return
     */
    public Class getDao(String key){
        return daos.get(key);
    }

    /**
     * 判断dao存在不存在
     * @param key
     * @return
     */
    public boolean daoExits(String key){
        return !(daos.get(key) == null);
    }

    /**
     * 得到所有的dao
     * @return
     */
    public Map<String,Class> getDaos(){
        return daos;
    }

}
