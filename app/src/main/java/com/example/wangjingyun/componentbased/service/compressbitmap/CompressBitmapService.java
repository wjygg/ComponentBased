package com.example.wangjingyun.componentbased.service.compressbitmap;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.support.annotation.Nullable;

import com.example.wangjingyun.componentbased.service.ThreadPoolManager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 压缩图片service
 * Created by Administrator on 2018/1/11.
 */

public class CompressBitmapService extends Service {

    public static final  String ACTION_COMPRESS_BROADCAST="CompressBitmap";
    public static final  String BITMAPPARAMS="BitmapParams";
    public static final  String ACTION_COMPRESS_FLAG="flag";
    public static final  int COMPRESS_BEGIN=1;
    public static final  int COMPRESS_END=2;
    private final Object object=new Object();
    private int maxNumber;
    private List<CompressResult> compressResults=new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        //发动广播 开始压缩
        Intent intent=new Intent(ACTION_COMPRESS_BROADCAST);
        intent.putExtra(ACTION_COMPRESS_FLAG,COMPRESS_BEGIN);
        sendBroadcast(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {

       final ArrayList<BitmapParams> paramArrayList = intent.getParcelableArrayListExtra(BITMAPPARAMS);

        maxNumber=paramArrayList.size();

        //如果paramArrayList过大,为了避免"The application may be doing too much work on its main thread"的问题,将任务的创建和执行统一放在后台线程中执行
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < paramArrayList.size(); ++i){
                    ThreadPoolManager.getInstance().execute(new CompressBitmapRunnable(paramArrayList.get(i),startId));
                }
            }
        }).start();

        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //发送广播停止压缩
        Intent intent=new Intent(ACTION_COMPRESS_BROADCAST);
        intent.putExtra(ACTION_COMPRESS_FLAG,COMPRESS_END);
        sendBroadcast(intent);
        compressResults.clear();
        ThreadPoolManager.getInstance().shutDownNow();
    }

    private class CompressBitmapRunnable implements Runnable {

        private BitmapParams bitmapParams;

        private int taskId;

        public CompressBitmapRunnable(BitmapParams bitmapParams, int taskId){

            this.bitmapParams=bitmapParams;
            this.taskId=taskId;

        }

        @Override
        public void run() {

            int outwidth = bitmapParams.getOutWidth();
            int outHieight = bitmapParams.getOutHeight();
            int maxFileSize = bitmapParams.getMaxFileSize();
            String srcImageUri = bitmapParams.getSrcImageUri();

            CompressResult compressResult = new CompressResult();
            String outPutPath = null;
            try {
                outPutPath = CompressBitmap.getInstance(CompressBitmapService.this).compressImage(
                        srcImageUri, outwidth, outHieight, maxFileSize);
            } catch (Exception e) {
            }
            compressResult.setSrcPath(srcImageUri);
            compressResult.setOutPath(outPutPath);
            if (outPutPath == null) {
                compressResult.setStatus(CompressResult.RESULT_ERROR);
            }
            synchronized (object){
                compressResults.add(compressResult);
                maxNumber--;
                if(maxNumber <= 0){
                    stopSelf(taskId);
                }
            }


        }


    }
}
