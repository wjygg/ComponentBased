package com.example.wangjingyun.componentbased.application;

import android.app.Application;

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
    }
    public static ComponentBasedApplication getInstance(){


        return application;

    }
}
