package com.example.wangjingyun.componentbased.network;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.example.wangjingyun.componentbased.entity.CallBackEntity;
import com.example.wangjingyun.componentbasesdk.http.HttpCallBack;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Created by Administrator on 2017/12/5.
 */

public abstract class HttpCallBackEntity<T> implements HttpCallBack{


    @Override
    public void onSucceed(String result) {
        //Class<T> t t的字节码对象 T t T类型的t 对象
        T t = (T) JSONObject.parseObject(result, analyticData(this).getClass());

        onSuccess(t);
    }

    public abstract void onSuccess(T t);

    //解析字符串

    public <T> T analyticData(Object clss){

        Type genericSuperclass = clss.getClass().getGenericSuperclass();

        Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();

        return (T) actualTypeArguments[0];
    }

}
