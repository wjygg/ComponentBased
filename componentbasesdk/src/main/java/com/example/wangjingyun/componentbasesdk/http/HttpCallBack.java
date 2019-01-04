package com.example.wangjingyun.componentbasesdk.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/29.
 */

public interface HttpCallBack {

    //公共请求参数准备
    public void onPreParameters(Context context, Map<String, String> params,Map<String, Object> fileParams, Map<String, String> headParams, boolean isPreParameters, boolean isPreUpParameters, boolean isPreHeadParameters);
    //错误回调
    public void onError(Exception e);
    //成功回调
    public void onSucceed(Object result);

}
