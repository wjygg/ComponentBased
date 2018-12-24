package com.example.wangjingyun.componentbased.okhttp;

/**
 * Created by hcDarren on 2017/11/18.
 */

public abstract class NamedRunnable implements Runnable{
    @Override
    public void run() {
        execute();
    }

    protected abstract void execute();
}
