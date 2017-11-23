package com.example.wangjingyun.componentbased.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.wangjingyun.componentbased.R;

import java.util.Random;

/**
 * 花束直播顶赞
 * Created by Administrator on 2017/11/15.
 */

public class BouquetPraiseView extends RelativeLayout{

    private Context context;

    private Drawable[] drawables=new Drawable[4];

    private Random random;

    private RelativeLayout.LayoutParams params;

    private int mDrawableWidth,mDrawableHeight;

    public BouquetPraiseView(Context context) {
        this(context,null);
    }

    public BouquetPraiseView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BouquetPraiseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        initView();
    }

    private void initView(){
        drawables[0]=getResources().getDrawable(R.mipmap.pl_blue);
        drawables[1]=getResources().getDrawable(R.mipmap.pl_red);
        drawables[2]=getResources().getDrawable(R.mipmap.pl_yellow);
        mDrawableWidth=drawables[0].getIntrinsicWidth();
        mDrawableHeight=drawables[0].getIntrinsicHeight();
        random=new Random();

        params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_HORIZONTAL,TRUE);
        params.addRule(ALIGN_PARENT_BOTTOM,TRUE);
    }


    public void  addView(){
        ImageView imageView=new ImageView(context);
        imageView.setLayoutParams(params);
        imageView.setImageDrawable(drawables[random.nextInt(drawables.length-1)]);
        addView(imageView);
        AnimatorSet animator = getAnimator(imageView);
        animator.start();
    }

    private AnimatorSet getAnimator(ImageView loveImage){
        AnimatorSet allAnimatorSet=new AnimatorSet();

        AnimatorSet animatorSet=new AnimatorSet();

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(loveImage, "alpha", 0.3f, 1.0f);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(loveImage, "scaleX", 0.3f, 1.0f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(loveImage, "scaleY", 0.3f, 1.0f);
        // 一起执行
        animatorSet.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator);
        animatorSet.setDuration(350);
        // 运行的路径动画  playSequentially 按循序执行
        allAnimatorSet.playSequentially(animatorSet, getBezierAnimator(loveImage));

        return allAnimatorSet;

    }

    private Animator getBezierAnimator(final ImageView loveImage){

        // 怎么确定四个点
        PointF point0 = new PointF(getMeasuredWidth() / 2 - mDrawableWidth / 2, getMeasuredHeight() - mDrawableHeight);
        // 确保 p2 点的 y 值 一定要大于 p1 点的 y 值
        PointF point1 = getPoint(1);
        PointF point2 = getPoint(2);
        PointF point3 = new PointF(random.nextInt(getMeasuredWidth()) - mDrawableWidth, 0);

        BezaTypeEvaluator bezaTypeEvaluator = new BezaTypeEvaluator(point1, point2);

        ValueAnimator valueAnimator = ObjectAnimator.ofObject(bezaTypeEvaluator, point0, point3);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                loveImage.setX(pointF.x);
                loveImage.setY(pointF.y);

                if(pointF.y==0){

                    removeView(loveImage);
                }
            }
        });
        valueAnimator.setDuration(3000);

        return valueAnimator;
    }



    //自定义贝塞尔估值器
    private class BezaTypeEvaluator implements TypeEvaluator<PointF>{

        private PointF point1,point2;

        public BezaTypeEvaluator(PointF point1,PointF point2){
            this.point1 = point1;
            this.point2  =point2;
        }

        @Override
        public PointF evaluate(float t, PointF point0, PointF point3) {
            // t 是 [0,1]  开始套公式 公式有四个点 还有两个点从哪里来（构造函数中来）
            PointF pointF = new PointF();

            pointF.x = point0.x*(1-t)*(1-t)*(1-t)
                    + 3*point1.x*t*(1-t)*(1-t)
                    + 3*point2.x*t*t*(1-t)
                    + point3.x*t*t*t;

            pointF.y = point0.y*(1-t)*(1-t)*(1-t)
                    + 3*point1.y*t*(1-t)*(1-t)
                    + 3*point2.y*t*t*(1-t)
                    + point3.y*t*t*t;

            return pointF;
        }
    }

    private PointF getPoint(int index) { // 1
        return new PointF(random.nextInt(getMeasuredWidth()) - mDrawableWidth, random.nextInt(getMeasuredHeight() / 2) + (index - 1) * (getMeasuredHeight() / 2));
    }




}
