package com.example.wangjingyun.componentbasesdk.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2017/12/12.
 */

public interface IDaoSupport<T> {

    void insert(T t);

    void init(SQLiteDatabase mSqLiteDatabase ,Class<T> tClass);
}
