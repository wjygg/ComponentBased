package com.example.wangjingyun.componentbasesdk.mvpbase;


import android.os.Bundle;

import com.example.wangjingyun.componentbasesdk.base.BaseActivity;


/**
 *
 * Created by wjy on 2018/5/7.
 */

public abstract class MvpActivity<T,P extends MvpPresenter<T>> extends BaseActivity {

    public P presenter;

    @Override
    public void initBefore(Bundle savedInstanceState) {
        super.initBefore(savedInstanceState);
        //初始化P层
        presenter=initPresenter();
        //判断 P层不等于null
        if(presenter!=null){
            presenter.attach((T)this);
        }
    }


    public abstract P initPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除绑定
        if(presenter!=null){
            presenter.dettach();
        }
    }
}
