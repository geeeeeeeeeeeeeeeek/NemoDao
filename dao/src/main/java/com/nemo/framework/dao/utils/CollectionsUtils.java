/* 
 * All rights Reserved, Designed By 微迈科技
 * 2017/12/20 10:39
 */
package com.nemo.framework.dao.utils;

import java.util.Collection;

/**
 * Created by Nemo on 2017/12/20.
 */
public class CollectionsUtils {

    /**
     * 获取集合是否为空
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection collection){
        return (collection == null || collection.isEmpty());
    }

    /**
     * 获取集合是否不为空
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection collection){
        return !isEmpty(collection);
    }

}
