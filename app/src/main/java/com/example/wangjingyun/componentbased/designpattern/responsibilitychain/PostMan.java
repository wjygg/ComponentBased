package com.example.wangjingyun.componentbased.designpattern.responsibilitychain;

/**
 * 责任链设计模式
 */
public abstract class PostMan {
    public PostMan postMan;

    public abstract void handleCourier(String address);

}
