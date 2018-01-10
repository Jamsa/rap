package com.github.jamsa.rap.model;

/**
 * Created by zhujie on 2018/1/9.
 */
public class ResponseData {
    private int code;
    private String message;
    private Object data;

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final int SUCCESS_CODE=2000;
    public static final int ERROR_CODE=-2000;

    public ResponseData(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseData success(int code, Object data){
        return new ResponseData(code,SUCCESS,data);
    }

    public static ResponseData success(Object data){
        return success(SUCCESS_CODE,data);
    }

    public static ResponseData error(int code, Object data){
        return new ResponseData(ERROR_CODE,ERROR,data);
    }

    public static ResponseData error(Object data){
        return error(ERROR_CODE,data);
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
