/* 
 * All rights Reserved, Designed By 微迈科技
 * 2017/12/19 15:37
 */
package com.nemo.dao.datasource;

import com.nemo.dao.utils.PropertiesUtils;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * 简单的数据源相关
 * Created by Nemo on 2017/12/19.
 */
public class SimpleDatasource implements DataSource {

    //mysql数据库驱动
    private static final String driver = PropertiesUtils.loadProp("jdbc.driver");
    //数据库连接地址
    private static final String url = PropertiesUtils.loadProp("jdbc.url");
    //数据库的用户名
    private static final String username = PropertiesUtils.loadProp("jdbc.username");
    //数据库的密码
    private static final String password = PropertiesUtils.loadProp("jdbc.password");
    //
    private static final int maxSize = PropertiesUtils.loadIntValue("datasource.max.size");
    //
    private static final int initSize = PropertiesUtils.loadIntValue("datasource.init.size");

    //连接池
    private static LinkedList<Connection> pool = new LinkedList<Connection>();

    private static SimpleDatasource instance = null;

    //静态代码块负责加载驱动
    static {
        try {
            Class.forName(driver);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private SimpleDatasource(){
    }

    /**
     * 获取数据源单例
     * @return
     */
    public static SimpleDatasource instance() {
        if (instance == null) {
            instance = new SimpleDatasource();
            //初始化连接池
            for(int i=0;i<initSize;i++){
                try {
                    Connection connection = instance.getConnection(username, password);
                    pool.add(connection);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return instance;
    }

    /**
     * 得到一个数据库连接
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        synchronized (pool) {
            if (pool.size() > 0) {
                //如果连接池中还有剩余连接，则取即可，否则需要新建连接
                return pool.removeFirst();
            } else{
              Connection connection = getConnection(username,password);
              recover(connection);
              return connection;
            }
        }
    }

    /**
     * 回收连接
     * @param connection
     */
    public void recover (Connection connection){
        synchronized(pool) {
            //不超过最大的池连接量，新增到池中
            if (pool.size() < maxSize) {
                pool.add(connection);
            }
        }
    }

    /**
     * 根据用户名 + 密码得到一个连接
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public Connection getConnection(String username, String password) throws SQLException {
        return DriverManager.getConnection(url,username,password);
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    public void setLoginTimeout(int seconds) throws SQLException {

    }

    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
