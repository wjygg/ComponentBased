package com.example.wangjingyun.componentbased.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/11/22.
 */

public class DialogParams {

    public Context context;

    public int themeResId;

    public View view;

    public int mViewResViewId;

    public int mHeight= ViewGroup.LayoutParams.WRAP_CONTENT;

    public int mWidth=ViewGroup.LayoutParams.WRAP_CONTENT;

    public int mAnimation=0;

    public int mGravity= Gravity.CENTER;

    public DialogParams(Context context, int themeResId){

        this.context=context;this.themeResId=themeResId;

    }

    //存储修改的字符串
    public SparseArray<String> stringSparseArray=new SparseArray<>();

    //存储点击事件
    public SparseArray<View.OnClickListener> viewClickSparseArray=new SparseArray<>();

}
