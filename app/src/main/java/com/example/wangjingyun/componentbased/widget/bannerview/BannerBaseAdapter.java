package com.example.wangjingyun.componentbased.widget.bannerview;

import android.view.View;

/**
 * Created by Administrator on 2017/8/29.
 */

public abstract class BannerBaseAdapter {

    public abstract View getView(int position);

    public abstract int getCount();

    public abstract String getString(int position);

}
