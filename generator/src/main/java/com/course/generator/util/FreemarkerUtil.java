package com.course.generator.util;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FreemarkerUtil {
    /**
     * ftlPath: 文件模板所在的文件夹路径
     */
    private static String ftlPath = "generator\\src\\main\\java\\com\\course\\generator\\ftl\\";
    private static Template temp = null;

    /**
     * freemarker初始化
     * @param fileName_from
     * @throws IOException
     */
    public static void initConfig(String fileName_from) throws IOException {
        Configuration config = new Configuration(Configuration.VERSION_2_3_29);
        config.setDirectoryForTemplateLoading(new File(ftlPath));
        config.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_29));
        temp = config.getTemplate(fileName_from);
    }

    /**
     * 生成模板文件
     * @param fileName_to
     * @throws IOException
     * @throws TemplateException
     */
    public static void generator(String fileName_to, Map<String,Object> map) throws IOException, TemplateException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName_to));
        temp.process(map,bw);
        bw.flush();
        bw.close();
    }

}
