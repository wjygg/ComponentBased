package com.example.wangjingyun.componentbased.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

/**仿汽车之家
 * Created by Administrator on 2017/10/17.
 */

public class CarHomeView extends FrameLayout {

    private ViewDragHelper viewDragHelper;

    private boolean flag=true;

    private int measuredChildHeight;

    private  int  newTop=0;

    private View childView;

    private  float y=0;

    private boolean isOpen=false;

    public CarHomeView(Context context) {
        this(context,null);
    }

    public CarHomeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CarHomeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        viewDragHelper=ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                //滑动第二个
                return child==getChildAt(1);
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {

                return 0;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                newTop=top;
                if(top>=measuredChildHeight)
                    newTop= measuredChildHeight;
                if(top<=0)
                    newTop=0;
                return  newTop;

            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);

                if(releasedChild==childView){
                    //判断 上层布局距离头部距离
                    if(childView.getTop()<measuredChildHeight/2){
                        //关闭
                        boolean close = close();
                        isOpen=false;
                        Log.e("close",""+close);

                    }else{
                        //打开
                        isOpen=true;
                        boolean open = open();
                        Log.e("open",""+open);
                    }
                }
            }
        });
    }

    public boolean open(){
        if(viewDragHelper.smoothSlideViewTo(childView,0,measuredChildHeight)){
            postInvalidate();
            return true;
        }
        return false;

    }

    public boolean close(){

        if(viewDragHelper.smoothSlideViewTo(childView,0,0)){
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

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if(isOpen){
            return  true;
        }
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:

               y=ev.getY();
                viewDragHelper.processTouchEvent(ev);

              break;

            case MotionEvent.ACTION_MOVE:
                //listview没滚动时  不会调用requestDisallowInterceptTouchEvent 会调用 move 和up事件

               //判断 listview 滚动到头部 拦截事件
                float newy=ev.getY();

              //  Toast.makeText(getContext(),""+(newy-y),Toast.LENGTH_SHORT).show();

              if(!canChildScrollUp()&&newy-y>0){

                    return true;

                }
                break;

            case MotionEvent.ACTION_UP:

                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        viewDragHelper.processTouchEvent(event);

        return true;
    }


    /**
     * @return Whether it is possible for the child view of this layout to
     *         scroll up. Override this if the child view is a custom view.
     */
    public boolean canChildScrollUp() {

        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (childView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) childView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(childView, -1) || childView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(childView, -1);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int childCount = getChildCount();

        if(childCount>2){
            throw  new RuntimeException("carhome只能有两个子布局");
        }
        childView = getChildAt(1);
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
