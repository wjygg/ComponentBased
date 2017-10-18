package com.example.wangjingyun.componentbased.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
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
 *
 * Created by Administrator on 2017/9/19.
 */

public class QQFiveView extends HorizontalScrollView{

    private float menuSize=200;

    private int windowManangerWidth;

    private  LinearLayout childAt;

    private GestureDetector gestureDetector;

    private boolean flag=true;

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

        initGestureDetector(context);
    }
    private void initGestureDetector(Context context){

            gestureDetector=new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            /**
             *
             * @param e1
             * @param e2
             * @param velocityX x轴 快速右滑 是正值 左划是负值
             * @param velocityY
             * @return
             */
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                //打开菜单
                if(velocityX>0){
                    //带动画
                    smoothScrollTo(0,0);

                }else{
                    //关闭菜单
                    smoothScrollTo(childAt.getChildAt(0).getWidth(),0);
                }

                return true;
            }
        });


    }


    //setContentView 执行 onresume之后view绘制  onMeasure -> onlayout -> onDraw
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

    //摆放 layout
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if(flag){
            flag=false;
            //默认关闭 x代表屏幕外的x轴距离 y代表屏幕外y轴的距离
            scrollTo(childAt.getChildAt(0).getWidth(),0);
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("tagqqdispatchTouch",ev.getAction()+"");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("tagqqonIntercepTtouch",ev.getAction()+"");
        float getX= ev.getX();

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:

                if(getX>childAt.getChildAt(0).getWidth()){
                    //拦截 并且关闭
                    scrollTo(childAt.getChildAt(0).getWidth(),0);
                    return true;
                }
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        Log.e("tagqqonTouch",ev.getAction()+"");


        if(gestureDetector.onTouchEvent(ev)){
            //返回true 快速滑动 操作

            return true;
        }

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
                return true;

        }
        return  super.onTouchEvent(ev);
    }

    private int getwindowWidth(Context context){

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }


    /**
     * DisplayMetrics是一个获取屏幕信息的类，density是设备密度。
     * 在此可见applyDemension，第一个参数是指第二个参数值得单位，
     * 并将该单位的值转换为px。例子中我们传入单位为dp，值为20.
     * @param context
     * @param value
     * @return
     */
    public float getDesign(Context context,float value){
       return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }


}
