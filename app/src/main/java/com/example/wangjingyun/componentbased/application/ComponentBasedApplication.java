package com.example.wangjingyun.componentbased.application;

import android.app.Application;
import android.content.Context;

import com.example.wangjingyun.componentbased.network.Constants;
import com.example.wangjingyun.componentbasesdk.log.ExctptionCarshHandler;

import java.io.File;

import static com.example.wangjingyun.componentbased.network.Constants.COMPRESSIMG;


/**
 * 组件化 application
 * Created by wangjingyun on 2017/3/11.
 */

public class ComponentBasedApplication extends Application {

    public static ComponentBasedApplication application=null;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    @Override
    public void onCreate() {
        super.onCreate();

        application=this;
        //崩溃信息
        ExctptionCarshHandler.getInstance().init(this);

        //创建文件夹
        mkdirsFiles();

    }

    private void mkdirsFiles() {

        //创建压缩图片文件夹
        File CompressFiles=new File(Constants.COMPRESSIMG);

        if(!CompressFiles.exists()){

            CompressFiles.mkdirs();
        }

    }

    public static ComponentBasedApplication getInstance(){


        return application;

    }
}
