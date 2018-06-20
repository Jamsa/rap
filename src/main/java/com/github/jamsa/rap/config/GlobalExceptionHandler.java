package com.github.jamsa.rap.config;


//import com.github.jamsa.rap.model.ResponseData;
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

    //@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity runtimeException(HttpServletRequest request, Exception e) throws Exception {
        return ResponseEntity.badRequest().body(e.getMessage());
        //return ResponseData.error(e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    //@ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity notFound(HttpServletRequest request) throws Exception {
        //return ResponseData.error("请求的地址不存在");
        return ResponseEntity.notFound().build();
    }

}
