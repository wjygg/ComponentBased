package com.example.wangjingyun.componentbasesdk.http;

/**
 * Created by Administrator on 2017/11/29.
 */

public interface HttpCallBack {

    public void onError(Exception e);

    public void onSucceed(String result);

}
