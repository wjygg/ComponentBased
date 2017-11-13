package com.example.wangjingyun.componentbased.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseActivity;
import com.example.wangjingyun.componentbased.widget.DropDownMenuView;
import com.example.wangjingyun.componentbasesdk.ioc.ViewById;

/**
 * Created by Administrator on 2017/11/3.
 */

public class DropDownActivity extends BaseActivity {


    @ViewById(R.id.dropdownmenuview)
    DropDownMenuView dropDownMenuView;

    private String[] datas=new String[]{"1","2","3","4"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_dropdown_layout;
    }

    @Override
    public void initDatas() {

        dropDownMenuView.setAdapter(new DropDownMenuView.DropDownMenuAdapter() {

            @Override
            public int getCount() {
                return datas.length;
            }

            @Override
            public View getTabView(Context context, int position, ViewGroup parent) {
                TextView textView=new TextView(context);
                textView.setText(datas[position]);
                textView.setTextSize(25);
                return textView;
            }

            @Override
            public View getContentView(Context context, int position, ViewGroup parent) {
                TextView textView=new TextView(context);
                textView.setText(datas[position]);
                return textView;
            }


        });

    }
}
