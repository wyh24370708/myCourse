package com.course.generator.enums;

import com.course.server.domain.Course;
import com.course.server.enums.*;
import org.apache.tomcat.jni.OS;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnumGenerator {
    //文件生成的地址
    static String toEnumsPath = "admin\\public\\static\\js\\enums.js";

    public static void main(String[] args) throws Exception {
        StringBuffer bufferObject = new StringBuffer();
        StringBuffer bufferArray = new StringBuffer();
        long start = System.currentTimeMillis();
        try {
            toJson(SectionChargeEnum.class, bufferObject, bufferArray);
            toJson(CourseChargeEnum.class, bufferObject, bufferArray);
            toJson(CourseLevelEnum.class, bufferObject, bufferArray);
            toJson(CourseStatusEnum.class, bufferObject, bufferArray);
            toJson(ProfileUseEnum.class, bufferObject, bufferArray);

            StringBuffer buffer = bufferObject.append("\r\n").append(bufferArray);
            System.out.println(buffer);
            writeJs(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("执行耗时" + (end - start) + "毫秒");
    }

    private static void toJson(Class<?> clazz, StringBuffer bufferObject, StringBuffer bufferArray) throws Exception {
        String key = toUnderLine(clazz.getSimpleName());
//        System.out.println(key);//SECTION_CHARGE
        toJson(clazz, bufferObject, bufferArray, key);
    }

    private static void toJson(Class<?> clazz, StringBuffer bufferObject, StringBuffer bufferArray, String key) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object[] enumConstants = clazz.getEnumConstants();
//        System.out.println(Arrays.toString(enumConstants));
        //[SectionChargeEnum{code='C', desc='收费'}, SectionChargeEnum{code='C', desc='免费'}]
        Method name = clazz.getMethod("name");
        Method getDesc = clazz.getMethod("getDesc");
        Method getCode = clazz.getMethod("getCode");

        //生成对象
        bufferObject.append(key).append("={");
        for (int i = 0, LEN = enumConstants.length; i < LEN; i++) {
            Object obj = enumConstants[i];
            if (getCode == null) {
                bufferObject.append(name.invoke(obj)).append(":{key:\"").append(name.invoke(obj)).append("\", value:\"").append(getDesc.invoke(obj)).append("\"}");
            } else {
                bufferObject.append(name.invoke(obj)).append(":{key:\"").append(getCode.invoke(obj)).append("\", value:\"").append(getDesc.invoke(obj)).append("\"}");
            }
            if (i < enumConstants.length - 1) {
                bufferObject.append(",");
            }
        }
        bufferObject.append("};\r\n");

        // 生成数组
        bufferArray.append(key).append("_ARRAY=[");
        for (int i = 0; i < enumConstants.length; i++) {
            Object obj = enumConstants[i];
            if (getCode == null) {
                bufferArray.append("{key:\"").append(name.invoke(obj)).append("\", value:\"").append(getDesc.invoke(obj)).append("\"}");
            } else {
                bufferArray.append("{key:\"").append(getCode.invoke(obj)).append("\", value:\"").append(getDesc.invoke(obj)).append("\"}");
            }
            if (i < enumConstants.length - 1) {
                bufferArray.append(",");
            }
        }
        bufferArray.append("];\r\n");

    }

    /**
     * 写文件
     * @param buffer
     */
    private static void writeJs(StringBuffer buffer) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(toEnumsPath);
            OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8");
            osw.write(buffer.toString());
            osw.flush();
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 功能：驼峰转大写下划线，并去掉_ENUM
     * 如：SectionChargeEnum 变成 SECTION_CHARGE
     * @param simpleName
     * @return
     */
    private static String toUnderLine(String simpleName) {
        StringBuffer result = underLine(simpleName);
        return result.substring(1,result.length()).toUpperCase().trim().replace("_ENUM","");
    }

    /**
     * 驼峰转下划线，第一位是下划线
     * 如：SectionChargeEnum 变成 _section_charge_enum
     * 注意每次只能转换一个,所以需要递归调用
     * @param simpleName
     * @return
     */
    private static StringBuffer underLine(String simpleName) {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher m = pattern.matcher(simpleName);
        StringBuffer sb = new StringBuffer(simpleName);
        if (m.find()){
            sb = new StringBuffer();
            m.appendReplacement(sb,"_"+m.group(0).toLowerCase());
            m.appendTail(sb);
        }else{
            return sb;
        }
        return underLine(sb.toString());
    }
}
