/* 
 * All rights Reserved, Designed By 微迈科技
 * 2017/12/20 18:48
 */
package com.nemo.framework.dao.utils;

/**
 * Created by Nemo on 2017/12/20.
 */
public class StringUtils {

    /**
     * 判断字符串是否为空，null ”“
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return str==null||str.trim().equals("");
    }

    /**
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

}
