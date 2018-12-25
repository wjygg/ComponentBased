package com.example.wangjingyun.componentbased.activity.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.wangjingyun.componentbasesdk.ioc.ViewUtils;




/**
 *
 * http://www.gcssloop.com/customview/CoordinateSystem 安卓自定义控件
 *http://blog.csdn.net/harvic880925/article/details/50995268 安卓自定义控件
 * Created by Administrator on 2017/3/11.
 *
 */

public abstract class BaseActivity extends AppCompatActivity {

    //sd卡 读权限
    public static final int READ_EXTERNAL_CODE =0x001;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        //状态栏的颜色
        setStatusBar();

        ViewUtils.Inject(this);
        //设置头部
        initTitle();
        initDatas();
    }



    public abstract int getLayoutId();

    public abstract void initDatas();

    public void setStatusBar(){}

    public void initTitle(){}

    //判断 6.0权限
    public boolean hasPermission(Context context, String ... permissions){

        for(String permission: permissions){

            if(ContextCompat.checkSelfPermission(context,permission)!= PackageManager.PERMISSION_GRANTED){

               return false;
            }
        }
       return true;
    }

    public void requestPermission(int requestCode,String ... permissions){

        if(Build.VERSION.SDK_INT>=23){

            ActivityCompat.requestPermissions(this,permissions,requestCode);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){

            case READ_EXTERNAL_CODE:

                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    readSd();
                }
            break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


    }
    //执行 读sd卡权限
    public void readSd(){};
}

