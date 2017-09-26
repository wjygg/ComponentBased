package com.example.wangjingyun.componentbased.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.wangjingyun.componentbased.R;

/**
 *
 * qq5.0侧滑
 *
 * Created by Administrator on 2017/9/19.
 */

public class QQFiveView extends HorizontalScrollView{

    private float menuSize=200;

    private int windowManangerWidth;

    private  LinearLayout childAt;

    public QQFiveView(Context context) {
        this(context,null);
    }

    public QQFiveView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QQFiveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        windowManangerWidth=getwindowWidth(context);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.QQFiveView);
        //右侧空余的大小
        menuSize=typedArray.getDimension(R.styleable.QQFiveView_QQFiveViewMenuSize,menuSize);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        childAt = (LinearLayout) getChildAt(0);
        int childCount = childAt.getChildCount();

        if(childCount>2){
            throw  new RuntimeException("LinnerLayout中只能有2个子布局");
        }

        //menu的宽度
        ViewGroup.LayoutParams layoutParams = childAt.getChildAt(0).getLayoutParams();
        layoutParams.width= (int) (windowManangerWidth-menuSize);


        //content的宽度
        ViewGroup.LayoutParams layoutParams1 = childAt.getChildAt(1).getLayoutParams();
        layoutParams1.width= windowManangerWidth;

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        //默认关闭 x代表屏幕外的x轴距离 y代表屏幕外y轴的距离
        scrollTo(childAt.getChildAt(0).getWidth(),0);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){

            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                if(getScrollX()>childAt.getChildAt(0).getWidth()/2){
                    //关闭 带动画
                    smoothScrollTo(childAt.getChildAt(0).getWidth(),0);
                }else{
                    //打开 带动画
                    smoothScrollTo(0,0);
                }
                return  true;
        }
        return super.onTouchEvent(ev);
    }

    private int getwindowWidth(Context context){

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }


}
