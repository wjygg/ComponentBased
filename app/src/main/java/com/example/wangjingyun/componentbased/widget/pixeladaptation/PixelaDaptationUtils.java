package com.example.wangjingyun.componentbased.widget.pixeladaptation;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class PixelaDaptationUtils {

    private static PixelaDaptationUtils mInstance;

    private Context context;

    private int screenWidth;

    private int screenHeight;

    private static final int mWidth=720;

    private static final int mHeight=1080;

    private PixelaDaptationUtils(Context context) {
        this.context=context;
        initDisplayMetrics();
    }

    public static PixelaDaptationUtils getInstance(Context context) {

        if (mInstance == null) {
            synchronized (PixelaDaptationUtils.class) {
                if (mInstance == null) {
                    mInstance = new PixelaDaptationUtils(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }


    public void initDisplayMetrics(){

        if (screenHeight == 0 || screenWidth == 0) { //宽高还未赋值
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (manager != null) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                manager.getDefaultDisplay().getMetrics(displayMetrics); //此时displayMetrics就可以获取到屏幕宽高信息了
                if (displayMetrics.widthPixels > displayMetrics.heightPixels) {
                    //横屏
                    screenWidth = displayMetrics.heightPixels;
                    screenHeight = displayMetrics.widthPixels - getStatusBarHeight();
                } else {
                    //竖屏
                    screenWidth = displayMetrics.widthPixels;
                    screenHeight = displayMetrics.heightPixels - getStatusBarHeight();
                }
            }
        }
    }

    public int getStatusBarHeight(){

        int result=0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;

    }

    public float getWidthCoefficient(){
        return screenWidth/mWidth;
    }

    public float getHeightCoefficient(){
        return screenHeight/mHeight;
    }

}
