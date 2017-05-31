package com.example.wangjingyun.componentbased.activity.fragment;

import android.os.Handler;
import android.os.Message;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseFragment;
import com.example.wangjingyun.componentbased.widget.QQView;

import butterknife.InjectView;

/**
 * Created by Administrator on 2017/3/11.
 */

public class HomeFragment extends BaseFragment {

     @InjectView(R.id.QQView)
     QQView qqView;

    private Handler handler=new Handler(){


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            qqView.setCurrent(msg.arg1,100);

        }
    };
    public static HomeFragment getInstance(){

        HomeFragment fragment=new HomeFragment();

        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_layout;
    }

    @Override
    public void initDatas() {

        new Thread(){

            @Override
            public void run() {
                super.run();

                for(int i=1;i<=100;i++){

                    Message msg=new Message();
                    msg.arg1=i;

                    handler.sendMessage(msg);
                    try {
                        sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();

    }
}
