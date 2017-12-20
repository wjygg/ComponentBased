package com.example.wangjingyun.componentbasesdk.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/29.
 */

public class OkHttpEngine implements HttpEngine {

    private static OkHttpClient mOkHttpClient = new OkHttpClient();

    private Handler handler=new Handler(Looper.getMainLooper());
    @Override
    public void post(final Context context, String url, Map<String, String> urlParams, final HttpCallBack httpCallBack, final boolean cache) {

        FormBody.Builder frombody = new FormBody.Builder();

        if(urlParams!=null){

            for(Map.Entry<String, String> entry:urlParams.entrySet()){

                frombody.add(entry.getKey(), entry.getValue());
            }
        }
        RequestBody requestBody = frombody.build();
        Request request = new Request.Builder()
                .url(url)
                .tag(context)
                .post(requestBody)
                .build();

        mOkHttpClient.newCall(request).enqueue(
                new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        executeError(httpCallBack, e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String resultJson = response.body().string();
                        executeSuccessMethod(httpCallBack, resultJson);
                        // 缓存处理，下一期我们没事干，自己手写数据库框架
                    }
                }
        );
    }

    /**
     *  执行成功的方法
     **/
    private void executeSuccessMethod(final HttpCallBack httpCallBack, final String resultJson) {
        try {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    httpCallBack.onSucceed(resultJson);
                }
            });
        } catch (Exception e) {
            executeError(httpCallBack, e);
            e.printStackTrace();
        }
    }

    /**
     *  执行失败的方法
     */
    private void executeError(final HttpCallBack httpCallBack, final Exception e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                httpCallBack.onError(e);
            }
        });
    }

    @Override
    public void get(Context context, String url, Map<String, String> urlParams, final HttpCallBack httpCallBack, boolean cache) {
        StringBuilder stringBuilder=new StringBuilder(url).append("?");

        if(urlParams!=null){

            for(Map.Entry<String,String> entry : urlParams.entrySet()){

                stringBuilder.append(entry.getKey()).append("=").
                        append(entry.getValue()).append("&");
            }
        }
        Request.Builder requestBuilder = new Request.Builder().url(stringBuilder.substring(0,stringBuilder.length()-1)).tag(context).method("GET",null);
        Request request = requestBuilder.build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                httpCallBack.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String resultJson = response.body().string();
                // 当然有的时候还需要不同的些许处理
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        httpCallBack.onSucceed(resultJson);
                    }
                });
            }
        });

    }

    private static final MediaType FILE_TYPE = MediaType.parse("application/octet-stream");
    /**
     * 上传文件
     * @param context
     * @param url
     * @param params
     * @param httpCallBack
     * @param cache
     */
    @Override
    public void sendMultipart(Context context, String url, Map<String, Object> params, final HttpCallBack httpCallBack, boolean cache) {


        MultipartBody.Builder requestBody = new MultipartBody.Builder();
        requestBody.setType(MultipartBody.FORM);
        if (params != null) {

            for (Map.Entry<String, Object> entry :params.entrySet()) {
                if (entry.getValue() instanceof File) {

                    requestBody.addFormDataPart(entry.getKey(),((File) entry.getValue()).getName(),RequestBody.create(FILE_TYPE, (File) entry.getValue()));

                } else if (entry.getValue() instanceof String) {

                    requestBody.addFormDataPart(entry.getKey(),(String) entry.getValue());
                }
            }
        }

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody.build())
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                httpCallBack.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String resultJson = response.body().string();
                // 当然有的时候还需要不同的些许处理
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        httpCallBack.onSucceed(resultJson);
                    }
                });
            }
        });

    }


}
