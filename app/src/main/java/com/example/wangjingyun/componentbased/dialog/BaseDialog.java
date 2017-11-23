package com.example.wangjingyun.componentbased.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/11/22.
 */

public class BaseDialog extends Dialog {

    public View view;

    public BaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }


    public void apply(DialogParams dialogParams){

        if(dialogParams.view==null&&dialogParams.mViewResViewId==0){

            throw new RuntimeException("请设置布局");


        }

        //设置布局
        if(dialogParams.view==null&&dialogParams.mViewResViewId!=0){

            view=View.inflate(dialogParams.context,dialogParams.mViewResViewId,null);

            setContentView(view);
        }

        if(dialogParams.view!=null&&dialogParams.mViewResViewId==0){

            view=dialogParams.view;
            setContentView(view);
        }

        //设置基本参数

        Window window = getWindow();

        window.setLayout(dialogParams.mWidth,dialogParams.mHeight);

        window.setGravity(dialogParams.mGravity);

        if(dialogParams.mAnimation!=0){

           window.setWindowAnimations(dialogParams.mAnimation);
        }

        //设置监听

        for(int i=0;i<dialogParams.viewClickSparseArray.size();i++){

            //获取控件
            View view = getView(dialogParams.viewClickSparseArray.keyAt(i));
            //设置点击事件
            view.setOnClickListener(dialogParams.viewClickSparseArray.get(dialogParams.viewClickSparseArray.keyAt(i)));
        }

        //修改字符串

        for(int j=0;j<dialogParams.stringSparseArray.size();j++){

            //获取控件
            TextView textView = getView(dialogParams.stringSparseArray.keyAt(j));
            //设置点击事件
            textView.setText(dialogParams.stringSparseArray.get(dialogParams.stringSparseArray.keyAt(j)));
        }
    }
    /**
     * 获取布局中的控件
     * @param resId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int resId){

        if(view!=null){

         View mView=view.findViewById(resId);

            if(mView!=null){

                return (T) mView;
            }
            return null;
        }

        return null;
    }

    public static class Builder{


        private DialogParams dialogParams;

        public Builder(Context context){

            this(context,0);
        }

        public Builder(Context context,int themeResId){
            //初始化参数类
            dialogParams=new DialogParams(context,themeResId);

        }


        /**
         * 设置布局
         * @param view
         */
        public Builder setView(View view){

            dialogParams.view=view;
            dialogParams.mViewResViewId=0;

            return this;

        }

        /**
         * 设置布局
         * @param layoutResId
         */
        public Builder setView(int layoutResId){

            dialogParams.view=null;
            dialogParams.mViewResViewId=layoutResId;

            return this;

        }

        /**
         * 设置宽度
         * @param width
         * @return
         */
        public Builder setWidth(int width){

            dialogParams.mWidth=width;

            return this;

        }

        /**
         * 设置高度
         * @param height
         * @return
         */
        public Builder setHeight(int height){


            dialogParams.mHeight=height;

            return  this;
        }

        /**
         * 显示位置
         * @param gravity
         * @return
         */
        public Builder setGravity(int gravity){

            dialogParams.mGravity=gravity;
            return  this;
        }

        /**
         * 设置动画
         * @param styleResId
         * @return
         */
        public Builder setAnimation(int styleResId){

          dialogParams.mAnimation=styleResId;

            return this;
        }

        //控件点击事件
        public Builder setOnClickListener(int resId,View.OnClickListener onClickListener){

            dialogParams.viewClickSparseArray.put(resId,onClickListener);

            return  this;
        }

        public Builder setText(int resId,String string){

            dialogParams.stringSparseArray.put(resId,string);

            return this;
        }


        public BaseDialog builder(){

            BaseDialog baseDialog=new BaseDialog(dialogParams.context,dialogParams.themeResId);

            baseDialog.apply(dialogParams);

            return  baseDialog;

        }





    }
}
