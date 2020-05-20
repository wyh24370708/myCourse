package com.course.server.util;

import com.course.server.exception.ValidatorException;
import org.springframework.util.StringUtils;

/**
 * 后端字段校验类
 */
public class ValidatorUtil {
    /**
     * 空校验 null或者 ""
     * @param str
     * @param fieldName
     */
    public static void require(String str, String fieldName){
        if (StringUtils.isEmpty(str)){
            throw new ValidatorException(fieldName + "不能为空");
        }
    }

    /**
     * 字段长度校验
     * @param str
     * @param fieldName
     * @param min
     * @param max
     */
    public static void length(String str, String fieldName, int min, int max){
        int length = 0;
        if (!StringUtils.isEmpty(str)){
            length = str.length();
        }
        if (length < min || length > max){
            throw new ValidatorException(fieldName + "长度为" + min + "~" + max);
        }
    }
}
