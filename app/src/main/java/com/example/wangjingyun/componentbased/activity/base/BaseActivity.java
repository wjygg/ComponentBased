package com.example.wangjingyun.componentbased.activity.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/11.
 */

public  abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(getLayoutId());
        ButterKnife.inject(this);
        initDatas();
    }




    public abstract int getLayoutId();

    public abstract void initDatas();



}
