package com.example.wangjingyun.componentbased.widget.bannerview;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by Administrator on 2017/8/29.
 */

public class BannerScroller extends Scroller {
    private int mDuration=900;

    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {

        super.startScroll(startX, startY, dx, dy, mDuration);
    }

}
