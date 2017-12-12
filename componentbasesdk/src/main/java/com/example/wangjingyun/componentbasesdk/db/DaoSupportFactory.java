package com.example.wangjingyun.componentbasesdk.db;

/**
 * Created by Administrator on 2017/12/12.
 */

public class DaoSupportFactory {

    private static DaoSupportFactory mFactory;

    private DaoSupportFactory(){

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
}
