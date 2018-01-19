package com.example.wangjingyun.componentbased.activity.fragment;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseFragment;

/**
 * Created by Administrator on 2018/1/19.
 */

public class ViewPagerOneFragment extends BaseFragment{


    public static  ViewPagerOneFragment getInstance(){

        ViewPagerOneFragment fragment=new ViewPagerOneFragment();

        return fragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_viewpagerone_layout;
    }

    @Override
    public void initDatas() {

    }


}
