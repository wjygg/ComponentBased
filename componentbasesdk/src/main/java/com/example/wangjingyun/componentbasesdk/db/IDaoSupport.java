package com.example.wangjingyun.componentbasesdk.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by Administrator on 2017/12/12.
 */

public interface IDaoSupport<T> {

    Long insert(T t);

    void init(SQLiteDatabase mSqLiteDatabase ,Class<T> tClass);

    // 批量插入  检测性能
    public void insert(List<T> datas);

    // 获取专门查询的支持类
    QuerySupport<T> querySupport();

    int delete(String whereClause, String... whereArgs);

    int update(T obj, String whereClause, String... whereArgs);

    // 按照语句查询
    public List<T> query(String where, String[] value);

    //查询全部
    public List<T> query();

    public int upData(ContentValues values, String where, String[] value);


}
