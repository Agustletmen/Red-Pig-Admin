package com.redpig.util;

public enum ResultCode {

    SUCCESS(200,"成功"),
    ERROR(500,"失败"),
    USERNAME_NOT_FOUND(1002,"用户名未找到");

    private Integer code;
    private String data;

    private ResultCode(){}

    private ResultCode(Integer code,String data){
        this.code = code;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return data;
    }
}

