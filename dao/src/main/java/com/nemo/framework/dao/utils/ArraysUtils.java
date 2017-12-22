/* 
 * All rights Reserved, Designed By 微迈科技
 * 2017/12/20 19:00
 */
package com.nemo.framework.dao.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nemo on 2017/12/20.
 */
public class ArraysUtils {

    /**
     * 数组是否包含某个元素
     * @param arr
     * @param str
     * @return
     */
    public static boolean include(String arr[],String str){
        Set<String> set = new HashSet<String>(Arrays.asList(arr));
        return set.contains(str);
    }

}
