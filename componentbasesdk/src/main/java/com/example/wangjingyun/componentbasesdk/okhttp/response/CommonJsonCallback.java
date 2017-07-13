package com.example.wangjingyun.componentbasesdk.okhttp.response;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.wangjingyun.componentbasesdk.okhttp.exception.OkHttpException;
import com.example.wangjingyun.componentbasesdk.okhttp.listener.DisposeDataHandler;
import com.example.wangjingyun.componentbasesdk.okhttp.listener.DisposeDataListener;


import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 *  封装 返回数据处理类
 * Created by wangjingyun on 2017/3/15.
 */

public class CommonJsonCallback implements Callback {

    private static final String ERROR_CODE = "ecode";

    private static final int NETWORK_ERROR = -1;
    private static final int JSON_ERROR = -2;
    private static final int CODE_ERROR = -3;
    private static final int JSONCONVERSION_ERROR = -5;
    private static final int OTHER_ERROR = -6;

    private DisposeDataListener mListener;

    private Class<?> mClass;

    private Handler mHandler;

    public CommonJsonCallback(DisposeDataHandler handler) {

        this.mClass = handler.mClass;

        this.mListener = handler.mListener;

        //表示默认放到 主线程中执行
        mHandler = new Handler(Looper.getMainLooper());
    }
    @Override
    public void onFailure(final Call call, final IOException e) {

        Log.e("okHttp3",e.getMessage());

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //主线程
                mListener.onFailure(new OkHttpException(NETWORK_ERROR,"请求失败"));
            }
        });
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {

        final String result = response.body().string();

        mHandler.post(new Runnable() {
            @Override
            public void run() {

                handlerResponse(result);
            }
        });
    }
    /**
     * 处理服务器返回的 数据
     *
     * @param result
     */
    private void handlerResponse(String result) {

        if (result == null && result.toString().trim().equals("")) {

            mListener.onFailure(new OkHttpException(JSON_ERROR, "JSON数据返回为空"));

            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(result);
            //判断 jsonObjetc 有没有 code 值
            if (jsonObject.has(ERROR_CODE)) {
                //判断 mClass 是不是为空
                if (mClass == null) {
                    //应用层自解析json
                    mListener.onSuccess(result);
                } else {
                    //解析 json 对象 返回
                    Object resultObject = com.alibaba.fastjson.JSONObject.parseObject((jsonObject.getJSONObject("result")).toString(), mClass);
                    //转换成功
                    if (resultObject != null) {

                        mListener.onSuccess(resultObject);
                    } else {
                        //转换失败
                        mListener.onFailure(new OkHttpException(JSONCONVERSION_ERROR, "JSON转换对象错误"));
                    }
                }
            } else {
                mListener.onFailure(new OkHttpException(CODE_ERROR, "JSON数据中没有CODE值"));
            }
        } catch (Exception e) {
            mListener.onFailure(new OkHttpException(OTHER_ERROR, "未知错误"));
        }


    }


}
