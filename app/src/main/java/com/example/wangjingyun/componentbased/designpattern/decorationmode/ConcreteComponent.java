package com.example.wangjingyun.componentbased.designpattern.decorationmode;

/**
 * 装饰 加糖
 */
public class ConcreteComponent implements Component{

    private Component component;

    public ConcreteComponent(Component component){

        this.component=component;
    }
    @Override
    public void operation() {
        component.operation();
        System.out.println("加糖");
    }
}
