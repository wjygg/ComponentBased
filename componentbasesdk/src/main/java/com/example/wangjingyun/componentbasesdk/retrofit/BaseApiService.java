package com.example.wangjingyun.componentbasesdk.retrofit;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


public interface BaseApiService<T> {

    public static final String Base_URL = "http://ip.taobao.com/";


    @GET("{url}")
    Observable<BaseResponse<T>> executeGet(@Path("url") String url, @QueryMap Map<String, String> maps);
    @POST("{url}")
    Observable<BaseResponse<T>> executePost(@Path("url") String url, @QueryMap Map<String, String> maps);

    @POST("{url}")
    Observable<ResponseBody> json(@Path("url") String url, @Body RequestBody jsonStr);

    @POST("{url}")
    Observable<ResponseBody> uploadFiles(@Path("url") String url, @Body RequestBody Body);

    /**
     * 下载文件 url
     * @param fileUrl
     * @return
     */
    @Streaming
    @POST()
    Observable<ResponseBody> downloadFile(@Url String fileUrl);

}
