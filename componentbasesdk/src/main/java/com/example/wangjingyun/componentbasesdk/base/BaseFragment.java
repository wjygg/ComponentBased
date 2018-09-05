package com.example.wangjingyun.componentbasesdk.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * 普通基类fragment
 * Created by uggyy on 2018/5/9.
 */

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(getContentViewId(),container,false);
        //黄油刀绑定控件
        ButterKnife.bind(this,view);
        //抛出mvp
        initBefore();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //初始化数据(一般指调用网络请求方法)
        initDatas();

        initEvent();
    }



    public void initBefore(){}

    public abstract int getContentViewId();

    public abstract void initDatas();

    public abstract void initEvent();

}
