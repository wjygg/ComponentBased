package com.example.wangjingyun.componentbased.activity.fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseFragment;
import com.example.wangjingyun.componentbased.network.RequestCenter;
import com.example.wangjingyun.componentbased.widget.CircularScaleDiagramView;
import com.example.wangjingyun.componentbased.widget.QQView;
import com.example.wangjingyun.componentbased.widget.TagView;
import com.example.wangjingyun.componentbasesdk.ioc.ViewById;
import com.example.wangjingyun.componentbasesdk.okhttp.listener.DisposeDataListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Administrator on 2017/3/11.
 */

public class HomeFragment extends BaseFragment{

     @InjectView(R.id.QQView)
     QQView qqView;

    @ViewById(R.id.circularScaleDiagramView)
    CircularScaleDiagramView circularScaleDiagramView;

    @ViewById(R.id.tagview)
    TagView tagview;

    List<String> strings=new ArrayList<>();

    private Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            qqView.setCurrent(msg.arg1,100);

            circularScaleDiagramView.setCurrent(msg.arg1);

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

        RequestCenter.Login(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {

                Log.d("homefragment",responseObj+"");
            }

            @Override
            public void onFailure(Object reasonObj) {

                Log.d("homefragment",reasonObj+"");
            }
        });

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

        strings.add("测试");
        strings.add("测试img");
        strings.add("WelCome");
        strings.add("工程师ing 进行中ing");
        strings.add("进行中ing");
        strings.add("WelCome");
        strings.add("工程师ing WelCome");
        strings.add("进行中ing");
        strings.add("测试");
        strings.add("测试img");




        tagview.setAdater(new TagView.BaseAdapter(){
            @Override
            public int getCount() {
                return strings.size();
            }

            @Override
            public View getView(int count,TagView parents) {
                TextView textview = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.tagview_textview_layout, parents, false);
                textview.setText(strings.get(count));
                return textview;
            }
        });

    }

}
