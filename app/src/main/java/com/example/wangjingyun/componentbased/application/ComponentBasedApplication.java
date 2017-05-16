package com.example.wangjingyun.componentbased.application;

import android.app.Application;

import com.example.wangjingyun.componentbasesdk.log.ExctptionCarshHandler;


/**
 * 组件化 application
 * Created by wangjingyun on 2017/3/11.
 */

public class ComponentBasedApplication extends Application {

    public static ComponentBasedApplication application=null;


    @Override
    public void onCreate() {
        super.onCreate();

        application=this;
        //崩溃信息
        ExctptionCarshHandler.getInstance().init(this);;

    }
    public static ComponentBasedApplication getInstance(){


        return application;

    }
}
