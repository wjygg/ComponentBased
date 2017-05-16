package com.example.wangjingyun.componentbasesdk.fixbug;

import android.content.Context;

import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;

/**
 * Created by Administrator on 2017/5/16.
 */

public class FixDexManager {

    private Context mcontext;

    public FixDexManager(Context context){

        this.mcontext=context;

    }


    public void fixDex(String absolutePath) throws Exception {


        // 获取 应用的 classLoad

        ClassLoader applicationClassLoader = mcontext.getClassLoader();

        //获取 类中的  dexElement

        Object dexElements=getDexElementsByClassLoad(applicationClassLoader);

        //获取 网上下载 dex


    }


    private Object getDexElementsByClassLoad(ClassLoader applicationClassLoader) throws Exception {

        // 获取 DexPathList

        Field pathListFiled = BaseDexClassLoader.class.getDeclaredField("pathList");

        //获取 public private
        pathListFiled.setAccessible(true);

        //获取 pathlist 的 属性值
        Object pathList = pathListFiled.get(applicationClassLoader);

        Field declaredField = pathList.getClass().getDeclaredField("dexElements");

        declaredField.setAccessible(true);

        return declaredField.get(pathList);
    }


}
