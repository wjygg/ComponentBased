package com.example.wangjingyun.componentbased.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2018/1/19.
 */

public class ViewPagerView extends ViewPager{


    private float downX ;    //按下时 的X坐标
    private float downY ;    //按下时 的Y坐标

    public ViewPagerView(Context context) {
        super(context);
    }

    public ViewPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int curPosition;
       //在触发时回去到起始坐标
        float x= event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //将按下时的坐标存储
                downX = x;
                downY = y;
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                curPosition = this.getCurrentItem();
                int count = this.getAdapter().getCount();

                float dx= x-downX;
                float dy = y-downY;

                //通过距离差判断方向
                int orientation = getOrientation(dx, dy);
                //当前页面是第一个 并且往右滑动 给父亲拦截

                if(curPosition==0&&orientation=='r'){

                    getParent().requestDisallowInterceptTouchEvent(false);

                    //当前 页面是最后一个 并且往左滑动 给父亲拦截
                }else if(curPosition == count - 1&&orientation=='l'){
                    getParent().requestDisallowInterceptTouchEvent(false);
                    //否则 都是自己拦截
                }else{

                    getParent().requestDisallowInterceptTouchEvent(true);
                }
        }
        return super.dispatchTouchEvent(event);
    }

    private int getOrientation(float dx, float dy) {

        if (Math.abs(dx)>Math.abs(dy)){
            //X轴移动
            return dx>0?'r':'l';
        }else{
            //Y轴移动
            return dy>0?'b':'t';
        }
    }
}
