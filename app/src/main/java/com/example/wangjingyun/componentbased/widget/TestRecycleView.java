package com.example.wangjingyun.componentbased.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/11/28.
 */

public class TestRecycleView extends RecyclerView{
    public TestRecycleView(Context context) {
        super(context);
    }

    public TestRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
