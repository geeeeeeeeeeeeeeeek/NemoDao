/* 
 * All rights Reserved, Designed By 微迈科技
 * 2017/12/20 10:33
 */
package com.nemo.dao.xml;

import com.nemo.dao.utils.CollectionsUtils;
import org.dom4j.DocumentException;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Nemo on 2017/12/20.
 */
public class MapperScaner {

    private static List<String> files = new ArrayList<String>();

    /**
     * 开始扫描
     * @param folder
     */
    public static void scan(String folder) throws IOException, DocumentException {
        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(folder);
        while (resources.hasMoreElements()){
            URL url = resources.nextElement();
            String protocol = url.getProtocol();
            if("file".equals(protocol)){
                String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                findAllXmls(filePath,true);
            }
        }

        //挨个文件开始加载到容器中
        if(CollectionsUtils.isNotEmpty(files)){
            MapperReader mapperReader = MapperReader.instance();
            for(String filePath : files){
                mapperReader.load(filePath);
            }
        }
    }

    /**
     * 递归扫描文件夹下的xml文件
     * @param path       实际路径
     * @param recursive  是否需要迭代下去查询
     */
    private static void findAllXmls(String path, final boolean recursive){
        File dir = new File(path);
        //如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        //如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            //自定义过滤规则 如果可以循环(包含子目录) 或则是以.xml结尾的文件
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".xml"));
            }
        });
        //循环所有文件
        for (File file : dirfiles) {
            //如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAllXmls(file.getAbsolutePath(), recursive);
            }
            else {
                files.add(file.getAbsolutePath());
            }
        }
    }

}
