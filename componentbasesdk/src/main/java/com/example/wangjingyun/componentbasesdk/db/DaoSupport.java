package com.example.wangjingyun.componentbasesdk.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/12.
 */

public class DaoSupport<T> implements IDaoSupport<T>{


    private SQLiteDatabase mSqLiteDatabase;

    private  Class<T> mClazz;

    private static final Object[] mPutMethodArgs = new Object[2];

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

    /**
     * 插入数据
     * @param obj
     */
    @Override
    public Long insert(T obj) {

        ContentValues values = contentValuesByObj(obj);
        return mSqLiteDatabase.insert(DaoUtil.getTableName(mClazz), null, values);
    }

    @Override
    public void insert(List<T> datas) {
        // 批量插入采用 事物
        mSqLiteDatabase.beginTransaction();
        for (T data : datas) {
            // 调用单条插入
            insert(data);
        }
        mSqLiteDatabase.setTransactionSuccessful();
        mSqLiteDatabase.endTransaction();

    }

    private QuerySupport<T> mQuerySupport;

    @Override
    public QuerySupport<T> querySupport() {

        if(mQuerySupport == null){
            mQuerySupport = new QuerySupport<>(mSqLiteDatabase,mClazz);
        }
        return mQuerySupport;
    }


   // 查询目前直接查询所有
    @Override
    public List<T> query() {
        Cursor cursor = mSqLiteDatabase.query(DaoUtil.getTableName(mClazz),
               null, null, null, null, null, null);
       return cursorToList(cursor);
    }

    //单个查询
    @Override
    public List<T> query(String key, String[] value) {
        Cursor cursor = mSqLiteDatabase.query(DaoUtil.getTableName(mClazz),
                null, key+"=?", value, null, null, null);
        return cursorToList(cursor);
    }

    @Override
    public int delete(String whereClause, String... whereArgs) {
        return mSqLiteDatabase.delete(DaoUtil.getTableName(mClazz), whereClause, whereArgs);
    }

    @Override
    public int update(T obj, String whereClause, String... whereArgs) {

        ContentValues values = contentValuesByObj(obj);
        return mSqLiteDatabase.update(DaoUtil.getTableName(mClazz),
                values, whereClause, whereArgs);
    }


    @Override
    public int upData(ContentValues values, String where, String[] value) {

        int count = mSqLiteDatabase.update(DaoUtil.getTableName(mClazz), values, where+"=?",value);
        return count;
    }


    private List<T> cursorToList(Cursor cursor) {
        List<T> list = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            // 不断的从游标里面获取数据
            do {
                try {
                    // 通过反射new对象
                    T instance = mClazz.newInstance();
                    Field[] fields = mClazz.getDeclaredFields();


                    for (Field field : fields) {
                        // 遍历属性
                        field.setAccessible(true);
                        String name = field.getName();
                        // 获取角标  获取在第几列
                        int index = cursor.getColumnIndex(name);
                        if (index == -1) {
                            continue;
                        }

                        // 通过反射获取 游标的方法  field.getType() -> 获取的类型
                        Method cursorMethod = cursorMethod(field.getType());
                        if (cursorMethod != null) {
                            // 通过反射获取了 value
                            Object value = cursorMethod.invoke(cursor, index);
                            if (value == null) {
                                continue;
                            }

                            // 处理一些特殊的部分
                            if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                                if ("0".equals(String.valueOf(value))) {
                                    value = false;
                                } else if ("1".equals(String.valueOf(value))) {
                                    value = true;
                                }
                            } else if (field.getType() == char.class || field.getType() == Character.class) {
                                value = ((String) value).charAt(0);
                            } else if (field.getType() == Date.class) {
                                long date = (Long) value;
                                if (date <= 0) {
                                    value = null;
                                } else {
                                    value = new Date(date);
                                }
                            }

                            // 通过反射注入
                            field.set(instance, value);
                        }
                    }
                    // 加入集合
                    list.add(instance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // 获取游标的方法
    private Method cursorMethod(Class<?> type) throws Exception {
        String methodName = getColumnMethodName(type);
        // type String getString(index); int getInt; boolean getBoolean
        Method method = Cursor.class.getMethod(methodName, int.class);
        return method;
    }

    private String getColumnMethodName(Class<?> fieldType) {
        String typeName;
        if (fieldType.isPrimitive()) {
            typeName = DaoUtil.capitalize(fieldType.getName());
        } else {
            typeName = fieldType.getSimpleName();
        }
        String methodName = "get" + typeName;
        if ("getBoolean".equals(methodName)) {
            methodName = "getInt";
        } else if ("getChar".equals(methodName) || "getCharacter".equals(methodName)) {
            methodName = "getString";
        } else if ("getDate".equals(methodName)) {
            methodName = "getLong";
        } else if ("getInteger".equals(methodName)) {
            methodName = "getInt";
        }
        return methodName;
    }


    public ContentValues contentValuesByObj(T obj){

        ContentValues values=new ContentValues();
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
