package com.course.generator.server;

import com.course.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;

public class ServerGenerator {

    private static String toServicePath = "server\\src\\main\\java\\com\\course\\server\\service\\";
    private static String toServiceImplPath = "server\\src\\main\\java\\com\\course\\server\\service\\impl\\";
    private static String Domain = null;
    private static String domain = null;

    public static void main(String[] args) throws IOException, TemplateException {

        HashMap<String, Object> map = getDomainMap(Domain,domain);
        //生成service接口
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toServicePath + map.get("Domain") + "Service.java", map);
        //生成serviceImpl
        FreemarkerUtil.initConfig("serviceImpl.ftl");
        FreemarkerUtil.generator(toServiceImplPath + map.get("Domain") + "ServiceImpl.java", map);

    }

    /**
     * 存储实体类的名字
     * @param domain
     * @param domain1
     * @return
     */
    private static HashMap<String, Object> getDomainMap(String domain, String domain1) {
        Domain = "Section";
        domain = "section";
        HashMap<String, Object> map = new HashMap<>();
        map.put("Domain",Domain);
        map.put("domain",domain);
        return map;
    }
}
