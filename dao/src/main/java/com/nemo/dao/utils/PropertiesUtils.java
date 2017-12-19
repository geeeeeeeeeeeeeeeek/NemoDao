/* 
 * All rights Reserved, Designed By 微迈科技
 * 2017/9/19 11:17
 */
package com.nemo.dao.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 配置相关操作工具
 * Created by Nemo on 2017/9/19.
 */
public class PropertiesUtils {

    //配置信息
    private static Map<String,String> params = new HashMap<String, String>();

    /**
     * 配置文件名称
     */
    private static String propname = "config.properties";

    /**
     * 获取配置参数
     * @param name
     * @return
     */
    public static String loadProp(String name){
        if(name == null){
          return null;
        }
        if(params.size()<=0){
            try {
                InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream(propname);;
                Properties props = new Properties();
                props.load(in);

                for(Object tempName : props.keySet()){
                    params.put(tempName.toString(),props.getProperty(tempName.toString()));
                }

                return props.getProperty(name);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }else{
            return params.get(name);
        }
    }

    /**
     * 获取整数类型参数
     * @param name
     * @return
     */
    public static int loadIntValue(String name){
        String str = loadProp(name);
        try{
            return Integer.parseInt(str);
        }catch (Exception e){
            return 0;
        }
    }

}
