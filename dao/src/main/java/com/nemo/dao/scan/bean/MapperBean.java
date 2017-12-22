/* 
 * All rights Reserved, Designed By 微迈科技
 * 2017/12/20 10:44
 */
package com.nemo.dao.scan.bean;

/**
 * Mapper容器Bean
 * Created by Nemo on 2017/12/20.
 */
public class MapperBean {

    private String type;

    private String id;

    private String parameterType;

    private String resultType;

    private String sql;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
