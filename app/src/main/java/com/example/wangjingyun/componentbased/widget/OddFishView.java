package com.example.wangjingyun.componentbased.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;

import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import android.widget.ScrollView;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/11/27.
 */

public class OddFishView extends FrameLayout {

    private ViewDragHelper viewDragHelper;

    //默认距离头部
    private int scrowViewTop;

    private int newTop;

    private  ScrollView scrollView;

    private float y=0;

    private Button btn_bttom;

    private int btnHeight;

    private int width;

    private int height;

    private int marginScrollHeight=500;

    private int scrollViewHeight;

    public OddFishView(@NonNull Context context) {
        this(context,null);
    }

    public OddFishView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public OddFishView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initGetWindowWidthAndHeight();

        viewDragHelper=ViewDragHelper.create(this,1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {

                //设置scrowview布局可以滑动
                 return child==getChildAt(1);
            }

            //设置所有的view 不能横着滑动
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {

                return 0;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {

                newTop=top;

                if(top>=scrowViewTop){

                   newTop=scrowViewTop;
                }

                return newTop;
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);

                //获取scrowview 的top
                if(changedView==getChildAt(1)){

                    scrowViewTop=top;
                }
            }
        });
    }

    private void initGetWindowWidthAndHeight() {

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
              width = wm.getDefaultDisplay().getWidth();
              height = wm.getDefaultDisplay().getHeight();
    }

    private boolean closeScrollView(){

        if(viewDragHelper.smoothSlideViewTo(scrollView,0,scrollViewHeight)){
            postInvalidate();
            return true;
        }

        return false;

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(viewDragHelper.continueSettling(true)){

            invalidate();
        }
    }

    public boolean openScrollView(){
        if(viewDragHelper.smoothSlideViewTo(scrollView,0,marginScrollHeight)){
            postInvalidate();
            return true;
        }
        return false;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //自己处理 可以滑动
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {


        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(scrowViewTop>0){

                    return true;
                }else{

                   break;
                }


            case MotionEvent.ACTION_MOVE:

                if(scrowViewTop>0){

                    return true;
                }else{

                   break;
                }

            case MotionEvent.ACTION_UP:

                break;
        }

        return super.onInterceptTouchEvent(ev);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //获取scrollView
        scrollView= (ScrollView) getChildAt(1);
        //底部button
        btn_bttom= (Button) getChildAt(2);
    }


    /**
     * @return Whether it is possible for the child view of this layout to
     *         scroll up. Override this if the child view is a custom view.
     */
    public boolean canChildScrollUp() {

        return ViewCompat.canScrollVertically(scrollView, -1);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //获取scrowview 的高度
        scrollViewHeight=getChildAt(1).getMeasuredHeight();
        btnHeight=btn_bttom.getMeasuredHeight();

        openScrollView();

        RecyclerView recyclerView= (RecyclerView) getChildAt(0);
        recyclerView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(scrowViewTop==500){
                    closeScrollView();
                }
                return false;
            }
        });
    }


}
