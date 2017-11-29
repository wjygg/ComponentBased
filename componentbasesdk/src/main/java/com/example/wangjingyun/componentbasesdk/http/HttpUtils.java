package com.example.wangjingyun.componentbasesdk.http;

import android.content.Context;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/29.
 */

public class HttpUtils {
    // 上下文
    private Context mContext;
    // 网络访问引擎
    private static OkHttpEngine mHttpEngine = new OkHttpEngine();
    // 接口地址
    private String mUrl;
    // 请求参数
    private Map<String, String> mParams;
    // get请求标识
    private final int GET_REQUEST = 0x0011;
    // post请求标识
    private final int POST_REQUEST = 0x0022;
    // 请求的方式
    private int mRequestMethod = GET_REQUEST;

    // 是否缓存
    private boolean mCache = false;

    // 切换引擎
    public void exchangeEngine(OkHttpEngine httpEngine){
        this.mHttpEngine = httpEngine;
    }


    private HttpUtils(Context context) {
        this.mContext = context;
        mParams = new HashMap<>();
    }

    public static HttpUtils with(Context context) {
        return new HttpUtils(context);
    }

    public HttpUtils addParams(String key,String values){
        mParams.put(key,values);
        return this;
    }

    public HttpUtils url(String url) {
        mUrl = url;
        return this;
    }

    public HttpUtils get(){
        mRequestMethod=GET_REQUEST;
        return this;
    }

    public HttpUtils post(){
        mRequestMethod=POST_REQUEST;
        return this;
    }

    // 执行方法
    public void execute(HttpCallBack httpCallBack) {
        if (TextUtils.isEmpty(mUrl)) {
            throw new NullPointerException("访问路径不能为空");
        }

        if (mRequestMethod == GET_REQUEST) {
            mHttpEngine.get(mContext,mUrl, mParams, httpCallBack,false);
        }

        if (mRequestMethod == POST_REQUEST) {
            mHttpEngine.post(mContext,mUrl, mParams, httpCallBack,false);
        }
    }



}
