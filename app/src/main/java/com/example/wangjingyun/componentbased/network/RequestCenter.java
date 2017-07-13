package com.example.wangjingyun.componentbased.network;

import com.example.wangjingyun.componentbasesdk.okhttp.CommonOkHttpClient;
import com.example.wangjingyun.componentbasesdk.okhttp.listener.DisposeDataHandler;
import com.example.wangjingyun.componentbasesdk.okhttp.listener.DisposeDataListener;
import com.example.wangjingyun.componentbasesdk.okhttp.request.CommonRequest;
import com.example.wangjingyun.componentbasesdk.okhttp.request.RequestParams;
import com.example.wangjingyun.componentbasesdk.okhttp.response.CommonJsonCallback;

/**
 * Created by wangjingyun on 2017/5/2.
 */

public class RequestCenter {

    public static  void  postRequest(String url, RequestParams params,DisposeDataListener listener,Class<?> mClass){

        CommonOkHttpClient.sendRequest(CommonRequest.createPostRequest(url,params)
                ,new CommonJsonCallback(new DisposeDataHandler(listener,mClass)));

    }


    /**
     * 登录网络请求
     * @param listener 请求监听
     */
    public static  void Login(DisposeDataListener listener){

        RequestParams params=new RequestParams();
        RequestCenter.postRequest(HttpConstants.PRODUCT_LIST,params,listener,null);

    }






}
