package com.example.wangjingyun.componentbased.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseActivity;
import com.example.wangjingyun.componentbased.activity.fragment.ViewPagerOneFragment;
import com.example.wangjingyun.componentbased.activity.fragment.ViewPagerThreeFragment;
import com.example.wangjingyun.componentbased.activity.fragment.ViewPagerTwoFragment;
import com.example.wangjingyun.componentbasesdk.ioc.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/19.
 */

public class ViewPagerActivity extends BaseActivity{

    @ViewById(R.id.viewpager)
    ViewPager viewpager;

    private List<Fragment> fragmentList=new ArrayList<>();

    @ViewById(R.id.bar_net)
    private ImageView bar_net;

    @ViewById(R.id.bar_music)
    private ImageView bar_music;

    @ViewById(R.id.bar_friends)
    private ImageView bar_friends;

    private List<ImageView> imgDatas=new ArrayList<>();

    @Override
    public int getLayoutId() {

        return R.layout.activity_viewpager_layout;
    }

    @Override
    public void initDatas() {

        fragmentList.add(ViewPagerOneFragment.getInstance());
        fragmentList.add(ViewPagerTwoFragment.getInstance());
        fragmentList.add(ViewPagerThreeFragment.getInstance());

        imgDatas.add(bar_net);
        imgDatas.add(bar_music);
        imgDatas.add(bar_friends);


        bar_net.setSelected(true);
        bar_net.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewpager.setCurrentItem(0);
            }
        });

        bar_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewpager.setCurrentItem(1);
            }
        });

        bar_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewpager.setCurrentItem(2);
            }
        });

        viewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),fragmentList));
        viewpager.setOffscreenPageLimit(3);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                //选中图片
               for(int i=0;i<=imgDatas.size()-1;i++){

                   if(position==i){
                       imgDatas.get(i).setSelected(true);
                   }else{
                       imgDatas.get(i).setSelected(false);
                   }
               }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }




    private static class ViewPagerAdapter extends FragmentPagerAdapter{

        private  List<Fragment> fragmentList;

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
