package com.example.wangjingyun.componentbasesdk.okhttp;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/3/13.
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



               okHttpClient=builder.build();

    }


}
