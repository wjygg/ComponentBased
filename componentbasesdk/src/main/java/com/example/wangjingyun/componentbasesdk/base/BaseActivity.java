package com.example.wangjingyun.componentbasesdk.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sicheng.ydjw.commonsdk.manager.AppManager;

import butterknife.ButterKnife;

/**
 * 基类activity
 * Created by wjy on 2018/5/7.
 */

public abstract class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取布局id
        setContentView(getContentViewId());
        //绑定注解
        ButterKnife.bind(this);
        //activity 添加进占中
        AppManager.getAppManager().addActivity(this);
        //抛出mvp调用
        initBefore(savedInstanceState);
        //初始化状态栏颜色
        setStatusBarColors();
        //初始化
        init();

    }

    public void setStatusBarColors(){

    }
    public abstract int getContentViewId();

    public void initBefore(Bundle savedInstanceState){}

    //初始化视图及数据
    private void init() {
        initView();
        initData();
    }

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //栈中移除activity
        AppManager.getAppManager().finishActivity(this);
    }
}
