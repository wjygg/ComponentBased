package com.example.wangjingyun.componentbasesdk.mvpbase;

/**
 * Created by uggyy on 2018/5/7.
 */

public class MvpPresenter<T> {

    public T view;

    /**
     * p层 和view 层绑定
     * @param view
     */
    public void attach(T view){

        this.view=view;
    }
    /**
     * 解除view层和p层绑定
     */
    public void dettach(){

        if(this.view!=null){

            this.view=null;
        }
    }

}
