package com.example.wangjingyun.componentbased.service.compressbitmap;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/1/11.
 */

public class BitmapParams implements Parcelable {


    private int outWidth;
    private int outHeight;
    private int maxFileSize;
    private String srcImageUri;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.outWidth);
        dest.writeInt(this.outHeight);
        dest.writeInt(this.maxFileSize);
        dest.writeString(this.srcImageUri);
    }

    public BitmapParams() {
    }

    protected BitmapParams(Parcel in) {
        this.outWidth = in.readInt();
        this.outHeight = in.readInt();
        this.maxFileSize = in.readInt();
        this.srcImageUri = in.readString();
    }

    public static final Parcelable.Creator<BitmapParams> CREATOR = new Parcelable.Creator<BitmapParams>() {
        @Override
        public BitmapParams createFromParcel(Parcel source) {
            return new BitmapParams(source);
        }

        @Override
        public BitmapParams[] newArray(int size) {
            return new BitmapParams[size];
        }
    };

    public int getOutWidth() {
        return outWidth;
    }

    public void setOutWidth(int outWidth) {
        this.outWidth = outWidth;
    }

    public int getOutHeight() {
        return outHeight;
    }

    public void setOutHeight(int outHeight) {
        this.outHeight = outHeight;
    }

    public int getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(int maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public String getSrcImageUri() {
        return srcImageUri;
    }

    public void setSrcImageUri(String srcImageUri) {
        this.srcImageUri = srcImageUri;
    }
}
