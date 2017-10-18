package com.example.wangjingyun.componentbased.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/10/17.
 */

public class CarHomeView extends LinearLayout {

    private ViewDragHelper viewDragHelper;

    private boolean flag=true;

    private int measuredChildHeight;

    public CarHomeView(Context context) {
        super(context);
    }

    public CarHomeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CarHomeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        viewDragHelper=ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                //滑动第一个
                return child==getChildAt(1);
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {

                return 0;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {

                if(top<=measuredChildHeight){

                    return top;
                }
                return measuredChildHeight;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        viewDragHelper.processTouchEvent(event);

        return super.onTouchEvent(event);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int childCount = getChildCount();

        if(childCount>2){
            throw  new RuntimeException("carhome只能有两个子布局");
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if(flag){

            flag=false;
            View childAt = getChildAt(0);
            measuredChildHeight = childAt.getMeasuredHeight();

        }
    }
}
