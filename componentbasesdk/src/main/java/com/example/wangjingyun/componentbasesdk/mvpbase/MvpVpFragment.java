package com.example.wangjingyun.componentbasesdk.mvpbase;


import com.example.wangjingyun.componentbasesdk.base.BaseVpFragment;

/**
 * mvp 基类fragment
 * Created by wjy on 2018/5/7.
 */

public abstract class MvpVpFragment<T,P extends MvpPresenter<T>> extends BaseVpFragment {

    public P presenter;

    @Override
    public void initBefore() {
        super.initBefore();
        //初始化 presenter
        presenter=initPresenter();
        //presenter 层与view层绑定
        presenter.attach((T)this);

    }

    public abstract P initPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter!=null){
            //view层 和P 层解绑
            presenter.dettach();
        }
    }
}
