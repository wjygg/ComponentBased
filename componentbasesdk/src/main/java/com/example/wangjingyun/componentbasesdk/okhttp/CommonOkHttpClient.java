package com.example.wangjingyun.componentbasesdk.okhttp;

import com.example.wangjingyun.componentbasesdk.okhttp.https.HttpsUtils;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by wangjingyun on 2017/3/13.
 */

public class CommonOkHttpClient {

    private static final int TIME_OUT=30;//超时参数

    private static OkHttpClient okHttpClient=null;

    static {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS);

        //https 支持
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
        //支持ssl协议
        builder.sslSocketFactory(HttpsUtils.initSSLSocketFactory());

        okHttpClient=builder.build();

    }

    /**
     * 发送具体的请求
     * @param request
     * @param callback
     * @return
     */
     public static Call sendRequest(Request request, Callback callback){

         Call call = okHttpClient.newCall(request);
         call.enqueue(callback);

         return call;
     }


}
