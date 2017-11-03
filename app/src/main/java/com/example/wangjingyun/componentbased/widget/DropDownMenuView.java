package com.example.wangjingyun.componentbased.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wangjingyun.componentbased.R;

/**
 * 下拉菜单
 * Created by Administrator on 2017/11/2.
 */

public class DropDownMenuView extends LinearLayout {

    private Context context;

    private View view;

    private LinearLayout lin_tabview;

    private FrameLayout frame_content;

    private View transview;

    public DropDownMenuView(Context context) {
        this(context,null);
    }

    public DropDownMenuView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DropDownMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        view=LayoutInflater.from(context).inflate(R.layout.item_dropmenu_layout,this);
        findById();
    }

    private void findById(){
        lin_tabview= (LinearLayout) view.findViewById(R.id.tabview);
        frame_content= (FrameLayout) view.findViewById(R.id.frame_content);
        transview=view.findViewById(R.id.transview);
        transview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
    }

    /**
     * @param context 上下文
     * @param values 值
     * @return
     */
    public float getDesign(Context context,float values){

       return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,values,getResources().getDisplayMetrics());

    }
    public void setAdapter(DropDownMenuAdapter adapter){

        if(adapter==null){

            throw  new RuntimeException("adapter不能为null");
        }else{
            int count=adapter.getCount();

            for(int i=0;i<count;i++){

                //添加tabview
                TextView tabView= (TextView) adapter.getTabView(context,i,this);
                tabView.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT,1);
                tabView.setLayoutParams(params);
                lin_tabview.addView(tabView);

                //添加contentview
                View contentView=adapter.getContentView(context,i,this);
                FrameLayout.LayoutParams frameLayoutParms=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
                contentView.setLayoutParams(frameLayoutParms);
                contentView.setVisibility(View.GONE);
                frame_content.addView(contentView);
            }
        }

    }


    public abstract static class DropDownMenuAdapter{

        //tab 个数
        public abstract int getCount();

        /**
         * 回调 tabview
         * @param context
         * @param parent
         * @return
         */
        public abstract View getTabView(Context context, int position,ViewGroup parent);

        /**
         * 回调 内容 view
         * @param context
         * @param parent
         * @return
         */
        public abstract View getContentView(Context context,int position,ViewGroup parent);
    }


    private void closeMenu(){



    }









}
