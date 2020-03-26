package com.macro.mall.controller.advice;

import com.macro.api.CommonResult;
import com.macro.excepiton.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author kzc
 */
@Slf4j
@RestControllerAdvice
public class AdviceController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    public CommonResult exceptionHandler(Exception ex){
        log.info(ex.getMessage());
        if(ex instanceof BusinessException){
            return CommonResult.failed("业务处理异常:"+ex.getMessage());
        } else if(ex instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) ex;
            return CommonResult.failed("参数校验错误:"+e.getBindingResult().getFieldError().getDefaultMessage());
        }
        return CommonResult.failed(ex.getMessage());
    }
}
