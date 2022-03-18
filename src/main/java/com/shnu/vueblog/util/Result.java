package com.shnu.vueblog.util;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一结果集封装
 */
@Data
public class Result implements Serializable {
    private int code;  //200表示正常，非200表示异常
    private  String msg;
    private Object data;


    public static Result success(int code,String msg,Object data){
        Result re = new Result();
        re.code = code;
        re.msg = msg;
        re.data = data;
        return re;
    }
    public static Result success(Object data){
        return success(200,"操作成功！",data);
    }
    public  static Result fail(int code,String msg,Object data){
        Result re = new Result();
        re.code = code;
        re.msg = msg;
        re.data = data;
        return re;
    }
    public static Result fail(int code,String msg){
        return fail(code,msg,null);
    }
    public static Result fail(String msg){
        return fail(400,msg,null);
    }
    public static Result fail(String msg,Object data){
        return fail(400,msg,data);
    }
}
