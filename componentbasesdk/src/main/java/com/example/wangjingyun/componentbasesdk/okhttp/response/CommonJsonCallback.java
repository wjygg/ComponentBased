package com.example.wangjingyun.componentbasesdk.okhttp.response;

import android.os.Handler;
import android.os.Looper;

import com.example.wangjingyun.componentbasesdk.okhttp.exception.OkHttpException;
import com.example.wangjingyun.componentbasesdk.okhttp.listener.DisposeDataHandler;
import com.example.wangjingyun.componentbasesdk.okhttp.listener.DisposeDataListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/15.
 */

public class CommonJsonCallback implements Callback{


    private static  final String ERROR_MSG ="emsg";

    private static final int NETWORK_ERROR=-1;
    private static final int JSON_ERROR=-2;
    private static final int OTHER_ERROR=-3;

    private DisposeDataListener mListener;

    private Class<?> mClass;

    private Handler mHandler;

    public CommonJsonCallback(DisposeDataHandler handler){

        this.mClass=handler.mClass;

        this.mListener=handler.mListener;

        //表示默认放到 主线程中执行
        mHandler=new Handler(Looper.getMainLooper());
    }


    @Override
    public void onFailure(final Call call, final IOException e) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //主线程

                mListener.onFailure(new OkHttpException(NETWORK_ERROR,e));
            }
        });

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

         final String result=response.body().string();

         mHandler.post(new Runnable() {
            @Override
            public void run() {

               handlerResponse(result);
            }
         });
    }

    /**
     * 处理服务器返回的 数据
     * @param result
     */
    private void handlerResponse(String result) {

        if(result==null&&result.toString().trim().equals("")){

            mListener.onFailure(new OkHttpException(JSON_ERROR,ERROR_MSG));
        }

        

    }



}
