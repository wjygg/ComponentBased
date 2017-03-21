package com.example.wangjingyun.componentbased.activity.fragment;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseFragment;

/**
 * Created by Administrator on 2017/3/11.
 */

public class MessageFragment extends BaseFragment {


    public static MessageFragment getInstance(){

        MessageFragment fragment=new MessageFragment();

        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message_layout;
    }

    @Override
    public void initDatas() {

    }

}
