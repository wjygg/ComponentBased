package com.example.wangjingyun.componentbased.designpattern.dynamicproxy;

import com.example.wangjingyun.componentbased.designpattern.decorationmode.Component;
import com.example.wangjingyun.componentbased.designpattern.decorationmode.ConcreteComponent;
import com.example.wangjingyun.componentbased.designpattern.decorationmode.Decorator;

import java.lang.reflect.Proxy;

public class Test {

    public static void main(String args[]) {

        IBank iBank = (IBank) Proxy.newProxyInstance(IBank.class.getClassLoader(), new Class<?>[]{IBank.class},
                new StuInvocationHandler<>(new Man()));

        iBank.onBank();
    }
}
