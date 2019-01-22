package com.example.wangjingyun.componentbasesdk.http;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface BaseApiService {
    /**
     * post 请求
     * @param url  url
     * @param params 参数
     * @param headers 头参数
     * @return
     */
    @POST()
    Call<ResponseBody> postMethod(@Url String url, @FieldMap Map<String,String> params,@HeaderMap Map<String, String> headers);

    /**
     * get 请求
     * @param url  url
     * @param params 参数
     * @param headers 头参数
     * @return
     */
    @GET()
    Call<ResponseBody> getMethod(@Url String url, @QueryMap Map<String,String> params,@HeaderMap Map<String, String> headers);

    /**
     * 下载文件 url
     * @param fileUrl
     * @return
     */
    @Streaming
    @POST()
    Call<ResponseBody> downloadFile(@Url String fileUrl);


    /**
     * 上传文件
     * @param url
     * @param Body
     * @return
     */
    @POST()
    Call<ResponseBody> upLoad(
            @Url() String url,
            @Body RequestBody Body);
}
