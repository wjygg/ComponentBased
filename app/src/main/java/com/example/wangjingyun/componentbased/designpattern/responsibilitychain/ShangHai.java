package com.example.wangjingyun.componentbased.designpattern.responsibilitychain;

public class ShangHai extends PostMan{

    @Override
    public void handleCourier(String address) {

        if("shanghai".equals(address)){
            System.out.println("上海接受快递");
            return;
        }else{
            //下一级继续调用
            postMan.handleCourier(address);
        }
    }
}
