package com.example.wangjingyun.componentbased.widget.bannerview;

import android.view.View;

/**
 * Created by Administrator on 2017/8/29.
 */

public abstract class BannerBaseAdapter {

    public abstract View getView(int position);

    public abstract int getCount();

    public String getString(int position) {
        return "";
    }

    public abstract void bannerClick(int position);

}
