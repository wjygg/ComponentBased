package com.example.wangjingyun.componentbased.designpattern.staticproxy;

public abstract class Subject {

    //做一些事
    public abstract void doAction();

    public void onSubject(){
        System.out.println("subject");
    };
}
