package com.example.wangjingyun.componentbasesdk.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/12/12.
 */

public class DaoSupport<T> implements IDaoSupport<T>{


    private SQLiteDatabase mSqLiteDatabase;

    private  Class<T> mClazz;

    private static final Object[] mPutMethodArgs = new Object[2];

    /**
     * 插入数据
     * @param obj
     */
    @Override
    public Long insert(T obj) {

        ContentValues values = contentValuesByObj(obj);
        return mSqLiteDatabase.insert(DaoUtil.getTableName(mClazz), null, values);
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


    public ContentValues contentValuesByObj(T obj){

        ContentValues values=new ContentValues();
        values.put("key",13);

        //反射获取实体类属性
        Field[] declaredFields = mClazz.getDeclaredFields();

        for (Field declaredField:declaredFields){
            try {
                //设置私有属性
                declaredField.setAccessible(true);
                //获取属性name
                String fieldName=declaredField.getName();
                // 获取属性值
                Object fileValue=declaredField.get(obj);
                //值是null
                if(fileValue==null){
                    continue;
                }

                mPutMethodArgs[0]=fieldName;
                mPutMethodArgs[1]=fileValue;

                //获取ContentValues 的put方法
                Method put = ContentValues.class.getDeclaredMethod("put", String.class, values.getClass());

                put.invoke(values,mPutMethodArgs);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                mPutMethodArgs[0] = null;
                mPutMethodArgs[1] = null;
            }
        }

        return  values;
    }





}
