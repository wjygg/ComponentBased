package com.example.wangjingyun.componentbasesdk.okhttp.request;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangjingyun
 * @function
 * Created by Administrator on 2017/3/13.
 */

public class RequestParams {

    public ConcurrentHashMap<String,String> urlParams=new ConcurrentHashMap<String,String>();

    public ConcurrentHashMap<String,Object> fileParams=new ConcurrentHashMap<String,Object>();


    public void put(String key,String values){

        if(key!=null&&values!=null){

            urlParams.put(key,values);
        }

    }

    public void put(String key,Object object){

        if(key!=null){
            fileParams.put(key,object);
        }
    }

}
