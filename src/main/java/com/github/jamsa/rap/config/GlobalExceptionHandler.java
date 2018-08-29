package com.github.jamsa.rap.config;


//import com.github.jamsa.rap.model.ResponseData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhujie on 2018/1/9.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
    public static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity runtimeException(HttpServletRequest request, Exception e) throws Exception {
        logger.error("运行时错误",e);
        return ResponseEntity.badRequest().body(e);
        //return ResponseData.error(e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    //@ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity notFound(HttpServletRequest request) throws Exception {
        //return ResponseData.error("请求的地址不存在");
        logger.error("请求的地址不存在"+request.getRequestURI());
        return ResponseEntity.notFound().build();
    }

}
