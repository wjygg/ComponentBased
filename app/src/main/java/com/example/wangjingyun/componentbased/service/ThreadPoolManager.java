package com.example.wangjingyun.componentbased.service;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Administrator on 2018/1/11.
 */

public class ThreadPoolManager {

    private static ThreadPoolManager threadPoolManager;

    private   ExecutorService executorService;

    private ThreadPoolManager(){

       executorService = Executors.newCachedThreadPool();
    }

    public static ThreadPoolManager getInstance(){

        if(threadPoolManager==null){

            synchronized (ThreadPoolManager.class){

                if(threadPoolManager==null){

                    threadPoolManager=new ThreadPoolManager();

                }
            }
        }

        return threadPoolManager;
    }

    public void execute(Runnable run){

        if(run==null)return;

        executorService.execute(run);
    }

    public void shutDownNow(){
        if(executorService==null) return;
        executorService.shutdownNow();
    }


}
