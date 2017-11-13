package com.example.wangjingyun.componentbased.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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

import java.util.ArrayList;
import java.util.List;

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

    private DropDownMenuAdapter adapter;

    private LinearLayout linear_layout;

    private float framelayoutHeight;

    private List<View> tabListView=new ArrayList<View>();

    private List<View> contentListView=new ArrayList<View>();

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
        initEvent();
    }

    private void findById(){
        lin_tabview= (LinearLayout) view.findViewById(R.id.tabview);
        frame_content= (FrameLayout) view.findViewById(R.id.frame_content);
        transview=view.findViewById(R.id.transview);
        linear_layout= (LinearLayout) view.findViewById(R.id.frame_layout);
    }

    private void initEvent(){

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
            this.adapter=adapter;
            for( int i=0;i<count;i++){
                //添加tabview
                TextView tabView= (TextView) adapter.getTabView(context,i,this);
                tabView.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT,1);
                tabView.setLayoutParams(params);
                lin_tabview.addView(tabView);
                tabListView.add(tabView);
                tabView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        openMenu(v);
                    }
                });

                //添加contentview
                View contentView=adapter.getContentView(context,i,this);
                contentView.setVisibility(View.GONE);
                FrameLayout.LayoutParams frameLayoutParms=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
                contentView.setLayoutParams(frameLayoutParms);
                frame_content.addView(contentView);
                contentListView.add(contentView);

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

        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(linear_layout,"translationY",0,-framelayoutHeight);
        objectAnimator.setDuration(500);
        objectAnimator.addListener(new ThisAnimator() {
            @Override
            public void onAnimationStart(Animator animation) {


            }

            @Override
            public void onAnimationEnd(Animator animation) {


            }
        });
        objectAnimator.start();

    }


    private void openMenu(final View view){


          for(int i=0;i<tabListView.size();i++){

                    if(tabListView.get(i).getId()==view.getId()){
                        contentListView.get(i).setVisibility(View.VISIBLE);

                    }else{
                        contentListView.get(i).setVisibility(View.GONE);
                    }

                }

        //显示 当前view 隐藏其他view
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(linear_layout,"translationY",-framelayoutHeight,0);
        objectAnimator.setDuration(500);
        objectAnimator.addListener(new ThisAnimator() {
            @Override
            public void onAnimationStart(Animator animation) {


            }

            @Override
            public void onAnimationEnd(Animator animation) {

                //动画结束 隐藏 其余的 内容view、

            }
        });
        objectAnimator.start();


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed){

            framelayoutHeight=linear_layout.getMeasuredHeight();
        }
    }

    private abstract class ThisAnimator implements Animator.AnimatorListener{

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
}
