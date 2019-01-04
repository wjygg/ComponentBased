package com.example.wangjingyun.componentbasesdk.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wjy on 2017/11/29.
 */

public class HttpUtils {
    // 上下文
    private Context mContext;
    // 网络访问引擎 多态
    public static HttpEngine mHttpEngine = new OkHttpEngine();
    // 接口地址
    private String mUrl;
    //下载文件路径
    private String saveDownLoadFilsUrl;
    // 请求参数集合
    private Map<String, String> mParams;
    //上传文件参数集合
    public Map<String,Object> fileParams;
    //头部参数集合
    private Map<String,String> mHeadParams;
    // get请求标识
    private final int GET_REQUEST = 0x0011;
    // post请求标识
    private final int POST_REQUEST = 0x0022;
    //上传文件
    private final int UPLOAD_FILES = 0x0033;
    //下载文件
    private final int DOWNLOAD_FILES = 0x0044;
    //长连接webSocket
    private final int WEBSOCKET_REQUEST = 0x0055;
    // 请求的方式
    private int mRequestMethod = GET_REQUEST;

    //是否添加 公共参数
    private boolean isPreParameters=false;

    //上传文件添加公共参数
    private boolean isPreUpParameters=false;

    //是否添加 公共请求头参数
    private boolean isPreHeadParameters=false;

    // 切换引擎
    public void exchangeEngine(OkHttpEngine httpEngine){
        this.mHttpEngine = httpEngine;
    }

    public HttpUtils(Context context) {
        this.mContext = context;
        mParams = new ConcurrentHashMap<>();
    }

    // 可以在Application中配置HttpEngine
    public static void initEngine(HttpEngine httpEngine){
          mHttpEngine = httpEngine;
    }

    public static HttpUtils with(Context context) {
        return new HttpUtils(context);
    }


    public HttpUtils url(String url) {
        mUrl = url;
        return this;
    }

    /**
     * 添加普通参数
     * @param key
     * @param values
     * @return
     */
    public HttpUtils addParams(String key, String values){
        if(mParams!=null){
            mParams.put(key,values);
        }
        return this;
    }

    /**
     *  初始化上传文件参数
     * @return
     */
    public HttpUtils initFileParams(){
        fileParams=new IdentityHashMap<>();
        return this;
    }

    /**
     * 添加上传文件 参数
     * @param key
     * @param values
     * @return
     */
    public HttpUtils addFileParams(String key, Object values){

        if(fileParams!=null){

            fileParams.put(new String(key),values);
        }
        return this;
    }

    /**
     * 初始化头部参数集合
     * @return
     */
    public HttpUtils initHeadParams(){
        mHeadParams=new ConcurrentHashMap<>();
        return this;
    }

    /**
     * 添加头部参数
     * @param key
     * @param values
     * @return
     */
    public HttpUtils addHeadParams(String key, String values){
        if(mHeadParams!=null){
            mHeadParams.put(key,values);
        }
        return this;
    }

    /**
     * 文件下载保存路径
     * @param url
     * @return
     */
    public HttpUtils saveDownLoadFilsUrl(String url) {
        this.saveDownLoadFilsUrl = url;
        return this;
    }

    /**
     * get 请求
     * @return
     */
    public HttpUtils get(){
        this.mRequestMethod=GET_REQUEST;
        return this;
    }
    /**
     * post 请求
     * @return
     */
    public HttpUtils post(){
        this.mRequestMethod=POST_REQUEST;
        return this;
    }
    /**
     * 上传 请求
     * @return
     */
    public HttpUtils UploadFiles(){
        this.mRequestMethod=UPLOAD_FILES;
        return this;
    }

    /**
     * 下载文件
     * @return
     */
    public HttpUtils downLoadFiles(){
        this.mRequestMethod=DOWNLOAD_FILES;
        return this;
    }

    /**
     * 长连接请求
     * @return
     */
    public HttpUtils initWebSocketRequest(){
        this.mRequestMethod=WEBSOCKET_REQUEST;
        return this;
    }

    /**
     * 是否添加公共参数
     * @param isPreParameters
     * @return
     */
    public HttpUtils isPreParameters(boolean isPreParameters){
        this.isPreParameters=isPreParameters;
        return this;
    }


    /**
     * 上传文件添加公共参数
     * @param isPreUpParameters
     * @return
     */
    public HttpUtils isPreUpParameters(boolean isPreUpParameters){
        this.isPreUpParameters=isPreUpParameters;
        return this;
    }


    /**
     * 是否添加公共请求头
     * @param isPreHeadParameters
     * @return
     */
    public HttpUtils isPreHeadParameters(boolean isPreHeadParameters){
        this.isPreHeadParameters=isPreHeadParameters;
        return this;
    }

    /**
     * 取消所有请求
     * @return
     */
    public void cancelAll(Context context){
        mHttpEngine.cancelAll(context);
    }

    // 执行方法
    public void execute(HttpCallBack httpCallBack) {

        if(isNetworkAvalible(mContext)==false){

           Toast.makeText(mContext,"无网络链接", Toast.LENGTH_SHORT).show();
            return;
        }
        //map 集合添加公共参数
        httpCallBack.onPreParameters(mContext,mParams,fileParams,mHeadParams,isPreParameters,isPreUpParameters,isPreHeadParameters);

        if (TextUtils.isEmpty(mUrl)) {
            throw new NullPointerException("访问路径不能为空");
        }

        if (mRequestMethod == GET_REQUEST) {
            mHttpEngine.get(mContext,mUrl, mParams,mHeadParams, httpCallBack);
        }

        if (mRequestMethod == POST_REQUEST) {
            mHttpEngine.post(mContext,mUrl, mParams,mHeadParams, httpCallBack);
        }

        if(mRequestMethod==UPLOAD_FILES){
            mHttpEngine.sendMultipart(mContext,mUrl,fileParams,mHeadParams,httpCallBack);
        }

        if(mRequestMethod==DOWNLOAD_FILES){
            mHttpEngine.downLoadFiles(mContext,mUrl,saveDownLoadFilsUrl,(HttpCallBackProgress)httpCallBack);
        }

        if(mRequestMethod==WEBSOCKET_REQUEST){
            mHttpEngine.connectionWebSocket(mContext,mUrl,(WebSocketCallBackListener)httpCallBack);
        }
    }


    public boolean isNetworkAvalible(Context context) {
        // 获得网络状态管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 建立网络数组
            NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();

            if (net_info != null) {
                for (int i = 0; i < net_info.length; i++) {
                    // 判断获得的网络状态是否是处于连接状态
                    if (net_info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
