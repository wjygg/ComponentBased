package com.example.wangjingyun.componentbased.activity.fragment;

import android.os.Handler;
import android.os.Message;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseFragment;
import com.example.wangjingyun.componentbased.widget.SlidingDiscolorationTextView;

import butterknife.InjectView;

/**
 * Created by Administrator on 2017/3/11.
 */

public class MessageFragment extends BaseFragment {

    @InjectView(R.id.slidingDiscolorationTextView)
    SlidingDiscolorationTextView slidingDiscolorationTextView;

    private Handler handler=new Handler(){


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            slidingDiscolorationTextView.setCurrentThis((float)msg.obj);

        }
    };
    public static MessageFragment getInstance(){

        MessageFragment fragment=new MessageFragment();

        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message_layout;
    }

    @Override
    public void initDatas() {

        new Thread(){

            @Override
            public void run() {
                super.run();

                for(float i =  0.1f; i<=1f; i++){

                    Message msg=new Message();
                    msg.obj=i;
                    handler.sendMessage(msg);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();
    }

}
