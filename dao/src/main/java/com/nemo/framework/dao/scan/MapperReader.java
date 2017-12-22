/* 
 * All rights Reserved, Designed By 微迈科技
 * 2017/12/20 10:08
 */
package com.nemo.framework.dao.scan;

import com.nemo.framework.dao.core.Context;
import com.nemo.framework.dao.utils.ArraysUtils;
import com.nemo.framework.dao.utils.CollectionsUtils;
import com.nemo.framework.dao.utils.StringUtils;
import com.nemo.framework.dao.scan.bean.MapperBean;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;

/**
 * Created by Nemo on 2017/12/20.
 */
public class MapperReader {

    private static MapperReader mapperReader = null;

    //private Map<String,MapperBean> mappers = new HashMap<String, MapperBean>();
    private static Context context = Context.instance();

    private String namespace;

    private String filePath;

    private MapperReader(){

    }

    public static MapperReader instance(){
        if(mapperReader == null){
            mapperReader = new MapperReader();
        }
        return mapperReader;
    }

    public void load(String path) throws DocumentException {
        this.filePath = path;

        SAXReader reader = new SAXReader();
        Document document = reader.read(path);
        Element rootElement = document.getRootElement();

        if(rootElement == null){
            return;
        }

        dealNamespace(rootElement);
        dealDetailElements(rootElement);
    }

    /**
     * 处理命名空间
     * @param rootElement
     */
    private void dealNamespace(Element rootElement){
        String name = rootElement.getName();

        if(StringUtils.isEmpty(name) || !name.equals("mapper")){
            throw new RuntimeException("文件："+filePath+"没有定义mapper节点");
        }

        Attribute attribute = rootElement.attribute("namespace");
        if(attribute == null || attribute.getData() == null){
            throw new RuntimeException("文件:"+filePath+"找不到namespace定义");
        }
        namespace = attribute.getData().toString();
    }

    /**
     * 处理详细的节点列表
     * @param rootElement
     */
    private void dealDetailElements(Element rootElement){
        //先得到下面所有的节点
        List<Element> elements = rootElement.elements();
        for(Element element : elements){
            dealElement(element);
        }
    }

    /**
     * 处理单个节点
     * @param element
     */
    private void dealElement(Element element){
        String type = element.getName();

        String []types = {"select","update","delete","insert"};
        if(!ArraysUtils.include(types,type)){
            throw new RuntimeException("抱歉，文件："+filePath+"定义了不能解析的元素："+type);
        }

        String id = getId(element);
        String parameterType = getParameterType(element,id);
        String resultType = getResultType(element,id);
        String sql = getSql(element,id);
        add2mappers(type,parameterType,resultType,id,sql);
    }

    /**
     * 根据类别来处理节点数据
     * @param rootElement
     * @param type
     */
    private void dealElementByType(Element rootElement,String type){
        List<Element> elements = rootElement.selectNodes(type);
        if(CollectionsUtils.isNotEmpty(elements)){
            for(Element element : elements){
                String id = getId(element);
                String parameterType = getParameterType(element,id);
                String resultType = getResultType(element,id);
                String sql = getSql(element,id);
                add2mappers(type,parameterType,resultType,id,sql);
            }
        }
    }

    /**
     * 得到ID
     * @param element
     * @return
     */
    private String getId(Element element){
        Attribute attribute = element.attribute("id");
        if(attribute == null || attribute.getData() == null || attribute.getData().toString().trim().equals("")){
            throw new RuntimeException("抱歉，您的mapper:"+filePath+"存在问题：某个节点不含ID参数");
        }
        return attribute.getData().toString();
    }

    /**
     * 得到ResultType
     * @param element
     * @param id
     * @return
     */
    private String getResultType(Element element,String id){
        Attribute attribute = element.attribute("resultType");
        if(attribute == null || attribute.getData() == null || attribute.getData().toString().trim().equals("")){
            throw new RuntimeException("抱歉，您的mapper："+filePath+"Id:"+id+"存在问题：不含resultType字段。");
        }
        return attribute.getData().toString();
    }

    /**
     * 得到parameterType
     * @param element
     * @param id
     * @return
     */
    private String getParameterType(Element element,String id){
        Attribute attribute = element.attribute("parameterType");
        if(attribute == null || attribute.getData() == null || attribute.getData().toString().trim().equals("")){
            throw new RuntimeException("抱歉，您的mapper："+filePath+"Id:"+id+"存在问题：不含parameterType字段。");
        }
        return attribute.getData().toString();
    }

    /**
     * 得到Sql
     * @param element
     * @return
     */
    private String getSql(Element element,String id){
        Object obj = element.getData();
        if(obj == null || obj.toString().trim().equals("")){
            throw new RuntimeException("抱歉，您的mapper："+filePath+"Id:"+id+"存在问题：不含Sql字段。");
        }
        return obj.toString();
    }

    /**
     * 添加mapper到sql上下文
     * @param type
     * @param parameterType
     * @param resultType
     * @param id
     * @param sql
     */
    private void add2mappers(String type,String parameterType,String resultType,String id,String sql){

        String mapperKey = namespace + "." +id;

        MapperBean mapperBean = context.getMapper(mapperKey);
        if(mapperBean!=null){
            throw new RuntimeException("抱歉，重复定义。");
        }
        if("select".equals(type)) {
            try {
                Class.forName(resultType);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("抱歉，您的mapper：" + filePath + "ID:" + id + "存在问题：找不到结果定义" + resultType);
            }
            try {
                Class.forName(parameterType);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("抱歉，您的mapper：" + filePath + "ID:" + id + "存在问题：找不到参数定义" + parameterType);
            }
        }else {
            type = "long";
        }
        mapperBean = new MapperBean();
        mapperBean.setId(id);
        mapperBean.setType(type);
        mapperBean.setParameterType(parameterType);
        mapperBean.setResultType(resultType);
        mapperBean.setSql(sql);
        context.addMapper(mapperKey,mapperBean);
    }

}
