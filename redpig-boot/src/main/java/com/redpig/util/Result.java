package com.redpig.util;

import cn.hutool.json.JSONUtil;
import lombok.Data;

@Data
public class Result {
    private Integer code;

    private Object data;

    public static Result result(ResultCode resultCode, Object data){
        Result result = new Result();
        result.code = resultCode.getCode();
        result.data = data;
        return result;
    }

    public static Result success(Object data){
        Result result = new Result();
        ResultCode resultCode = ResultCode.SUCCESS;
        result.code = resultCode.getCode();
        result.data = data;
        return result;
    }

    public static Result success(){
        return success("ok");
    }

    public static String okJSON(Object data){
        return JSONUtil.toJsonStr(success(data));
    }

    public static String okJSON(){
        return JSONUtil.toJsonStr(ok());
    }

    public static Result ok(Object data){
        return success(data);
    }

    public static Result ok(){
        return success("ok");
    }

    public static Result error(Object data){
        Result result = new Result();
        ResultCode resultCode = ResultCode.ERROR;
        result.code = resultCode.getCode();
        result.data = data;
        return result;
    }

    public static String errorJSON(Object result) {
        return JSONUtil.toJsonStr(error(result));
    }

    public static void main(String[] args) {
        System.out.println(success("哈哈哈"));
        System.out.println(error("失败。。。"));
    }
}
