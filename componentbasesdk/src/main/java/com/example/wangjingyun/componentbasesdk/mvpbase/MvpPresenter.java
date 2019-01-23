package com.example.wangjingyun.componentbasesdk.mvpbase;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;

/**
 * Created by uggyy on 2018/5/7.
 */

public class MvpPresenter<T,M> {

    private WeakReference<T> mView = null; //弱引用持有view

    public T proxyView;

    public M model;
    /**
     * p层 和view 层绑定
     * @param view
     */
    public void attachView(T view){
        this.mView=new WeakReference<>(view);
        //动态代理调用实现接口判断activity是否为空
        proxyView= (T) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

                if(mView!=null&&mView.get()!=null){

                    return method.invoke(mView,objects);
                }
                return null;
            }
        });

        try {
            ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class<M> modelClazz = (Class<M>) (parameterizedType.getActualTypeArguments()[1]);
            model = modelClazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解除view层和p层绑定
     */
    public void detachView(){

        if(this.mView!=null){

            this.mView=null;

            this.proxyView=null;
        }
    }

}
