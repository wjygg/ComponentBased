package com.example.wangjingyun.componentbased.designpattern.staticproxy;

import com.example.wangjingyun.componentbased.designpattern.decorationmode.Component;
import com.example.wangjingyun.componentbased.designpattern.decorationmode.ConcreteComponent;
import com.example.wangjingyun.componentbased.designpattern.decorationmode.Decorator;

public class Test {

    public static void main(String args[]) {

        Subject subject=new Proxy();

        subject.doAction();
    }

}
