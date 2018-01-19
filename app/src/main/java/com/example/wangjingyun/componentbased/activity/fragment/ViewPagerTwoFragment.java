package com.example.wangjingyun.componentbased.activity.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.ViewPagerActivity;
import com.example.wangjingyun.componentbased.activity.base.BaseFragment;
import com.example.wangjingyun.componentbased.widget.ViewPagerView;
import com.example.wangjingyun.componentbasesdk.ioc.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/19.
 */

public class ViewPagerTwoFragment extends BaseFragment{


    @ViewById(R.id.viewpager)
    ViewPagerView viewpager;

    @ViewById(R.id.tablayout)
    TabLayout tablayout;

    private List<Fragment> fragmentList=new ArrayList<>();

    public static ViewPagerTwoFragment getInstance(){

        ViewPagerTwoFragment fragment=new ViewPagerTwoFragment();

        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_viepagertwo_layout;
    }

    @Override
    public void initDatas() {

        fragmentList.add(ViewPagerOneFragment.getInstance());
        fragmentList.add(ViewPagerThreeFragment.getInstance());
        fragmentList.add(ViewPagerFourFragment.getInstance());


        viewpager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(),fragmentList));
        tablayout.setupWithViewPager(viewpager);

        tablayout.getTabAt(0).setText("美女");
        tablayout.getTabAt(1).setText("直播");
        tablayout.getTabAt(2).setText("我的");

    }


    private static class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;

        public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList=fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
