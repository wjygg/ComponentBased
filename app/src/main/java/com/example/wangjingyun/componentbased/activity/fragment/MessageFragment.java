package com.example.wangjingyun.componentbased.activity.fragment;



import android.content.Context;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;

import android.util.Log;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.wangjingyun.componentbased.R;

import com.example.wangjingyun.componentbased.activity.base.BaseFragment;

import com.example.wangjingyun.componentbased.widget.SlidingDiscolorationTextView;
import com.example.wangjingyun.componentbased.widget.Tablayout.HorizonBaseAdapter;
import com.example.wangjingyun.componentbased.widget.Tablayout.HorizonTabLayout;
import com.example.wangjingyun.componentbasesdk.ioc.ViewById;


import java.util.ArrayList;

import java.util.List;






/**

 * Created by Administrator on 2017/3/11.

 */



public class MessageFragment extends BaseFragment {


    public static MessageFragment getInstance() {

        MessageFragment fragment = new MessageFragment();

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