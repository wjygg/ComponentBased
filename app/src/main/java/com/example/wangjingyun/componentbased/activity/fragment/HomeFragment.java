package com.example.wangjingyun.componentbased.activity.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseFragment;

import com.example.wangjingyun.componentbased.utils.StatusBarUtils;
import com.example.wangjingyun.componentbased.widget.CircularScaleDiagramView;
import com.example.wangjingyun.componentbased.widget.QQView;
import com.example.wangjingyun.componentbased.widget.TagView;
import com.example.wangjingyun.componentbasesdk.ioc.ViewById;


import java.util.ArrayList;
import java.util.List;



/**
 * Created by Administrator on 2017/3/11.
 */

public class HomeFragment extends BaseFragment{


    public static HomeFragment getInstance(){

        HomeFragment fragment=new HomeFragment();

        return fragment;
    }

    @Override
    public void setStatusBar(View view) {
        super.setStatusBar(view);
        StatusBarUtils.setFragmentStatusBarColor(getActivity(),view,Color.BLUE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_layout;
    }

    @Override
    public void initDatas() {


    }

}
