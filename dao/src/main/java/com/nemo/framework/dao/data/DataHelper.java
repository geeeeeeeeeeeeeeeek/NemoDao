/* 
 * All rights Reserved, Designed By 微迈科技
 * 2017/9/19 10:01
 */
package com.nemo.framework.dao.data;

import com.nemo.framework.dao.datasource.SimpleDatasource;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作工具
 * Created by Nemo on 2017/9/19.
 */
public class DataHelper {

    private static Connection connection;
    private static PreparedStatement pstmt;
    private static ResultSet resultSet;

    private static SimpleDatasource datasource = null;

    public static DataHelper instance(){
        return new DataHelper();
    }

    private DataHelper(){
        getConnection();
    }

    /**
     * 获得数据库的连接
     * @return
     */
    private Connection getConnection(){
        try {
            if (datasource == null) {
                datasource = SimpleDatasource.instance();
            }
            if (connection == null) {
                connection = datasource.getConnection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * TODO 需要考虑事务
     * 更新数据
     * @param sql
     * @param params
     * @return  返回是否更新成功
     * @throws SQLException
     */
    public boolean update(String sql, List<Object>params) throws SQLException {
        pstmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int index = 1;
        if(params != null && !params.isEmpty()){
            for(int i=0; i<params.size(); i++){
                pstmt.setObject(index++, params.get(i));
            }
        }
        boolean result = pstmt.executeUpdate()>0;
        releaseConn();
        return result;
    }

    /**
     * TODO 需要考虑事务
     * 新增数据
     * @param sql
     * @param params
     * @return 返回新增数据的ID
     * @throws SQLException
     */
    public Integer insert(String sql, List<Object>params) throws SQLException {
        pstmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int index = 1;
        if(params != null && !params.isEmpty()){
            for(int i=0; i<params.size(); i++){
                pstmt.setObject(index++, params.get(i));;
            }
        }
        //执行新增
        pstmt.executeUpdate();
        ResultSet rs = pstmt.getGeneratedKeys();
        Integer key = null;
        if(rs.next()) {
            key = rs.getInt(1);
        }
        releaseConn();
        return key;
    }

    /**
     * 查询单条记录
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public Map<String, Object> findSimpleResult(String sql, List<Object> params) throws SQLException{
        Map<String, Object> map = new HashMap<String, Object>();
        int index  = 1;
        pstmt = getConnection().prepareStatement(sql);
        if(params != null && !params.isEmpty()){
            for(int i=0; i<params.size(); i++){
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();//返回查询结果
        ResultSetMetaData metaData = resultSet.getMetaData();
        int col_len = metaData.getColumnCount();
        while(resultSet.next()){
            for(int i=0; i<col_len; i++ ){
                String cols_name = metaData.getColumnName(i+1);
                Object cols_value = resultSet.getObject(cols_name);
                if(cols_value == null){
                    cols_value = "";;
                }
                map.put(cols_name, cols_value);
            }
        }
        releaseConn();
        return map;
    }

    /**查询多条记录
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public List<Map<String, Object>> findModeResult(String sql, List<Object> params) throws SQLException{
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int index = 1;
        pstmt = getConnection().prepareStatement(sql);
        if(params != null && !params.isEmpty()){
            for(int i = 0; i<params.size(); i++){
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while(resultSet.next()){
            Map<String, Object> map = new HashMap<String, Object>();
            for(int i=0; i<cols_len; i++){
                String cols_name = metaData.getColumnName(i+1);
                Object cols_value = resultSet.getObject(cols_name);;
                if(cols_value == null){
                    cols_value = "";;
                }
                map.put(cols_name, cols_value);
            }
            list.add(map);
        }
        releaseConn();
        return list;
    }

    /**通过反射机制查询单条记录
     * @param sql
     * @param params
     * @param cls
     * @return
     * @throws Exception
     */
    public <T> T findSimpleRefResult(String sql, List<Object> params,
                                     Class<T> cls )throws Exception{
        T resultObject = null;
        int index = 1;
        pstmt = getConnection().prepareStatement(sql);
        if(params != null && !params.isEmpty()){
            for(int i = 0; i<params.size(); i++){
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData  = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while(resultSet.next()){
            //通过反射机制创建一个实例
            resultObject = cls.newInstance();
            for(int i = 0; i<cols_len; i++){
                String cols_name = metaData.getColumnName(i+1);
                Object cols_value = resultSet.getObject(cols_name);
                if(cols_value == null){
                    cols_value = "";;
                }
                Field field = cls.getDeclaredField(cols_name);
                field.setAccessible(true); //打开javabean的访问权限
                field.set(resultObject, cols_value);
            }
        }
        releaseConn();
        return resultObject;

    }

    /**通过反射机制查询多条记录
     * @param sql
     * @param params
     * @param cls
     * @return
     * @throws Exception
     */
    public <T> List<T> findMoreRefResult(String sql, List<Object> params,
                                         Class<T> cls )throws Exception {
        List<T> list = new ArrayList<T>();
        int index = 1;
        pstmt = getConnection().prepareStatement(sql);
        if(params != null && !params.isEmpty()){
            for(int i = 0; i<params.size(); i++){
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData  = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while(resultSet.next()){
            //通过反射机制创建一个实例
            T resultObject = cls.newInstance();
            for(int i = 0; i<cols_len; i++){
                String cols_name = metaData.getColumnName(i+1);
                Object cols_value = resultSet.getObject(cols_name);
                if(cols_value == null){
                    cols_value = "";
                }
                Field field = cls.getDeclaredField(cols_name);
                field.setAccessible(true); //打开javabean的访问权限
                field.set(resultObject, cols_value);
            }
            list.add(resultObject);
        }
        releaseConn();
        return list;
    }

    /**
     * 释放数据库连接
     */
    public void releaseConn(){
        if(resultSet != null){
            try{
                resultSet.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        if(pstmt != null){
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection!=null && datasource!=null){
            datasource.recover(connection);
            connection = null;
        }
    }

}
