package com.course.server.exception;

/**
 * 校验异常类
 */
public class ValidatorException extends RuntimeException{

    public ValidatorException(String message) {
        super(message);//父类的message传入
    }
}
