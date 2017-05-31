package com.example.wangjingyun.componentbased.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wangjingyun.componentbasesdk.ioc.ViewUtils;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/11.
 */

public abstract  class BaseFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View  view=inflater.inflate(getLayoutId(),null);

        ButterKnife.inject(this,view);

        ViewUtils.Inject(view,this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDatas();
    }


    public abstract int getLayoutId();

    public abstract void initDatas();
}
