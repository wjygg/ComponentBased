package com.example.wangjingyun.componentbased.utils;

/**
 * 生成差分包 工具类
 * Created by uggyy on 2018/7/16.
 */

public class PatchUtils {

    /**
     * @param oldApkPath 旧版本apk路径
     * @param newApkPath  新版本apk路径
     * @param patchPath 差分包路径
     */
    public static native void combine(String oldApkPath,String newApkPath,String patchPath);

}
