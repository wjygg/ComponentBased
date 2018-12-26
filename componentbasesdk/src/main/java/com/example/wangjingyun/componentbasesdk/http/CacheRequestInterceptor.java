package com.example.wangjingyun.componentbasesdk.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CacheRequestInterceptor implements Interceptor{

    private Context mContext;
    public CacheRequestInterceptor(Context context){
        this.mContext = context;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if(!isNetWork(mContext)){
            // 没有网络只读缓存
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE).build();
        }
        return chain.proceed(request);
    }

    private boolean isNetWork(Context context) {
        // 获得网络状态管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 建立网络数组
            NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();

            if (net_info != null) {
                for (int i = 0; i < net_info.length; i++) {
                    // 判断获得的网络状态是否是处于连接状态
                    if (net_info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
