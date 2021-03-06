package com.example.wangjingyun.componentbasesdk.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/29.
 */

public interface  HttpEngine {

    // post 提交
    public void post(Context context, String url, Map<String, String> params, Map<String, String> headParams, HttpCallBack httpCallBack);
    // get 提交
    public void get(Context context, String url, Map<String, String> params, Map<String, String> headParams, HttpCallBack httpCallBack);
    // 取消请求
    public void cancelAll(Context context);
    // 下载文件
    public void downLoadFiles(Context context, String url, String saveFileDir, HttpCallBackProgress httpCallBackProgress);
    // 上传文件
    public void sendMultipart(Context context, String url, Map<String, Object> params, Map<String, String> headParams, HttpCallBack httpCallBack);
    //webSocket长连接
    public void connectionWebSocket(Context context, String url, WebSocketCallBackListener webSocketCallBackListener);

    // https添加安全证书


}
