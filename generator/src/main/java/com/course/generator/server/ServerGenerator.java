package com.course.generator.server;

import com.course.generator.util.DbUtil;
import com.course.generator.util.Field;
import com.course.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 服务层代码生成器
 *      |controller
 *      |service
 *      |serviceImpl
 *
 *  注意: 每次运行此文件都会重新生成,且覆盖相同名称的文件
 */
public class ServerGenerator {

    private static String MODULE = "file";
    private static String toDtoPath = "server\\src\\main\\java\\com\\course\\server\\dto\\";
    private static String toServicePath = "server\\src\\main\\java\\com\\course\\server\\service\\";
    private static String toServiceImplPath = "server\\src\\main\\java\\com\\course\\server\\service\\impl\\";
    private static String toControllerPath =
            MODULE+"\\src\\main\\java\\com\\course\\"+MODULE+"\\controller\\admin\\";
    static String generatorConfigPath = "server\\src\\main\\resources\\generator\\generatorConfig.xml";

    public static void main(String[] args) throws Exception {

        HashMap<String, Object> map = getDomainMap();
        //生成dto
        FreemarkerUtil.initConfig("dto.ftl");
        FreemarkerUtil.generator(toDtoPath + map.get("Domain") + "Dto.java",map);
        //生成service接口
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toServicePath + map.get("Domain") + "Service.java", map);
        //生成serviceImpl
        FreemarkerUtil.initConfig("serviceImpl.ftl");
        FreemarkerUtil.generator(toServiceImplPath + map.get("Domain") + "ServiceImpl.java", map);
        //生成controller
        FreemarkerUtil.initConfig("controller.ftl");
        FreemarkerUtil.generator(toControllerPath + map.get("Domain") + "Controller.java", map);

    }

    /**
     * 存储实体类的名字
     * @return
     */
    private static HashMap<String, Object> getDomainMap() throws Exception {
        String module = MODULE;
        // 只生成配置文件中的第一个table节点
        File file = new File(generatorConfigPath);
        SAXReader reader=new SAXReader();
        //读取xml文件到Document中
        Document doc=reader.read(file);
        //获取xml文件的根节点
        Element rootElement=doc.getRootElement();
        //读取context节点
        Element contextElement = rootElement.element("context");
        //定义一个Element用于遍历
        Element tableElement;
        //取第一个“table”的节点
        tableElement=contextElement.elementIterator("table").next();
        String Domain = tableElement.attributeValue("domainObjectName");
        String tableName = tableElement.attributeValue("tableName");
        String tableNameCN = DbUtil.getTableComment(tableName);
        String domain = Domain.substring(0, 1).toLowerCase() + Domain.substring(1);
        System.out.println("表："+tableElement.attributeValue("tableName"));
        System.out.println("Domain："+tableElement.attributeValue("domainObjectName"));

        List<Field> fieldList = DbUtil.getColumnByTableName(tableName);
        Set<String> typeSet = getJavaTypes(fieldList);
        HashMap<String, Object> map = new HashMap<>();
        map.put("Domain",Domain);
        map.put("domain",domain);
        map.put("module",module);
        map.put("tableNameCN",tableNameCN);
        map.put("fieldList",fieldList);
        map.put("typeSet",typeSet);
        return map;
    }

    /**
     * 获取所有的Java类型，使用Set去重
     * @param fieldList
     * @return
     */
    private static Set<String> getJavaTypes(List<Field> fieldList) {
        Set<String> set = new HashSet<>();
        for (Field field : fieldList) {
            set.add(field.getJavaType());
        }
        return set;
    }


}
