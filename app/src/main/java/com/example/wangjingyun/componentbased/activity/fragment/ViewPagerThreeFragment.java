package com.example.wangjingyun.componentbased.activity.fragment;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseFragment;

/**
 * Created by Administrator on 2018/1/19.
 */

public class ViewPagerThreeFragment extends BaseFragment {

    public static ViewPagerThreeFragment getInstance(){

        ViewPagerThreeFragment fragment=new ViewPagerThreeFragment();

        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_viewpagerthree_layout;
    }

    @Override
    public void initDatas() {

    }

}
