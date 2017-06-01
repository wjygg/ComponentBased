package com.example.wangjingyun.componentbased.activity.fragment;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseFragment;

/**
 * 推荐 fragment
 * Created by wangjingyun on 2017/6/1.
 */

public class RecommendFragment extends BaseFragment {


    public static RecommendFragment getInstance() {

        RecommendFragment fragment = new RecommendFragment();

        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recommend_layout;
    }

    @Override
    public void initDatas() {

    }
}
