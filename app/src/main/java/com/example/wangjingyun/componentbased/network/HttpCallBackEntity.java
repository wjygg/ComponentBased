package com.example.wangjingyun.componentbased.network;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;


import com.alibaba.fastjson.JSONObject;
import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.dialog.BaseDialog;
import com.example.wangjingyun.componentbasesdk.http.HttpCallBack;




import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;


/**
 * 网络 框架json 转换对象
 * Created by wangjingyun on 2018/3/26.
 */

public abstract class HttpCallBackEntity<T> implements HttpCallBack {

    private static final String ERRORCODE = "status";
    private static final String MSG = "msg";
    private static final String CONNECT_TIME_OUT="connect timed out" ;
    private static final String NO_ROUTE_TO_HOST="No route to host";
    private static final int SUCCESS_CODE=0;
    private static final int LOGIN_CODE=10;
    //默认不显示dialog
    private boolean isDialog = false;

    //请求等待dialog
    private BaseDialog baseDialog;

    private Context context;

    public HttpCallBackEntity(Context context) {
        this.context=context;
    }

    public HttpCallBackEntity(Context context, boolean isDialog) {
        this.isDialog = isDialog;
        this.context = context;
    }

    @Override
    public void onPreParameters(Context context, Map<String, String> params,Map<String, String> headParams,boolean isParams,boolean isHeadParams) {

        //添加公共请求参数
        if(isParams&&params!=null){

            params.put("policeno","1");
        }
        //添加公共请求头参数
        if(isHeadParams&&headParams!=null){

         // headParams.put("Cookie","jeeplus.session.id="+"这里登陆返回 sessionId");
        }
        showDialog(context, isDialog);
    }

    public void showDialog(Context context, boolean isDialog) {

        if (isDialog) {
            //显示dialog
            baseDialog = new BaseDialog.Builder(context, R.style.MyDialog).
                    setGravity(Gravity.CENTER).setView(R.layout.dialog_httpcallback_layout).builder();
            baseDialog.show();
        }
    }

    @Override
    public void onSucceed(Object result) {
        //Class<T> t t的字节码对象 T t T类型的t 对象
        try {
            if (!TextUtils.isEmpty((String) result)) {
                T t = (T) JSONObject.parseObject((String) result, analyticData(this));
                //反射判断返回的errorCode字段
                Field declaredField = t.getClass().getDeclaredField(ERRORCODE);
                //反射判断返回的msg
                Field msgdField = t.getClass().getDeclaredField(MSG);
                if (declaredField != null && msgdField != null) {
                    //访问私有属性
                    msgdField.setAccessible(true);
                    declaredField.setAccessible(true);
                    //获取私有属性值
                    int str = (int) declaredField.get(t);
                    String msg = (String) msgdField.get(t);
                    switch (str) {
                        //请求成功
                        case SUCCESS_CODE:
                            onSuccess(t);
                            break;

                        case LOGIN_CODE:
                            //重新登陆

                            break;

                        //其余错误 提示用户为啥出错
                        default:
                            Exception exception = new Exception(msg);
                            onFails(exception);
                    }
                } else {
                    Exception exception = new Exception(ERRORCODE + "字段不存在");
                    onFails(exception);
                }
            }
        } catch (Exception e) {
            Log.e("tag","json解析失败");
        }
        //隐藏dialog
        if (isDialog && baseDialog != null && baseDialog.isShowing()) {
            baseDialog.dismiss();
        }
    }

    @Override
    public void onError(Exception e) {

        switch (e.getMessage()) {
            //session 失效 返回 连接超时 返回登陆页
            case CONNECT_TIME_OUT:
                Log.e("tag","连接超时!");
            //没有主机提示
            case NO_ROUTE_TO_HOST:
                Log.e("tag","未找到服务器!");
                break;

            default:
                Log.e("tag",e.getMessage());
                break;
        }

        if (isDialog && baseDialog != null && baseDialog.isShowing()) {
            baseDialog.dismiss();
        }

    }

    //错误回调
    public abstract void onFails(Exception e);

    //成功回调
    public abstract void onSuccess(T t);

    //解析字符串
    public Class<?> analyticData(Object clss) {

        Type genericSuperclass = clss.getClass().getGenericSuperclass();

        Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();

        return (Class<?>) actualTypeArguments[0];
    }


}
