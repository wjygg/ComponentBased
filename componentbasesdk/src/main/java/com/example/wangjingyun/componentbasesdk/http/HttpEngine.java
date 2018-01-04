package com.example.wangjingyun.componentbasesdk.http;

import android.content.Context;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/29.
 */

public interface  HttpEngine {

    // post 提交
    public void post(Context context, String url, Map<String, String> params, HttpCallBack httpCallBack, boolean cache);

    // get 提交
    public void get(Context context, String url, Map<String, String> params, HttpCallBack httpCallBack, boolean cache);

    // 取消请求

    // 下载文件
    public void downLoadFiles(Context context, String url, String saveFileDir,HttpCallBackProgress httpCallBackProgress);
    // 上传文件
    public void sendMultipart(Context context, String url, Map<String, Object> params, HttpCallBack httpCallBack, boolean cache);
    // https添加安全证书


}
