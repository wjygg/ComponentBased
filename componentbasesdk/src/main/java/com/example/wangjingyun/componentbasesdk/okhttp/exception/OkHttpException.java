package com.example.wangjingyun.componentbasesdk.okhttp.exception;

/**
 *
 * 自定义异常类
 *
 * Created by wangjingyun on 2017/3/15.
 */

public class OkHttpException extends Exception{

    //错误码
    private int ecode;
    //错误 类
    private Object emsg;

    public OkHttpException(int ecode,Object emsg){

        this.ecode=ecode;
        this.emsg=emsg;
    }

    public int getEcode() {
        return ecode;
    }

    public Object getEmsg() {
        return emsg;
    }
}
