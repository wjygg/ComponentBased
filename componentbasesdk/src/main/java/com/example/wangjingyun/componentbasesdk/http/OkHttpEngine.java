package com.example.wangjingyun.componentbasesdk.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
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

    private static OkHttpClient mOkHttpClient =null;

    private static final int TIME_OUT=10;//超时参数

    static {
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(TIME_OUT, TimeUnit.SECONDS);

        mOkHttpClient=builder.build();
    }
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
    /**
     * post请求
     * @param context
     * @param url
     * @param urlParams
     * @param httpCallBack
     */
    @Override
    public void post(final Context context, String url, Map<String, String> urlParams,Map<String, String> headParams, final HttpCallBack httpCallBack) {

        FormBody.Builder frombody = new FormBody.Builder();

        if(urlParams!=null){
            for(Map.Entry<String, String> entry:urlParams.entrySet()){
                    frombody.add(entry.getKey(), entry.getValue());
            }
        }
        RequestBody requestBody = frombody.build();
        Request.Builder build = new Request.Builder()
                .url(url)
                .tag(context)
                .post(requestBody);

        if(headParams!=null){
            for(Map.Entry<String, String> entry:headParams.entrySet()){
                build.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = build.build();

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

                    }
                }
        );
    }
    /**
     * get请求
     * @param context
     * @param url
     * @param urlParams
     * @param httpCallBack
     */
    @Override
    public void get(Context context, String url, Map<String, String> urlParams,Map<String, String> headParams, final HttpCallBack httpCallBack) {

        StringBuilder stringBuilder=new StringBuilder(url).append("?");
        if(urlParams!=null){

            for(Map.Entry<String,String> entry : urlParams.entrySet()){

                    stringBuilder.append(entry.getKey()).append("=").
                            append(entry.getValue()).append("&");


            }
        }
        Request.Builder requestBuilder = new Request.Builder().url(stringBuilder.substring(0,stringBuilder.length()-1)).tag(context).method("GET",null);

        if(headParams!=null){
            for(Map.Entry<String, String> entry:headParams.entrySet()){
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = requestBuilder.build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                executeError(httpCallBack, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String resultJson = response.body().string();

                    executeSuccessMethod(httpCallBack,resultJson);

            }
        });

    }

    /**
     * 取消所有请求
     */
    @Override
    public void cancelAll() {

        if(mOkHttpClient!=null){

            mOkHttpClient.dispatcher().cancelAll();
        }

    }

    /**
     * 下载文件
     * @param context 上下文
     * @param url 文件地址
     * @param  httpCallBackProgress 回调
     */
    @Override
    public void downLoadFiles(Context context, String url, final String saveFileDir, final HttpCallBackProgress httpCallBackProgress) {

        this.httpCallBackProgress=httpCallBackProgress;

        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                executeError(httpCallBackProgress, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final File file = handleResponse(response,saveFileDir);
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
        });
    }




    private File handleResponse(Response response, String saveFileDir){

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
            inputStream = response.body().byteStream();
            sumLength = (double) response.body().contentLength();

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
     * 上传文件
     * @param context
     * @param url
     * @param params
     * @param httpCallBack
     * @param cache
     */
    private static final MediaType FILE_TYPE = MediaType.parse("multipart/form-data");

    @Override
    public void sendMultipart(Context context, String url, Map<String, Object> params,Map<String, String> headParams, final HttpCallBack httpCallBack) {


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
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(requestBody.build());

        if(headParams!=null){
            for(Map.Entry<String, String> entry:headParams.entrySet()){
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = requestBuilder.build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                executeError(httpCallBack, e);
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

    /**
     * websocket 长连接
     * @param context 上下文
     * @param url 连接url
     * @param webSocketCallBackListener 长连接回掉
     */
    @Override
    public void connectionWebSocket(Context context, String url, WebSocketCallBackListener webSocketCallBackListener) {

        Request request = new Request.Builder()
                .url(url)
                .build();

        WebSocketEngine webSocketEngine = WebSocketEngine.getInstance();
        webSocketEngine.setWebSocketCallBack(webSocketCallBackListener);
        OkHttpClient client = new OkHttpClient();
        client.newWebSocket(request,webSocketEngine);

        client.dispatcher().executorService().shutdown();
    }


}
