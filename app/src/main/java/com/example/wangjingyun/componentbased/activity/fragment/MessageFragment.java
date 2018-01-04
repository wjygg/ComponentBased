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

    @ViewById(R.id.ll_message)
    LinearLayout ll_message;

    @ViewById(R.id.viewpager)
    ViewPager viewPager;

    @ViewById(R.id.horizontablayout)
    HorizonTabLayout horizontablayout;

    private String[] items = new String[]{"推荐", "直播", "视频", "段子", "精华"};
    private String[] hoitems = new String[]{"推荐", "直播", "视频", "段子", "精华","推荐", "直播", "视频", "段子", "精华","推荐", "直播", "视频", "段子", "精华","推荐", "直播", "视频", "段子", "精华"};
    private List<SlidingDiscolorationTextView> datas = new ArrayList<SlidingDiscolorationTextView>();

    private List<Fragment> fargments=new ArrayList<Fragment>();

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

        //初始化字体

        initView();
        initViewPager();

        horizontablayout.setAdapter(new HorizonBaseAdapter() {
            @Override
            public int getCount() {
                return hoitems.length;
            }

            @Override
            public View getView(int position, Context context) {

                TextView textView=new TextView(context);
                textView.setGravity(Gravity.CENTER);
                textView.setText(hoitems[position]);

                return textView;
            }
        });

    }
    private void initView() {

        LinearLayout.LayoutParams parmas = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);

        for (String item : items) {

            SlidingDiscolorationTextView view = new SlidingDiscolorationTextView(getActivity());

            view.setSlidingChangeColor(R.color.orange);

            view.setSlidingColor(R.color.black_alpha_30);

            view.setSlidingText(item);

            view.setSlidingTextSize(100);

            view.setLayoutParams(parmas);

            ll_message.addView(view);

            datas.add(view);

            fargments.add(RecommendFragment.getInstance());

        }
    }
    private void initViewPager() {
        viewPager.setAdapter(new MyFragmentPagerAdapter(getFragmentManager(),fargments));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                Log.e("messagefragment",positionOffset+"");
                //左边的
                SlidingDiscolorationTextView slidingDiscolorationTextView = datas.get(position);

                slidingDiscolorationTextView.setCurrentThis(positionOffset, SlidingDiscolorationTextView.TRUN.RIGHT_TOLEFT);

                if(position==items.length-1){

                    return;
                }
                //右边的
                SlidingDiscolorationTextView slidingDiscolorationTextView1 = datas.get(position + 1);

                slidingDiscolorationTextView1.setCurrentThis(positionOffset, SlidingDiscolorationTextView.TRUN.LEFT_TORIGHT);
            }
            @Override
            public void onPageSelected(int position) {

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

    }



    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {



        private  List<Fragment> datas;



        public MyFragmentPagerAdapter(FragmentManager manager, List<Fragment> datas) {



            super(manager);



            this.datas=datas;

        }



        @Override

        public Fragment getItem(int position) {

            return datas.get(position);

        }



        @Override

        public int getCount() {

            return datas.size();

        }

    }



}