package com.example.wangjingyun.componentbased.service.compressbitmap;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 图片 压缩结果类
 * Created by Administrator on 2018/1/12.
 */

public class CompressResult implements Parcelable {
    public static final int RESULT_OK = 0;
    public static final int RESULT_ERROR = 1;
    private int status = RESULT_OK;
    private String srcPath;
    private String outPath;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeString(this.srcPath);
        dest.writeString(this.outPath);
    }

    public CompressResult() {
    }

    protected CompressResult(Parcel in) {
        this.status = in.readInt();
        this.srcPath = in.readString();
        this.outPath = in.readString();
    }

    public static final Parcelable.Creator<CompressResult> CREATOR = new Parcelable.Creator<CompressResult>() {
        @Override
        public CompressResult createFromParcel(Parcel source) {
            return new CompressResult(source);
        }

        @Override
        public CompressResult[] newArray(int size) {
            return new CompressResult[size];
        }
    };

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }
}
