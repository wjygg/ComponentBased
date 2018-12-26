package com.example.wangjingyun.componentbased.designpattern.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理类
 * @param <T>
 */
public class StuInvocationHandler<T>  implements InvocationHandler{

    private T mClzz;

    public StuInvocationHandler(T mClzz){

        this.mClzz=mClzz;
    }


    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

        Object result = method.invoke(mClzz, objects);

        System.out.println("代理完毕!");
        return result;
    }
}
