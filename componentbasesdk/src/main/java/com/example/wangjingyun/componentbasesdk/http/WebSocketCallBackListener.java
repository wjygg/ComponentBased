package com.example.wangjingyun.componentbasesdk.http;

import android.content.Context;

import java.util.Map;

import okhttp3.Response;
import okhttp3.WebSocket;
import okio.ByteString;

public abstract class WebSocketCallBackListener implements HttpCallBack{

    public abstract void onOpen(WebSocket webSocket, Response response);

    /** Invoked when a text (type {@code 0x1}) message has been received. */
    public abstract void onMessage(WebSocket webSocket, String text);

    /** Invoked when a binary (type {@code 0x2}) message has been received. */
    public abstract void onMessage(WebSocket webSocket, ByteString bytes);

    /**
     * Invoked when the remote peer has indicated that no more incoming messages will be
     * transmitted.
     */
    public abstract void onClosing(WebSocket webSocket, int code, String reason);

    /**
     * Invoked when both peers have indicated that no more messages will be transmitted and the
     * connection has been successfully released. No further calls to this listener will be made.
     */
    public abstract void onClosed(WebSocket webSocket, int code, String reason);

    /**
     * Invoked when a web socket has been closed due to an error reading from or writing to the
     * network. Both outgoing and incoming messages may have been lost. No further calls to this
     * listener will be made.
     */
    public abstract void onFailure(WebSocket webSocket, Throwable t,  Response response);

    //公共请求参数准备
    public void onPreParameters(Context context, Map<String, String> params, Map<String, String> headParams, boolean isPreParameters, boolean isPreHeadParameters){};
    //错误回调
    public void onError(Exception e){};
    //成功回调
    public void onSucceed(Object result){};
}
