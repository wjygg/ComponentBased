package com.example.wangjingyun.componentbased.activity.mvp;

import com.example.wangjingyun.componentbasesdk.mvpbase.MvpActivity;

public class MvpTestActivity extends MvpActivity<IMvpAcivityListener,MvpTestModel,MvpTestPresenter>implements IMvpAcivityListener{
    @Override
    public MvpTestPresenter initPresenter() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
