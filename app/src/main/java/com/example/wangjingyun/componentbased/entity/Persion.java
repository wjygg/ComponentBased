package com.example.wangjingyun.componentbased.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/1/23.
 */

public class Persion implements Parcelable {

    private String name;

    private String age;

    private boolean flag;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.age);
        dest.writeByte(this.flag ? (byte) 1 : (byte) 0);
    }

    public Persion() {
    }

    public Persion(String name, String age, boolean flag) {
        this.name = name;
        this.age = age;
        this.flag = flag;
    }

    protected Persion(Parcel in) {
        this.name = in.readString();
        this.age = in.readString();
        this.flag = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Persion> CREATOR = new Parcelable.Creator<Persion>() {
        @Override
        public Persion createFromParcel(Parcel source) {
            return new Persion(source);
        }

        @Override
        public Persion[] newArray(int size) {
            return new Persion[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
