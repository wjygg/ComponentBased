package com.example.wangjingyun.componentbased.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import ioc.ViewUtils;

/**
 * Created by Administrator on 2017/3/11.
 *
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.inject(this);
        ViewUtils.Inject(this);
        initDatas();
    }

    public abstract int getLayoutId();

    public abstract void initDatas();



}
