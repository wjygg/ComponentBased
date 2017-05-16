package com.example.wangjingyun.componentbasesdk.ioc;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/5/7.
 */

public class ViewUtils {

    //绑定activity 注解
    public static void Inject(Activity activity) {


        Inject(new ViewFilter(activity), activity);
    }

    //绑定 自定义view 注解
    public static void Inject(View view) {

        Inject(new ViewFilter(view), view);
    }

    //绑定 fragment
    public static void Inject(View view, Object object) {

        Inject(new ViewFilter(view), object);

    }


    //反射 需要执行的类
    private static void Inject(ViewFilter viewFilter, Object object) {

        //反射找到属性
        InjectFiled(viewFilter, object);
        //反射注册事件
        InjectEvent(viewFilter, object);

    }

    public static void InjectFiled(ViewFilter viewFilter, Object object) {

        //反射获取类的势力
        Class<?> clazz = object.getClass();
        //获取类中所有属性 public  private
        Field[] declaredFields = clazz.getDeclaredFields();

        for (Field field : declaredFields) {

            //属性上的注解
            ViewById viewById = field.getAnnotation(ViewById.class);

            //判断注解不为null
            if (viewById != null) {
                //获取 注解后面的值
                int viewId = viewById.value();

                //具体的控件
                View view = viewFilter.findViewById(viewId);

                if (view != null) {

                    //设置  可以给private 和 public 属性注入
                    field.setAccessible(true);
                    try {
                        //给属性 赋值 注入
                        field.set(object, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }


        }

    }

    public static void InjectEvent(ViewFilter viewFilter, Object object) {

        //反射获取类
        Class<?> aClass = object.getClass();

        //获取类中所有的方法
        Method[] declaredMethods = aClass.getDeclaredMethods();

        for (Method method : declaredMethods) {

            //根据方法名找到 注解
            OnClick annotation = method.getAnnotation(OnClick.class);

            //判断 注解不为null

            if (annotation != null) {

                //获取注解后面的 值

                int[] values = annotation.value();

                //找到每个控件 添加 OnClick方法

                for (int value : values) {

                    View view = viewFilter.findViewById(value);

                    //判断 有没有 网络注解

                    boolean idCheckNet = method.getAnnotation(CheckNet.class) != null;

                    if (view != null) {

                        view.setOnClickListener(new ViewOnClickListener(method, object, idCheckNet));

                    }
                }
            }

        }
    }

    private static class ViewOnClickListener implements View.OnClickListener {

        private Method method;

        private Object object;

        private boolean idCheckNet;

        public ViewOnClickListener(Method method, Object object, boolean idCheckNet) {

            this.method = method;

            this.object = object;

            this.idCheckNet = idCheckNet;
        }

        @Override
        public void onClick(View view) {
            //需要判断网络
            if (idCheckNet) {

                //没有网络
                if (!isNetworkConnected(view.getContext())) {

                    Toast.makeText(view.getContext(), "没有网络", Toast.LENGTH_SHORT).show();

                    return;
                }
            }
            try {
                //所有修饰符方法 都可以调用
                method.setAccessible(true);
                //执行方法名方法
                method.invoke(object, view);

            } catch (Exception e) {
                e.printStackTrace();


            }
        }
    }


    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */

    public static boolean isNetworkConnected(Context context) {

        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

        if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {   //判断网络连接是否打开

            return mNetworkInfo.isConnected();

        }

        return false;

    }

}
