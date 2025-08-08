package com.rabbiter.am.config;

import java.io.Serializable;
/**
 * 统一API返回json工具类
 */
public class Result implements Serializable {

    private Integer code;
    private String message;
    private Object data;


    public Result() {
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Result success(){
        Result res = new Result();
        res.setResultCode(ResultCode.SUCCESS);
        return res;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static Result failure(ResultCode resultCode) {
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setMessage(resultCode.message());
        return result;
    }

    public static Result failure(ResultCode resultCode, Object data) {
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }
    public void setResultCode(ResultCode code) {
        this.code = code.code();
        this.message = code.message();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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
