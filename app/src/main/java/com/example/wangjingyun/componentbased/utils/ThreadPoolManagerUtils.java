package com.example.wangjingyun.componentbased.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/12/21.
 */

public class ThreadPoolManagerUtils {


    /**
     *单例设计模式（饿汉式）
     *单例首先私有化构造方法，然后饿汉式一开始就开始创建，并提供get方法
     */
    private static ThreadPoolManagerUtils mInstance=new

            ThreadPoolManagerUtils();

    public static ThreadPoolManagerUtils getInstance()
    {
        return mInstance;
    }

    private ThreadPoolExecutor executor;
    private ThreadPoolManagerUtils()

    {
        executor=new ThreadPoolExecutor(0, Integer.MAX_VALUE,60L,TimeUnit.SECONDS,
        new SynchronousQueue<Runnable>()


);
    }
    /**
     *执行任务
     */
    public void execute(Runnable runnable) {
        if (runnable == null) return;

        executor.execute(runnable);
    }
    /**
     *从线程池中移除任务
     */
    public void remove(Runnable runnable) {
        if (runnable == null) return;

        executor.remove(runnable);
    }


    }

