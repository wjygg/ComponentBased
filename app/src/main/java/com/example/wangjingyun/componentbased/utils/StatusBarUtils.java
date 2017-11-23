package com.example.wangjingyun.componentbased.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.wangjingyun.componentbased.R;

import java.lang.reflect.Method;

/**
 * 沉浸式状态栏
 * Created by Administrator on 2017/10/26.
 */

public class StatusBarUtils {

    /**
     * 设置状态栏 变色
     * @param activity
     * @param color
     */
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
            contentView.setPadding(0,getStatusHeight(activity),0,0);
            //根布局
          //  View childAt = contentView.getChildAt(0);
            //设置为true 空余出 状态栏的值 类似padding
           // childAt.setFitsSystemWindows(true);

           // childAt.setPadding(0,getStatusHeight(activity),0,0);

        }


    }

    //设置activity全屏
    public static void setStatusBarTransParent(Activity activity){

        //5.0以上
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            //状态栏和虚拟键全屏（华为虚拟键覆盖在底部tab上）
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);

            //判断有虚拟键 把底部tab顶上去
           /* if(checkDeviceHasNavigationBar(activity)){

                ViewGroup contentView= (ViewGroup) activity.findViewById(android.R.id.content);
                contentView.setPadding(0,0,0,getDeviceHasNavigationBar(activity));

            }*/
        }

        //sdk 4.4-5.0以下
        else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {

            //电量存在  状态栏和虚拟键全屏（华为虚拟键覆盖在底部tab）
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //判断有虚拟键 把底部tab顶上去
           /* if(checkDeviceHasNavigationBar(activity)){

                ViewGroup contentView= (ViewGroup) activity.findViewById(android.R.id.content);
                contentView.setPadding(0,0,0,getDeviceHasNavigationBar(activity));

            }*/

        }
    }


    public static  void setFragmentStatusBarColor(Activity activity,View layout, int color){


        View view=new View(activity);
        view.setBackgroundColor(color);
        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusHeight(activity));
        view.setLayoutParams(params);

        //添加进 decorView
        if(layout instanceof LinearLayout)
        ((LinearLayout) layout).addView(view);
        if(layout instanceof RelativeLayout)
        ((RelativeLayout) layout).addView(view);
    }

    /**
     * 获取状态栏高度
     * @param activity
     * @return
     */
    public static int getStatusHeight(Activity activity){

        Resources resources=activity.getResources();
        int resourcesID=resources.getIdentifier("status_bar_height","dimen","android");
        return resources.getDimensionPixelOffset(resourcesID);
    }

    public static int getStatusHeight(Context activity){

        Resources resources=activity.getResources();
        int resourcesID=resources.getIdentifier("status_bar_height","dimen","android");
        return resources.getDimensionPixelOffset(resourcesID);
    }

    /**
     * 获取 华为虚拟键的高度
     * @param activity
     * @return
     */
    public static  int getDeviceHasNavigationBar(Activity activity){

        Resources res = activity.getResources();
        int id = res.getIdentifier("navigation_bar_height", "dimen", "android");
        return res.getDimensionPixelOffset(id);
    }

    /**
     * 反射判断是否存在华为虚拟键 NavigationBar
     * @param context
     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Activity context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }

}
