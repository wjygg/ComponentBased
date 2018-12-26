package com.example.wangjingyun.componentbasesdk.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 返回数据缓存
 */
public class CacheResponseInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {

        Response response = chain.proceed(chain.request());

        // 过期时间是 30s
        response = response.newBuilder()
                .removeHeader("Cache-Control")
                .removeHeader("Pragma")
                .addHeader("Cache-Control","max-age="+30).build();
        return response;
    }
}
