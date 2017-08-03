package com.example.wangjingyun.componentbased.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wangjingyun.componentbased.R;

/**
 * 58同城组合loading 控件
 *
 * Created by wangjingyun on 2017/7/24.
 */

public class WithCityLoading extends LinearLayout {

    private WithCity withCity;

    private ImageView withimage;

    private boolean flag=true;

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

    private void initLayout(final Context context) {

        LayoutInflater.from(context).inflate(R.layout.loading_layout,this);

        withCity= (WithCity) findViewById(R.id.withcity);

        withimage= (ImageView) findViewById(R.id.withimage);

        post(new Runnable() {
            @Override
            public void run() {

                downAnimator(context);
            }
        });


    }

    private void downAnimator(final Context context) {

        if(flag==false){

            return;
        }
        Log.e("downAnimator","downAnimator");
        ObjectAnimator translationY = ObjectAnimator.ofFloat(withCity, "translationY", 0, Dp2Px(context, 70));
        translationY.setInterpolator(new AccelerateInterpolator());
        translationY.addListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                withCity.setCurrentType();
                //上抛
                upAnimator(context);
                //阴影变化



            }
        });
        //下方控件

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(withimage, "scaleX", 0.2f, 1f);

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.play(translationY).with(scaleX);
        animatorSet.setDuration(500);
        animatorSet.start();

    }

    public void upAnimator(final Context context){

        if(flag==false){

            return;
        }

        Log.e("upAnimator","upAnimator");

        ObjectAnimator translationY1 = ObjectAnimator.ofFloat(withCity, "translationY", Dp2Px(context, 70),0 );
        translationY1.setInterpolator(new DecelerateInterpolator());
        translationY1.addListener(new AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                downAnimator(context);

            }
        });

        //旋转动画
        ObjectAnimator rotationY = ObjectAnimator.ofFloat(withCity, "rotation", 0, 135);

        //下方控件

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(withimage, "scaleX", 1f, 0.2f);

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.play(translationY1).with(rotationY).with(scaleX);
        animatorSet.setDuration(500);
        animatorSet.start();
    }

    @Override
    public void setVisibility(int visibility) {

       super.setVisibility(View.INVISIBLE);
        //停止动画
        flag=false;

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
