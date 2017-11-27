package com.example.wangjingyun.componentbased.activity;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseActivity;
import com.example.wangjingyun.componentbased.utils.StatusBarUtils;
import com.example.wangjingyun.componentbasesdk.ioc.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Administrator on 2017/10/23.
 */

public class VDHActivity extends BaseActivity {

   @ViewById(R.id.listview)
    ListView listview;

    private List<String> datas=new ArrayList<>();

    @Override
    public void setStatusBar() {
        super.setStatusBar();
        //设置activity全屏
        StatusBarUtils.setStatusBarUtils(VDHActivity.this, Color.BLUE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_vdh_layout;
    }

    @Override
    public void initDatas() {

        for(int i=0;i<=20;i++){

            datas.add(""+i);
        }

        listview.setAdapter(new BaseAdapter() {


            @Override
            public int getCount() {
                return datas.size();
            }

            @Override
            public Object getItem(int position) {
                return datas.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
               View view=LayoutInflater.from(VDHActivity.this).inflate(R.layout.item_vdh_layout,null);
                TextView textview= (TextView) view.findViewById(R.id.textview);
                textview.setText(datas.get(position));
                textview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(VDHActivity.this,datas.get(position)+"",Toast.LENGTH_SHORT).show();
                    }
                });
                return view;
            }
        });
    }
}
