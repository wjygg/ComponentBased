package com.example.wangjingyun.componentbased.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 58同城组合loading 控件
 *
 * Created by wangjingyun on 2017/7/24.
 */

public class WithCityLoading extends LinearLayout {
    public WithCityLoading(Context context) {
        this(context,null);
    }

    public WithCityLoading(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WithCityLoading(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }


}
