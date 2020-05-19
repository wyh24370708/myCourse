package com.course.server.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * dto 和 domain之间的转换
 */
public class CopyUtil {

    /**
     * 单个实体类的转换
     */
    public static <T> T copy(Object source, Class<T> targetClass){
        //判断源实例不能为空
        if (source == null ){
            return null;
        }
        //创建目标实例的对象
        T obj = null;
        try {
            obj = targetClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //转换
        BeanUtils.copyProperties(source,obj);
        return obj;
    }

    /**
     * 实体类的list集合的转换
     */
    public static <T> List<T> copyList(List source,Class<T> targetClass){
        //创建目标list
        List<T> targetList = new ArrayList<>();
        if (source.isEmpty()){
            return null;
        }else{
            for (Object obj : source) {
                T tClass = copy(obj, targetClass);
                targetList.add(tClass);
            }
        }
        return targetList;
    }
}
