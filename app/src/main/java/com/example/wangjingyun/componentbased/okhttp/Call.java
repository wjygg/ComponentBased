package com.example.wangjingyun.componentbased.okhttp;

/**
 * Created by hcDarren on 2017/11/18.
 * Call
 */

public interface Call {
    void enqueue(Callback callback);

    Response execute();
}
