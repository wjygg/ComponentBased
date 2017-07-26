package com.example.wangjingyun.componentbased.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.example.wangjingyun.componentbased.R;

/**
 * 58同城组合loading 控件
 *
 * Created by wangjingyun on 2017/7/24.
 */

public class WithCityLoading extends LinearLayout {

    private WithCity withCity;
    public WithCityLoading(Context context) {
        this(context,null);
    }

    public WithCityLoading(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WithCityLoading(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initLayout(context);
    }

    private void initLayout(Context context) {

        LayoutInflater.from(context).inflate(R.layout.loading_layout,this);

        withCity= (WithCity) findViewById(R.id.withcity);

        beginAnimator(context);

    }

    private void beginAnimator(Context context) {

        ObjectAnimator translationY = ObjectAnimator.ofFloat(withCity, "translationY", 0, Dp2Px(context, 70));
        translationY.setInterpolator(new AccelerateInterpolator());
        translationY.addListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                withCity.setCurrentType();
            }
        });


        ObjectAnimator translationY1 = ObjectAnimator.ofFloat(withCity, "translationY", Dp2Px(context, 70),0 );
        translationY1.setInterpolator(new DecelerateInterpolator());
        translationY1.addListener(new AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {


            }
        });

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.playSequentially(translationY,translationY1);
        animatorSet.start();
    }


    public int Dp2Px(Context context, float dp) {

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private  abstract class AnimatorListener implements Animator.AnimatorListener{


        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

}
