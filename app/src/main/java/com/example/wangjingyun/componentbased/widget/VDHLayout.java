package com.example.wangjingyun.componentbased.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * ViewDragHelper 学习
 * Created by Administrator on 2017/8/9.
 *
 */

public class VDHLayout extends LinearLayout{

    private ViewDragHelper viewDragHelper;

    public VDHLayout(Context context) {
        this(context,null);
    }

    public VDHLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VDHLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        int count=getChildCount();
        viewDragHelper= ViewDragHelper.create(this,1.0f,new ViewDragHelperCallBack());
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);

        return true;
    }

    private class ViewDragHelperCallBack extends ViewDragHelper.Callback{

        /**
         *  规定子view 滑动 返回true 全部滑动
         * @param child 子view
         * @param pointerId
         * @return
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        /**
         *
         * @param child
         * @param left 横向滑动的left
         * @param dx
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {

            int paddingLeft=getPaddingLeft();

            int right=getWidth()-child.getWidth()-paddingLeft;

            int newLeft=Math.min(Math.max(paddingLeft,left),right);
            return newLeft;
        }

        /**
         * 布局之间 滑动
         * @param child
         * @param top 纵向滑动的top
         * @param dy
         * @return
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            int paddingTop=getPaddingTop();
            //布局高度-孩子高度-布局padding值
            int bottom=getHeight()-child.getHeight()-paddingTop;

            int newTop=Math.min(Math.max(paddingTop,top),bottom);
            return newTop;
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int count=getChildCount();
        int count1=getChildCount();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
}
