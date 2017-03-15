package com.example.wangjingyun.componentbasesdk.okhttp.listener;

/**
 * Created by Administrator on 2017/3/15.
 */

public interface DisposeDataListener {
    /**
     * 请求成功回调
     * @param responseObj
     */
    public void onSuccess(Object responseObj);

    /**
     * 请求失败回调
     * @param reasonObj
     */
    public void onFailure(Object reasonObj);

}
