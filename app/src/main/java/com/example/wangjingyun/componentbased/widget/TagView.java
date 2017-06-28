package com.example.wangjingyun.componentbased.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 * 自定义流式布局
 * Created by wangjingyun on 2017/6/14.
 */

public class TagView extends ViewGroup{


    public TagView(Context context) {
        this(context,null);

    }

    public TagView(Context context, AttributeSet attrs) {

        this(context,attrs,0);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMeasureSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightMeasureSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        //总宽度
        int width=0;

        //总高度
        int height=0;

        //行宽
        int lineWidth=0;

        //行高
        int lineHeight=0;

        //一行最高的高度
        int maxHeight=0;

        int  childCount=getChildCount();

        for(int i=0;i<childCount;i++){

            //得到子view
            View child = getChildAt(0);
            //测量 子view
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            //获取layout params 获取 子控件 margin 值
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();

            //子view 占据的宽度
            int childWidth=child.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin;

            //子view 占据的 高度
            int childHeight=child.getMeasuredHeight()+layoutParams.bottomMargin+layoutParams.topMargin;
            //大于换行
            if(lineWidth+childWidth>widthMeasureSpecSize){

                //最大宽度
                width=Math.max(lineWidth,childWidth);

                lineWidth=childWidth;

                //最高的控件 叠加整行的高
                height+=lineHeight;

                lineHeight=childHeight;

            }else{
                //不换行 累加宽度

                lineWidth+=childWidth;

                //最高的 控件
                lineHeight=Math.max(lineHeight,childHeight);


            }

            //累加最后一个的高度
            if(i==childCount-1){

                width=Math.max(width,lineWidth);
                height+=lineHeight;
            }

        }

        setMeasuredDimension((widthMeasureSpecMode==MeasureSpec.EXACTLY)? widthMeasureSpecSize:width,
                heightMeasureSpecMode==MeasureSpec.EXACTLY?heightMeasureSpecSize:height);

    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

        //
        
    }



    // 返回系统的layoutParams
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
}
