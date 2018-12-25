package com.example.wangjingyun.componentbased.designpattern.decorationmode;

public class Test {
    public static void main(String args[]) {

        Component component=new ConcreteComponent(new Decorator());
        component.operation();
    }
}
