package com.example.wangjingyun.componentbased.widget.Tablayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * 自定义 滑动tablayout
 * Created by Administrator on 2017/9/18.
 */

public class HorizonTabLayout extends HorizontalScrollView{

    private HorizonBaseAdapter adapter;
    private Context context;
    private DisplayMetrics metrics;
    public HorizonTabLayout(Context context) {
        this(context,null);
    }

    public HorizonTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HorizonTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //设置scrowview 子view填充 scrowview
        setFillViewport(true);
        this.context=context;
        metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
    }

    public void setAdapter(HorizonBaseAdapter adapter){

        this.adapter=adapter;

        ViewGroup.LayoutParams viewGroupLayoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout layout=new LinearLayout(context);
        //设置横向
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setBackgroundColor(Color.RED);
        layout.setLayoutParams(viewGroupLayoutParams);

        //父布局添加
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(metrics.widthPixels/4,LinearLayout.LayoutParams.WRAP_CONTENT);
        for(int i=0;i<adapter.getCount();i++){

            View view=adapter.getView(i,context);

            view.setLayoutParams(layoutParams);

            layout.addView(view);
        }

        addView(layout);
    }






}
