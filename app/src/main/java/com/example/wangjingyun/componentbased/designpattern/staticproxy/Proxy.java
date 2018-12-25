package com.example.wangjingyun.componentbased.designpattern.staticproxy;

/**
 * 代理类扩展
 */
public class Proxy implements Subject{

    private Subject realSubject;

    public Proxy(){
        realSubject=new RealSubject();
    }

    @Override
    public void doAction() {

        System.out.println("做一些代理的事情!");

        realSubject.doAction();
    }


}
