package com.example.wangjingyun.componentbasesdk.ioc;

import android.app.Activity;
import android.view.View;

/**
 *
 *  Created by Administrator on 2017/5/7.
 *
 */

public class ViewFilter {

    private Activity activity;

    private View view;

    public ViewFilter(Activity activity){

      this.activity=activity;
    }

    public ViewFilter(View view){

        this.view=view;
    }

    public View findViewById(int viewId){


        return activity!=null?activity.findViewById(viewId):view.findViewById(viewId);

    }

}
