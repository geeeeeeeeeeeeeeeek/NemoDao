/* 
 * All rights Reserved, Designed By 微迈科技
 * 2017/12/22 12:39
 */
package com.nemo.framework.dao.scan;

import com.nemo.framework.dao.bean.PropKey;
import com.nemo.framework.dao.core.Context;
import com.nemo.framework.dao.utils.PropertiesUtils;
import com.nemo.framework.dao.utils.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * TODO dao扫描器
 * Created by Nemo on 2017/12/22.
 */
public class DaoScaner {

    private static String daoBase = PropertiesUtils.loadProp(PropKey.DAO_BASE);

    private static String daoBasePath;

    private static Context context = Context.instance();

    public static void scan(){
        loanDaoBase();
        getAllDao();
    }

    /**
     * 得到所有的dao实例
     * @return
     */
    private static void getAllDao() {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        try {
            //读取指定package下的所有class
            Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(daoBasePath);
            while (dirs.hasMoreElements()){
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                //判断是否以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    //获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    //以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(daoBase, filePath, true, classes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //添加到上下文
        for (Class<?> clazz : classes) {
            context.addDao(clazz.getName(),clazz);
        }
    }

    /**
     * 加载dao根目录
     */
    private static void loanDaoBase(){
        if (StringUtils.isEmpty(daoBase)) {
            throw new RuntimeException("抱歉，请配置dao包路径。");
        }
        daoBasePath = daoBase.replace('.', '/');
    }

    /**
     * 根据包名，取得其下的所有类文件
     * @param packageName 包名
     * @param packagePath 包的实际路径
     * @param recursive  是否需要迭代下去查询
     * @param classes 存放类对象的集合
     */
    private static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes){
        //获取此包的目录 建立一个File
        File dir = new File(packagePath);
        //如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        //如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        //循环所有文件
        for (File file : dirfiles) {
            //如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                        file.getAbsolutePath(),
                        recursive,
                        classes);
            }
            else {
                //如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    //添加到集合中去
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
