package com.example.wangjingyun.componentbased.widget.Tablayout;

import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2017/9/18.
 */

public abstract class HorizonBaseAdapter {

    public abstract  int getCount();

    public abstract View getView(int position, Context context);

}
