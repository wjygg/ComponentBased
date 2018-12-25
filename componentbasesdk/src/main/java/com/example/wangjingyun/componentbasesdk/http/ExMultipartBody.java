package com.example.wangjingyun.componentbasesdk.http;

import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;

/**
 * 静态代理 代理MultipartBody 上传文件监听
 */
public class ExMultipartBody extends RequestBody{

    private RequestBody requestBody;

    private UpLoadProgress upLoadProgress;

    private int mCurrentLength;

    public ExMultipartBody(MultipartBody requestBody){
        this.requestBody=requestBody;
    }

    public ExMultipartBody(MultipartBody requestBody,UpLoadProgress upLoadProgress){

        this.requestBody=requestBody;

        this.upLoadProgress=upLoadProgress;

    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {

        Log.e("TAG","监听");
        // 总的长度
        final long contentLength = contentLength();
        // 获取当前写了多少数据？BufferedSink Sink(okio 就是 io )就是一个 服务器的 输出流，我还是不知道写了多少数据

        // 又来一个代理 ForwardingSink
        ForwardingSink forwardingSink = new ForwardingSink(sink) {
            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                // 每次写都会来这里
                mCurrentLength += byteCount;
                if(upLoadProgress!=null){
                    upLoadProgress.onProgress(contentLength,mCurrentLength);
                }
                Log.e("TAG",contentLength+" : "+mCurrentLength);
                super.write(source, byteCount);
            }
        };
        // 转一把
        BufferedSink bufferedSink = Okio.buffer(forwardingSink);
        requestBody.writeTo(bufferedSink);
        // 刷新，RealConnection 连接池
        bufferedSink.flush();
    }
}
