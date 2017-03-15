package com.example.wangjingyun.componentbasesdk.okhttp.request;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 *
 * blog.csdn.net/itachi85/article/details/51190687  okhttp3参考
 * Created by Administrator on 2017/3/13.
 */

public class CommonRequest {


    //post网络请求
    public static Request createPostRequest(String url,RequestParams params){

        FormBody.Builder frombody = new FormBody.Builder();

        if(params!=null){
            
            for(Map.Entry<String, String> entry:params.urlParams.entrySet()){

                frombody.add(entry.getKey(),entry.getValue());
            }
        }
        RequestBody build = frombody.build();
        Request request = new Request.Builder()
                .url(url)
                .post(build)
                .build();
        return  request;
    }
    //get请求

    public static Request createGetRequest(String url,RequestParams params){

        StringBuilder stringBuilder=new StringBuilder(url).append("?");

        if(params!=null){

            for(Map.Entry<String,String> entry : params.urlParams.entrySet()){

                stringBuilder.append(entry.getKey()).append("=").
                        append(entry.getValue()).append("&");
            }
        }

        return  new Request.Builder().url(stringBuilder.substring(0,stringBuilder.length()-1)).method("GET",null).build();
    }
}
