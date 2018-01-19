package com.example.wangjingyun.componentbased.activity.fragment;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseFragment;

/**
 * Created by Administrator on 2018/1/19.
 */

public class ViewPagerFourFragment extends BaseFragment {


    public static  ViewPagerFourFragment getInstance(){

        ViewPagerFourFragment fragment=new ViewPagerFourFragment();

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
