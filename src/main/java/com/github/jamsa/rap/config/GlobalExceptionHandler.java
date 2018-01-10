package com.github.jamsa.rap.config;


import com.github.jamsa.rap.model.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhujie on 2018/1/9.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseData<String> runtimeException(HttpServletRequest request, Exception e) throws Exception {
        ResponseData<String> responseData = new ResponseData<>();
        responseData.setData(e.getMessage());
        responseData.setMessage("error");
        responseData.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return responseData;
    }

}
