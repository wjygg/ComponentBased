package com.example.wangjingyun.componentbased.activity.fragment;

import com.example.wangjingyun.componentbased.activity.base.BaseFragment;

/**
 * Created by Administrator on 2017/3/11.
 */

public class MessageFragment extends BaseFragment {


    public static MineFragment getInstance(){

        MineFragment fragment=new MineFragment();

        return fragment;
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initDatas() {

    }

}
