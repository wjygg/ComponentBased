package com.example.wangjingyun.componentbasesdk.mvpbase;


import android.os.Bundle;

import com.example.wangjingyun.componentbasesdk.base.BaseActivity;

/**
 *
 * @param <T> T是Persenter 回调
 * @param <M> model 层
 * @param <P>
 */

public abstract class MvpActivity<T,M,P extends MvpPresenter<T,M>> extends BaseActivity {

    public P presenter;

    @Override
    public void initBefore(Bundle savedInstanceState) {
        super.initBefore(savedInstanceState);
        //初始化P层
        presenter=initPresenter();
        //判断 P层不等于null
        if(presenter!=null){
            presenter.attachView((T)this);
        }
    }


    public abstract P initPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除绑定
        if(presenter!=null){
            presenter.detachView();
        }
    }
}
