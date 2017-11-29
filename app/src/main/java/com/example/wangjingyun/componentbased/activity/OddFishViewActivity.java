package com.example.wangjingyun.componentbased.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangjingyun.commonrecycleviewsdk.adapter.CommonRecycleViewAdapter;
import com.example.wangjingyun.commonrecycleviewsdk.viewholder.CommonViewHolder;
import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseActivity;
import com.example.wangjingyun.componentbased.widget.TestRecycleView;
import com.example.wangjingyun.componentbasesdk.ioc.OnClick;
import com.example.wangjingyun.componentbasesdk.ioc.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * qiyu
 * Created by Administrator on 2017/11/27.
 */

public class OddFishViewActivity extends BaseActivity{

    @ViewById(R.id.recycleview)
    TestRecycleView recyclerView;

    private List<String> datas=new ArrayList<>();

    @ViewById(R.id.tv_text)
    TextView tv_text;
    @Override
    public int getLayoutId() {
        return R.layout.activity_oddfish_layout;
    }

    @Override
    public void initDatas() {

        for(int i=0;i<=20;i++){

            datas.add("sssssssss");
        }


        recyclerView.setLayoutManager(new LinearLayoutManager(OddFishViewActivity.this,LinearLayoutManager.VERTICAL,false));

        recyclerView.setAdapter(new CommonRecycleViewAdapter<String>(OddFishViewActivity.this, datas, R.layout.item_vdh_layout) {
            @Override
            public void convert(CommonViewHolder commonViewHolder, int i, String s) {


                commonViewHolder.setText(R.id.textview,s);

            }
        });

    }

    @OnClick(R.id.tv_text)
    public void getClick(){

        Toast.makeText(this,"dd",Toast.LENGTH_LONG).show();
    }

}
