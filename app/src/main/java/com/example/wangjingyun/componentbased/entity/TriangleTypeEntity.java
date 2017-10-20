package com.example.wangjingyun.componentbased.entity;

/**
 * Created by Administrator on 2017/10/20.
 */

public class TriangleTypeEntity {

    //名字
    private String name;
    //时间
    private String time;

    //小箭头朝向 0 朝上 1朝下
    private int direction;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
