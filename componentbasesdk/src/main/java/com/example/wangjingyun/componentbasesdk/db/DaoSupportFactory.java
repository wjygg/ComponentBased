package com.example.wangjingyun.componentbasesdk.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2017/12/12.
 */

public class DaoSupportFactory {

    private static DaoSupportFactory mFactory;

    private SQLiteDatabase mSqLiteDatabase;

    private DaoSupportFactory(){

        // 把数据库放到内存卡里面  判断是否有存储卡 6.0要动态申请权限
        File dbRoot = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator + "nhdz" + File.separator + "database");

        if (!dbRoot.exists()) {
            dbRoot.mkdirs();
        }
        File dbFile = new File(dbRoot, "outcheck.db");

        // 打开或者创建一个数据库
        mSqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dbFile, null);

    }


    public static DaoSupportFactory getInstance(){

        if(mFactory==null){

            synchronized (DaoSupportFactory.class){

                if(mFactory==null){

                    mFactory=new DaoSupportFactory();
                }
            }
        }

        return mFactory;

    }


    public <T> IDaoSupport<T> getDao(Class<T> tClass){

        IDaoSupport<T> iDaoSupport=new DaoSupport<T>();

        iDaoSupport.init(mSqLiteDatabase,tClass);
        return iDaoSupport;
    }




}
