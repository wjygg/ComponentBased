package com.example.wangjingyun.componentbased.dialog.titlebar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/11/23.
 */

public class BaseTitleBarParams {

    public Context context;

    public int backGroundColor;

    public SparseArray<String> stringSparseArray=new SparseArray<>();
    //图片
    public SparseArray<Drawable> drawableSparseArray=new SparseArray<>();
    //显示 隐藏
    public SparseArray<Integer> integerSparseArray=new SparseArray<>();
    //颜色
    public SparseArray<Integer> colorSparseArray=new SparseArray<>();
    //点击事件
    public SparseArray<View.OnClickListener> clickSparseArray=new SparseArray<>();

    public BaseTitleBarParams(Context context){

        this.context=context;

    }
}
