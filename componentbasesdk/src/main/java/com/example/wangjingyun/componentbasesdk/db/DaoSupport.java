package com.example.wangjingyun.componentbasesdk.db;

import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/12/12.
 */

public class DaoSupport<T> implements IDaoSupport<T>{


    private SQLiteDatabase mSqLiteDatabase;

    private  Class<T> mClazz;


    /**
     * 插入数据
     * @param t
     */
    @Override
    public void insert(T t) {

    }

    //初始化数据库
    @Override
    public void init(SQLiteDatabase mSqLiteDatabase, Class<T> mClazz) {


        this.mSqLiteDatabase = mSqLiteDatabase;

        this.mClazz = mClazz;

        StringBuffer sb = new StringBuffer();

        sb.append("create table if not exists ")
                .append(DaoUtil.getTableName(mClazz))
                .append("(id integer primary key autoincrement, ");

        Field[] fields = mClazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);// 设置权限

            String name = field.getName();
            if (name==null||name.contains("$")) {
                continue;
            }
            String type = field.getType().getSimpleName();// int String boolean
            //  type需要进行转换 int --> integer, String text;
            sb.append(name).append(DaoUtil.getColumnType(type)).append(", ");
        }

        sb.replace(sb.length() - 2, sb.length(), ")");

        String createTableSql = sb.toString();
        // 创建表
        mSqLiteDatabase.execSQL(createTableSql);

    }



}
