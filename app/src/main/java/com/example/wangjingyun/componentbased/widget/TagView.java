package com.example.wangjingyun.componentbased.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 *
 * 自定义流式布局
 * Created by wangjingyun on 2017/6/14.
 */

public class TagView extends ViewGroup{


    public TagView(Context context) {
        super(context);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }



}
