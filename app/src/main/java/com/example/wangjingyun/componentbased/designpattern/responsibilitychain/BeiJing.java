package com.example.wangjingyun.componentbased.designpattern.responsibilitychain;

public class BeiJing extends PostMan{

    @Override
    public void handleCourier(String address) {
        if("beijing".equals(address)){
            System.out.println("北京接受快递");
           return;
        }else{
           //下一级继续调用
           postMan.handleCourier(address);
        }
    }
}
