package com.example.wangjingyun.componentbased.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
            View child = getChildAt(i);
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

    //记录所有的 控件
    private List<List<View>> mAllViews=new ArrayList<List<View>>();

    //记录每行的高
    private List<Integer> mHeight=new ArrayList<Integer>();

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

        mAllViews.clear();
        mHeight.clear();

        //总宽度
        int width=getWidth();

        //一行累加宽度
        int lineWidth=0;
        //一行最高的宽度
        int lineHeight=0;

        int childCount=getChildCount();

        List<View> views=new ArrayList<View>();

        for(int j=0;j<childCount;j++){

            View child=getChildAt(j);

            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();

            int childWidth=child.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin;

            int childHeight=child.getMeasuredHeight()+layoutParams.topMargin+layoutParams.bottomMargin;

            if(lineWidth+childWidth>width){

                //存储每行最高的
                mHeight.add(lineHeight);

                mAllViews.add(views);

                //添加一行view
                views=new ArrayList<View>();

                views.add(child);
                //换行
                lineWidth=childWidth;
                //行高重新赋值
                lineHeight=childHeight;

            }else{
                //累加宽度

                lineWidth=lineWidth+childWidth;

                lineHeight=Math.max(lineHeight,childHeight);

                views.add(child);

            }
        }

            // 记录最后一行  
                mHeight.add(lineHeight);
                mAllViews.add(views);

        int left=0;
        int top=0;

        //总行数
        int mAllViewsSize=mAllViews.size();
        for(int z=0;z<mAllViewsSize;z++){

            //每一行的 views
            views = mAllViews.get(z);

            //每行最高
            lineHeight=mHeight.get(z);

            for(int k=0;k<views.size();k++){

                View child=views.get(k);

                if(child.getVisibility()==View.GONE){

                    continue;
                }
                MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();

                int lc=left+layoutParams.leftMargin;
                int tc=top+layoutParams.topMargin;
                int rc=lc+child.getMeasuredWidth();
                int bc=tc+child.getMeasuredHeight();

                child.layout(lc,tc,rc,bc);

                left+=child.getMeasuredWidth()+layoutParams.rightMargin+
                        layoutParams.leftMargin;

            }
            left=0;
            top+=lineHeight;
        }
    }
    // 返回系统的layoutParams
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    public void setAdater(BaseAdapter adapter){

        //移除所有控件
        removeAllViews();

        if(adapter==null){
            throw new RuntimeException("BaseAdapter空指针异常");
        }
        for(int i=0;i<adapter.getCount();i++){

            View child = adapter.getView(i,this);

            addView(child);

        }

    }

    //adater设计模式
    public abstract static class BaseAdapter{

        public abstract int getCount();

        public abstract View getView(int count,TagView parents);


    }


}
