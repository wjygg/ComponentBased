package com.example.wangjingyun.componentbased.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.wangjingyun.componentbased.R;

/**
 * 沉浸式状态栏
 * Created by Administrator on 2017/10/26.
 */

public class StatusBarUtils {


    public static void setStatusBarUtils(Activity activity, int color){


        //5.0以上
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){

            activity.getWindow().setStatusBarColor(color);
        }

        //sdk 4.4-5.0以下
        else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){

            //电量存在  全屏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //获取 decorView 布局 添加一个布局
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();

            View view=new View(activity);
            view.setBackgroundColor(color);
            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusHeight(activity));
            view.setLayoutParams(params);

            //添加进 decorView
            decorView.addView(view);

            //phonewindow-  decorView  - contentView - 根布局
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            //设置contentview的padding值
           // contentView.setPadding(0,getStatusHeight(activity),0,0);
            //根布局
            View childAt = contentView.getChildAt(0);
            //设置为true 空余出 状态栏的值 类似padding
            childAt.setFitsSystemWindows(true);

           // childAt.setPadding(0,getStatusHeight(activity),0,0);

        }


    }

    /**
     * 获取状态栏高度
     * @param activity
     * @return
     */
    private static int getStatusHeight(Activity activity){

        Resources resources=activity.getResources();
        int resourcesID=resources.getIdentifier("status_bar_height","dimen","android");
        return resources.getDimensionPixelOffset(resourcesID);
    }

    //设置activity全屏
    public static void setStatusBarTransParent(Activity activity){

        //5.0以上
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){

            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        //sdk 4.4-5.0以下
        else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {

            //电量存在  全屏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


}
