package com.example.wangjingyun.componentbased.designpattern.responsibilitychain;

import com.example.wangjingyun.componentbased.designpattern.dynamicproxy.IBank;
import com.example.wangjingyun.componentbased.designpattern.dynamicproxy.Man;
import com.example.wangjingyun.componentbased.designpattern.dynamicproxy.StuInvocationHandler;

import java.lang.reflect.Proxy;

public class Test {

    public static void main(String args[]) {

        PostMan beijing=new BeiJing();
        PostMan shanghai=new ShangHai();
        PostMan guangzhou=new GuangZhou();

        beijing.postMan=shanghai;
        shanghai.postMan=guangzhou;

        beijing.handleCourier("guangzhou");
    }
}
