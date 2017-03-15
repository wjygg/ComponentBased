package com.example.wangjingyun.componentbasesdk.okhttp.listener;

/**
 * Created by Administrator on 2017/3/15.
 */

public class DisposeDataHandler {

    public DisposeDataListener mListener;

    //Class<T> 代表一个具体的类 Class<?> 代表 一个 类型
    public Class<?> mClass =null;

    public DisposeDataHandler (DisposeDataListener listener){
        this.mListener=listener;
    }
    public DisposeDataHandler (DisposeDataListener listener,Class<?> mClass){

        this.mListener=listener;

        this.mClass=mClass;
    }
}
