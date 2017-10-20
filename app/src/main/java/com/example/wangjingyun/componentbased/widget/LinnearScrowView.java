package com.example.wangjingyun.componentbased.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;


/**
 * Created by Administrator on 2017/10/20.
 */

public class LinnearScrowView extends HorizontalScrollView {
    public LinnearScrowView(Context context) {
        super(context);

    }

    public LinnearScrowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinnearScrowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }
}
