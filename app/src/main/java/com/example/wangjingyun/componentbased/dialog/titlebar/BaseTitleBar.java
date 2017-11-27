package com.example.wangjingyun.componentbased.dialog.titlebar;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.dialog.BaseDialog;

/**
 * Created by Administrator on 2017/11/23.
 */
public class BaseTitleBar {

    private BaseTitleBarParams baseTitleBarParams;

    private View view;

    public BaseTitleBar(BaseTitleBarParams baseTitleBarParams){

        this.baseTitleBarParams=baseTitleBarParams;

        initView();

        initEvent();
    }


    private void initView(){

        //decorView 是framelayout 包含系统布局 LinearLayout 包含  framelayout id contentview 加载自己布局
        ViewGroup decorView = (ViewGroup) ((Activity) baseTitleBarParams.context).getWindow().getDecorView();

        LinearLayout childAt = (LinearLayout) decorView.getChildAt(0);

        view= LayoutInflater.from(baseTitleBarParams.context).inflate(R.layout.bar_basetitle_layout,childAt,false);

        childAt.addView(view,1);
    }

    private void initEvent(){

        //控件显示
        for(int i=0;i<baseTitleBarParams.integerSparseArray.size();i++){

            View view = getView(baseTitleBarParams.integerSparseArray.keyAt(i));
            if(baseTitleBarParams.integerSparseArray.get(baseTitleBarParams.integerSparseArray.keyAt(i))==View.VISIBLE)
            view.setVisibility(View.VISIBLE);

        }

        //控件图标

        for(int j=0;j<baseTitleBarParams.drawableSparseArray.size();j++){

            View view = getView(baseTitleBarParams.drawableSparseArray.keyAt(j));

            ((ImageView)view).setImageDrawable(baseTitleBarParams.drawableSparseArray.get(baseTitleBarParams.drawableSparseArray.keyAt(j)));

        }

        //控件字体

        for(int j=0;j<baseTitleBarParams.stringSparseArray.size();j++){

            View view = getView(baseTitleBarParams.stringSparseArray.keyAt(j));

            ((TextView)view).setText(baseTitleBarParams.stringSparseArray.get(baseTitleBarParams.stringSparseArray.keyAt(j)));

        }

        //字体颜色
        for(int j=0;j<baseTitleBarParams.colorSparseArray.size();j++){

            View view = getView(baseTitleBarParams.colorSparseArray.keyAt(j));

            ((TextView)view).setTextColor(baseTitleBarParams.colorSparseArray.get(baseTitleBarParams.colorSparseArray.keyAt(j)));

        }

        //点击事件
        for(int j=0;j<baseTitleBarParams.clickSparseArray.size();j++){

            View view = getView(baseTitleBarParams.clickSparseArray.keyAt(j));

            view.setOnClickListener(baseTitleBarParams.clickSparseArray.get(baseTitleBarParams.clickSparseArray.keyAt(j)));

        }
        //设置背景颜色
        view.setBackgroundColor(baseTitleBarParams.backGroundColor);

    }


    public <T extends View> T getView(int resId){

        if(view!=null){

           View mview=view.findViewById(resId);

            if(mview!=null){

                return (T) mview;
            }
            return null;
        }
        return null;
    }


    public static class Builder{

        private BaseTitleBarParams baseTitleBarParams;

        private Context context;

        public Builder(Context context){

            this.context=context;

            baseTitleBarParams=new BaseTitleBarParams(context);
        }

        /**
         * 设置 中间 字体
         * @param resId
         * @param string
         * @return
         */
        public Builder setAlignText(int resId,String string){

            baseTitleBarParams.integerSparseArray.put(resId,View.VISIBLE);

            baseTitleBarParams.stringSparseArray.put(resId,string);

            return this;
        }

        public Builder setAlignTextVisible(int resId){
            baseTitleBarParams.integerSparseArray.put(resId,View.VISIBLE);
            return this;
        }

        public Builder setAlignTextColor(int resId,int color){

            baseTitleBarParams.colorSparseArray.put(resId,ContextCompat.getColor(context, color));
            return this;
        }

        /**
         * 设置右边字体
         * @param resId
         * @param string
         * @return
         */
        public Builder setRightText(int resId,String string){

            baseTitleBarParams.integerSparseArray.put(resId, View.VISIBLE);

            baseTitleBarParams.stringSparseArray.put(resId,string);

            return this;

        }

        /**
         * 右边字体颜色
         * @param resId
         * @param color
         * @return
         */
        public Builder setRightTextColor(int resId,int color){
            baseTitleBarParams.colorSparseArray.put(resId,ContextCompat.getColor(context, color));
            return this;
        }

        /**
         * 设置右边字体显示
         * @param resId
         * @return
         */
        public Builder setRightTextVisible(int resId){
            baseTitleBarParams.integerSparseArray.put(resId, View.VISIBLE);
            return this;
        }

        /**
         * 设置右边字体
         * @param resId
         * @param drawable
         * @return
         */
        public Builder setRightIcon(int resId,int drawable){

            baseTitleBarParams.integerSparseArray.put(resId,View.VISIBLE);

            baseTitleBarParams.drawableSparseArray.put(resId,ContextCompat.getDrawable(context,drawable));

            return this;

        }

        /**
         * 右边图标 显示
         * @param resId
         * @return
         */
        public Builder setRightIconVisible(int resId){

            baseTitleBarParams.integerSparseArray.put(resId,View.VISIBLE);

            return this;
        }

        /**
         * 设置背景颜色
         * @param color
         * @return
         */
        public Builder setBackGround(int color){

            baseTitleBarParams.backGroundColor= ContextCompat.getColor(context,color);

            return this;
        }

        public Builder setLeftIconVisible(int resId){

            baseTitleBarParams.integerSparseArray.put(resId,View.VISIBLE);

            return this;
        }

        public Builder setLeftIcon(int resId,int drawable){

            baseTitleBarParams.integerSparseArray.put(resId,View.VISIBLE);

            baseTitleBarParams.drawableSparseArray.put(resId,ContextCompat.getDrawable(context,drawable));

            return this;
        }

        public Builder setOnClickListener(int resId,View.OnClickListener onClickListener){

            baseTitleBarParams.clickSparseArray.put(resId,onClickListener);

            return this;
        }


        public BaseTitleBar builder(){

            BaseTitleBar baseTitleBar=new BaseTitleBar(baseTitleBarParams);

            return baseTitleBar;
        }

    }
}
