package com.example.wangjingyun.componentbased.widget.bannerview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wangjingyun.componentbased.R;

/**
 * Created by 轮播图组合控件 on 2017/8/30.
 */

public class BannerView extends LinearLayout {

    private BannerViewPager bannerViewPager;

    private TextView content;

    private LinearLayout dotComBination;

    private BannerBaseAdapter bannerBaseAdapter;

    private Context context;

    public BannerView(Context context) {
        this(context,null);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context=context;
        /**
         * 加载布局
         */
        inflate(context, R.layout.bannerview_layout, this);
        bannerViewPager= (BannerViewPager) findViewById(R.id.viewpageradapter);
        content= (TextView) findViewById(R.id.content);
        dotComBination= (LinearLayout) findViewById(R.id.dotcombination);
    }


    public void setAdapter(final BannerBaseAdapter bannerBaseAdapter){

        this.bannerBaseAdapter=bannerBaseAdapter;

        bannerViewPager.setAdapter(bannerBaseAdapter);

        //设置第一页内容
        content.setText(bannerBaseAdapter.getString(0));

        //初始化小点
        initDots();
        //viewpager添加监听
        bannerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }
            @Override
            public void onPageSelected(int position) {

                int currentItem=position%bannerBaseAdapter.getCount();

                content.setText(bannerBaseAdapter.getString(currentItem));

                pageSelected(currentItem);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void pageSelected(int position){

        Log.d("tag",position+"");//position 从第二页开始

        for(int i=0;i<bannerBaseAdapter.getCount();i++){

            BannerCircleImageView view= (BannerCircleImageView) dotComBination.getChildAt(i);

                if(i==position){
                    //当前item
                    view.setImageColor(BannerCircleImageView.DotColor.RED);
                }else{

                    view.setImageColor(BannerCircleImageView.DotColor.WHITE);
                }


        }
    }

    /**
     * 初始化小点
     */
    private void initDots(){

        int dotCounts=bannerBaseAdapter.getCount();

        for(int i=0;i<dotCounts;i++){

            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(20,20);
            params.rightMargin=20;

            BannerCircleImageView bannerCircleImageView=new BannerCircleImageView(context);

            if(i==0){
                bannerCircleImageView.setImageColor(BannerCircleImageView.DotColor.RED);
            }else{
                bannerCircleImageView.setImageColor(BannerCircleImageView.DotColor.WHITE);
            }

            bannerCircleImageView.setLayoutParams(params);

            dotComBination.addView(bannerCircleImageView);
        }

    }
    /**
     * 开始轮播
     */
    public void startCarousel(){

        bannerViewPager.startCarousel();
    }




}
