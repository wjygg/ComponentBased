package com.example.wangjingyun.componentbased.widget.bannerview;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/8/29.
 */

public class BannerViewPager extends ViewPager{

    private BannerBaseAdapter bannerBaseAdapter;
    private static final int TIME=3000;
    private Activity mActivity;
    private Handler handler=new Handler();
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this,TIME);
            Log.e("tag","runnable");
            setCurrentItem(getCurrentItem()+1);
        }
    };

    public BannerViewPager(Context context) {
        this(context,null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mActivity=(Activity) context;
        setRate(context);
    }

    /**
     * 开始轮播
     */
    public void startCarousel(){

        handler.postDelayed(runnable,TIME);

    }

    /**
     * 设置速率
     */
    public void setRate(Context context){

        try {
            BannerScroller bannerScroller=new BannerScroller(context);
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            //私有属性也可以改变值
            mScroller.setAccessible(true);
            mScroller.set(this,bannerScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 控件销毁的时候
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(handler!=null){
            handler.removeCallbacks(runnable);
            mActivity.getApplication().unregisterActivityLifecycleCallbacks(bannerActivityLifecycleCallBacks);
            handler=null;
        }
    }

    public void setAdapter(BannerBaseAdapter bannerBaseAdapter){

       this.bannerBaseAdapter=bannerBaseAdapter;
       //设置适配器
       setAdapter(new BannerAdapter());
       mActivity.getApplication().registerActivityLifecycleCallbacks(bannerActivityLifecycleCallBacks);


   }


   private class BannerAdapter extends PagerAdapter{

       @Override
       public int getCount() {
           return Integer.MAX_VALUE;
       }

       @Override
       public boolean isViewFromObject(View view, Object object) {
           return view==object;
       }

       @Override
       public void destroyItem(ViewGroup container, int position, Object object) {
          // super.destroyItem(container, position, object);
           container.removeView((View)object);
       }

       @Override
       public Object instantiateItem(ViewGroup container, final int position) {

           View view=bannerBaseAdapter.getView(position%bannerBaseAdapter.getCount());
           view.setOnClickListener(new OnClickListener() {
               @Override
               public void onClick(View v) {

                   bannerBaseAdapter.bannerClick(position%bannerBaseAdapter.getCount());
               }
           });
           container.addView(view);

           return view;
       }
   }

   //管理activity的生命周期
   private BannerActivityLifecycleCallBacks bannerActivityLifecycleCallBacks=new BannerActivityLifecycleCallBacks() {


       @Override
       public void onActivityResumed(Activity activity) {

           if(activity==mActivity){

               handler.postDelayed(runnable,TIME);
           }
       }

       @Override
       public void onActivityPaused(Activity activity) {

           if(activity==mActivity){
               handler.removeCallbacks(runnable);
           }
       }
   };


}
