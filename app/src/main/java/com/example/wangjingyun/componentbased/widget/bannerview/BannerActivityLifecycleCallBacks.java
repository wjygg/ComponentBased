package com.example.wangjingyun.componentbased.widget.bannerview;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by Administrator on 2017/9/5.
 */

public abstract class BannerActivityLifecycleCallBacks implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

}
