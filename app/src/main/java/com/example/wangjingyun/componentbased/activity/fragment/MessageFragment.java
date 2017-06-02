package com.example.wangjingyun.componentbased.activity.fragment;

import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.Message;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseFragment;
import com.example.wangjingyun.componentbased.widget.SlidingDiscolorationTextView;

import butterknife.InjectView;

/**
 * Created by Administrator on 2017/3/11.
 */

public class MessageFragment extends BaseFragment {

    @InjectView(R.id.slidingDiscolorationTextView)
    SlidingDiscolorationTextView slidingDiscolorationTextView;

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

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.0f, 0.0f);

        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float currentValues=(float)valueAnimator.getAnimatedValue();

                slidingDiscolorationTextView.setCurrentThis(currentValues);
            }
        });

        valueAnimator.start();

    }

}
