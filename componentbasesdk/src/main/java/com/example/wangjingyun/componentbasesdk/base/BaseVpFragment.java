package com.example.wangjingyun.componentbasesdk.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * 基类fragment 懒加载 fragmentpageadapter 使用
 *
 * Created by wjy on 2018/5/7.
 */

public abstract class BaseVpFragment extends Fragment{

    //fragement懒加载
    public boolean isVisible;
    public boolean isPrepared;
    public boolean isFirst=true;

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

        isPrepared=true;
        //初始化数据(一般指调用网络请求方法)
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        //视图可见
        if(getUserVisibleHint()){
            isVisible=true;
            lazyLoad();
        }else{
            isVisible=false;
        }

    }

    public void lazyLoad(){

        if(!isVisible||!isPrepared||!isFirst){
            return;
        }
        initDatas();
        //初始化事件
        initEvent();

        isFirst=false;
    }

    public void initBefore(){}

    public abstract int getContentViewId();

    public abstract void initDatas();

    public abstract void initEvent();


}
