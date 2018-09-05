package com.example.wangjingyun.componentbasesdk.http;

import android.support.annotation.Nullable;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WebSocketEngine extends WebSocketListener{

    private static WebSocketEngine webSocketEngine;
    private WebSocketCallBackListener webSocketCallBackListener;
    public WebSocketEngine(){

    }

    public static WebSocketEngine getInstance(){
        if(webSocketEngine==null){
            synchronized (WebSocketEngine.class){
                if(webSocketEngine==null){
                    webSocketEngine=new WebSocketEngine();
                }
            }
        }
        return  webSocketEngine;
    }

    public void setWebSocketCallBack(WebSocketCallBackListener webSocketCallBack){

        this.webSocketCallBackListener=webSocketCallBack;
    }

    public void onOpen(WebSocket webSocket, Response response) {

        if(webSocketCallBackListener!=null){

            webSocketCallBackListener.onOpen(webSocket,response);
        }
    }

    /** Invoked when a text (type {@code 0x1}) message has been received. */
    public void onMessage(WebSocket webSocket, String text) {

        if(webSocketCallBackListener!=null){

            webSocketCallBackListener.onMessage(webSocket,text);
        }
    }

    /** Invoked when a binary (type {@code 0x2}) message has been received. */
    public void onMessage(WebSocket webSocket, ByteString bytes) {

        if(webSocketCallBackListener!=null){

            webSocketCallBackListener.onMessage(webSocket,bytes);
        }
    }

    /**
     * Invoked when the remote peer has indicated that no more incoming messages will be
     * transmitted.
     */
    public void onClosing(WebSocket webSocket, int code, String reason) {

        if(webSocketCallBackListener!=null){

            webSocketCallBackListener.onClosing(webSocket,code,reason);
        }
    }

    /**
     * Invoked when both peers have indicated that no more messages will be transmitted and the
     * connection has been successfully released. No further calls to this listener will be made.
     */
    public void onClosed(WebSocket webSocket, int code, String reason) {

        if(webSocketCallBackListener!=null){

            webSocketCallBackListener.onClosed(webSocket,code,reason);
        }
    }

    /**
     * Invoked when a web socket has been closed due to an error reading from or writing to the
     * network. Both outgoing and incoming messages may have been lost. No further calls to this
     * listener will be made.
     */
    public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {

        if(webSocketCallBackListener!=null){

            webSocketCallBackListener.onFailure(webSocket,t,response);
        }
    }

}
