package com.example.wangjingyun.componentbased.activity.fragment;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseFragment;

/**
 * Created by Administrator on 2017/3/11.
 */

public class MineFragment extends BaseFragment {


    public static MineFragment getInstance() {

        MineFragment fragment = new MineFragment();

        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_layout;
    }

    @Override
    public void initDatas() {


    }
}
