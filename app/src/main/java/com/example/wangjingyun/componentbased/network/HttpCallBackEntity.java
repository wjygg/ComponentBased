package com.example.wangjingyun.componentbased.network;


import com.alibaba.fastjson.JSONObject;
import com.example.wangjingyun.componentbased.entity.CallBackEntity;
import com.example.wangjingyun.componentbasesdk.http.HttpCallBack;



/**
 * Created by Administrator on 2017/12/5.
 */

public abstract class HttpCallBackEntity<T> implements HttpCallBack{


    @Override
    public void onSucceed(String result) {

        CallBackEntity callBackEntity = (CallBackEntity<T>)JSONObject.parseObject(result, CallBackEntity.class);

    }

    public abstract void onSuccess(T t);



}
