package com.example.wangjingyun.componentbased.designpattern.responsibilitychain;

public class GuangZhou extends PostMan{
    @Override
    public void handleCourier(String address) {

        if("guangzhou".equals(address)){
            System.out.println("广州接受快递");
            return;
        }else{
            postMan.handleCourier(address);
        }
    }
}
