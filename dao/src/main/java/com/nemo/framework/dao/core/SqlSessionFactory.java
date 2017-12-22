/* 
 * All rights Reserved, Designed By 微迈科技
 * 2017/12/20 19:18
 */
package com.nemo.framework.dao.core;

/**
 * sql会话工厂
 * Created by Nemo on 2017/12/20.
 */
public class SqlSessionFactory {

    private SqlSessionFactory(){

    }

    /**
     * 得到一个会话
     * TODO 这里应该是从一个会话池中获取,后续做
     * @return
     */
    public static SqlSession getSession(){
        return SqlSession.instance();
    }

}

