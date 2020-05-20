package com.course.business.controller.admin;

import com.course.server.dto.ResponseDto;
import com.course.server.exception.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * controller统一异常处理类
 *
 * 一般是做try..catch
 * 统一异常处理就是抛出异常之后,通过@ExceptionHandler(value = ValidatorException.class)接收并处理异常
 *
 */
@RestControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);

    /**
     * 处理校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = ValidatorException.class)
    public ResponseDto validatorExceptionHandler(ValidatorException e){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(false);
        LOG.warn(e.getMessage());
        responseDto.setMessage("请求参数异常!");
        return responseDto;
    }

}
