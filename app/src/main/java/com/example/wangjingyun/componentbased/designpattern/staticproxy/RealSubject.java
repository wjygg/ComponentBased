package com.example.wangjingyun.componentbased.designpattern.staticproxy;


/**
 * 被代理的类
 */
public class RealSubject implements Subject{

    @Override
    public void doAction() {

        System.out.println("实际做的事情!");
    }
}
