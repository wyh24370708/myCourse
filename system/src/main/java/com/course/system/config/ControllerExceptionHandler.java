package com.course.system.config;

import com.course.server.dto.ResponseDto;
import com.course.server.exception.BusinessException;
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
    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * 处理校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public ResponseDto validatorExceptionHandler(BusinessException e){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(false);
        LOG.warn("业务异常:{}",e.getMessage());
        responseDto.setMessage(e.getMessage());
        return responseDto;
    }

}
