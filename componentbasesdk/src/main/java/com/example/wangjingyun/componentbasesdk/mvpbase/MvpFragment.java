package com.example.wangjingyun.componentbasesdk.mvpbase;

import com.example.wangjingyun.componentbasesdk.base.BaseFragment;



/**
 * 继承普通基类 fragment 的mvp
 * Created by uggyy on 2018/5/9.
 */

public abstract class MvpFragment<T,P extends MvpPresenter<T>> extends BaseFragment {

    public P presenter;

    @Override
    public void initBefore() {
        super.initBefore();
        //初始化 presenter
        presenter=initPresenter();
        //presenter 层与view层绑定
        presenter.attachView((T)this);

    }

    public abstract P initPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter!=null){
            //view层 和P 层解绑
            presenter.detachView();
        }
    }

}
