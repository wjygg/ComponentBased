package com.example.wangjingyun.componentbasesdk.http;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * retrofit 实现类
 */
public class RetrofitEngine implements HttpEngine{

    private static OkHttpClient mOkHttpClient =null;

    private static final int TIME_OUT=10;//超时参数

    //存储所有的call请求
    //存储请求集合
    private static Map<Class<?>,List<Call>> callMap;

    private static Retrofit retrofit=null;

    private static BaseApiService baseApiService;

    protected final String EMPTY_MSG = "下载文件为null";

    private static final int PROGRESS_MESSAGE = 0x01;

    private HttpCallBackProgress httpCallBackProgress;

    private Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PROGRESS_MESSAGE:
                    httpCallBackProgress.onProgress((int) msg.obj);
                    break;
            }
        }
    };

    static {

        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        mOkHttpClient=builder.build();
        callMap=new ConcurrentHashMap<>();

        retrofit =new Retrofit.Builder().client(mOkHttpClient).build();
        baseApiService=retrofit.create(BaseApiService.class);
    }


    @Override
    public void post(Context context, String url, Map<String, String> params, Map<String, String> headParams, final HttpCallBack httpCallBack) {

        Call<ResponseBody> responseBodyCall = baseApiService.postMethod(url, params, headParams);

        putCall((Activity)context,responseBodyCall);

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String resultJson =response.body().toString();

                executeSuccessMethod(httpCallBack, resultJson);
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

                executeError(httpCallBack, new Exception(t.getLocalizedMessage()));

            }
        });
    }

    @Override
    public void get(Context context, String url, Map<String, String> params, Map<String, String> headParams, final HttpCallBack httpCallBack) {

        Call<ResponseBody> responseBodyCall =baseApiService.getMethod(url,params,headParams);

        putCall((Activity)context,responseBodyCall);

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String resultJson = response.body().toString();

                executeSuccessMethod(httpCallBack,resultJson);
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                executeError(httpCallBack, new Exception(t.getLocalizedMessage()));
            }
        });
    }

    @Override
    public void cancelAll(Context context) {

        if(mOkHttpClient!=null){

            cancelCall(((Activity)context).getClass());
        }
    }

    @Override
    public void downLoadFiles(Context context, String url, final String saveFileDir, final HttpCallBackProgress httpCallBackProgress) {

        Call<ResponseBody> responseBodyCall = baseApiService.downloadFile(url);
        putCall((Activity)context, responseBodyCall);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                final File file = handleResponse(response.body(),saveFileDir);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (file != null) {
                            httpCallBackProgress.onSucceed(file);
                        } else {
                            httpCallBackProgress.onError(new Exception(EMPTY_MSG));
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                executeError(httpCallBackProgress, new Exception(t.getLocalizedMessage()));
            }
        });
    }

    private static final MediaType FILE_TYPE = MediaType.parse("multipart/form-data");
    @Override
    public void sendMultipart(Context context, String url, Map<String, Object> params, Map<String, String> headParams,final HttpCallBack httpCallBack) {

        MultipartBody.Builder requestBody = new MultipartBody.Builder();
        requestBody.setType(MultipartBody.FORM);
        if (params != null) {

            for (Map.Entry<String, Object> entry :params.entrySet()) {
                if (entry.getValue() instanceof File) {

                    requestBody.addFormDataPart(entry.getKey(),((File) entry.getValue()).getName(), RequestBody.create(FILE_TYPE, (File) entry.getValue()));

                } else if (entry.getValue() instanceof String) {

                    requestBody.addFormDataPart(entry.getKey(),(String) entry.getValue());

                }
            }
        }

        Call<ResponseBody> responseBodyCall = baseApiService.upLoad(url, requestBody.build());
        putCall((Activity)context, responseBodyCall);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                final String resultJson = response.body().toString();
                // 当然有的时候还需要不同的些许处理
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        httpCallBack.onSucceed(resultJson);
                    }
                });

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                executeError(httpCallBack, new Exception(t.getLocalizedMessage()));
            }
        });
    }

    @Override
    public void connectionWebSocket(Context context, String url, WebSocketCallBackListener webSocketCallBackListener) {

    }

    private File handleResponse(ResponseBody response, String saveFileDir){

        if (response == null) {
            return null;
        }
        InputStream inputStream = null;
        File file = null;
        FileOutputStream fos = null;
        byte[] buffer = new byte[2048];
        int length;
        Long currentLength = 0L;
        double sumLength;
        try {

            Long filesLength=checkLocalFilePath(saveFileDir);
            currentLength=filesLength;
            file = new File(saveFileDir);

            fos = new FileOutputStream(file);
            inputStream = response.byteStream();
            sumLength = (double) response.contentLength();

            while ((length = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, length);
                currentLength += length;

                int mProgress = (int) (currentLength * 1.0f / sumLength * 100);

                //5%进度 发一次
                //   if(mProgress%5==0){
                handler.obtainMessage(PROGRESS_MESSAGE, mProgress).sendToTarget();
                //  }
            }
            fos.flush();
        } catch (Exception e) {
            file = null;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (inputStream != null) {

                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;

    }


    private Long checkLocalFilePath(String localFilePath) {

        //文件不存在下载
        File path = new File(localFilePath.substring(0,
                localFilePath.lastIndexOf("/") + 1));
        File file = new File(localFilePath);
        if (!path.exists()) {
            path.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{

            //文件存在返回长度
            return file.length();
        }

        return 0L;
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

                try{
                    httpCallBack.onError(e);

                }catch ( Exception e1){


                }

            }
        });
    }


    /**
     * 保存请求集合
     * @param
     */
    private static void putCall(Activity context, Call call){

        Class<? extends Activity> aClass = context.getClass();
        List<Call> calls = callMap.get(aClass);
        if(calls==null){
            calls=new LinkedList<>();
            calls.add(call);
            callMap.put(aClass,calls);
        }else{
            calls.add(call);
        }

    }

    /**
     * 取消请求
     */
    public static void cancelCall(Class<?> mzClass){

        List<Call> callList = callMap.get(mzClass);

        if(null != callList){

            for(Call call : callList){

                if(!call.isCanceled())

                    call.cancel();
            }
            callMap.remove(mzClass);

        }
    }

}
