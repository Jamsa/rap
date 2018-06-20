package com.github.jamsa.rap.model;

/**
 * Created by zhujie on 2018/1/9.
 */
public class ResponseData {
    private int code;
    private String message;

    public static final int SUCCESS_CODE=2000;
    public static final int ERROR_CODE=-2000;

    public ResponseData(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseData success(String message){
        return new ResponseData(SUCCESS_CODE,message);
    }

    public static ResponseData error(String message){
        return new ResponseData(ERROR_CODE,message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
