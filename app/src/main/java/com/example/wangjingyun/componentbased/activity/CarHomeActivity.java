package com.example.wangjingyun.componentbased.activity;

import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseActivity;
import com.example.wangjingyun.componentbased.entity.TriangleTypeEntity;
import com.example.wangjingyun.componentbased.widget.LinnearScrowView;
import com.example.wangjingyun.componentbased.widget.TriangleTypeView;
import com.example.wangjingyun.componentbasesdk.ioc.ViewById;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/10/17.
 */

public class CarHomeActivity extends BaseActivity {


    @ViewById(R.id.horizontalscrollview)
    HorizontalScrollView horizontalscrollview;

    @ViewById(R.id.add_triangletype)
    LinearLayout add_triangletype;

    private List<TriangleTypeEntity> triangleTypeEntities=new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_carhome_layout;
    }

    @Override
    public void initDatas() {


        //一个对象
        TriangleTypeEntity triangleTypeEntity=new TriangleTypeEntity();
        triangleTypeEntity.setName("入店时间");
        triangleTypeEntity.setTime("14:00后");
        triangleTypeEntity.setDirection(0);

        TriangleTypeEntity triangleTypeEntity1=new TriangleTypeEntity();
        triangleTypeEntity1.setName("离店时间");
        triangleTypeEntity1.setTime("12:00前");
        triangleTypeEntity1.setDirection(1);

        TriangleTypeEntity triangleTypeEntity2=new TriangleTypeEntity();
        triangleTypeEntity2.setName("入店押金");
        triangleTypeEntity2.setTime("在线支付押金200元,芝麻信用600分,免押");
        triangleTypeEntity2.setDirection(0);

        TriangleTypeEntity triangleTypeEntity3=new TriangleTypeEntity();
        triangleTypeEntity3.setName("入住期间保洁");
        triangleTypeEntity3.setTime("短信通知");
        triangleTypeEntity3.setDirection(1);

        triangleTypeEntities.add(triangleTypeEntity);
        triangleTypeEntities.add(triangleTypeEntity1);
        triangleTypeEntities.add(triangleTypeEntity2);
        triangleTypeEntities.add(triangleTypeEntity3);
        for(int i=0;i<triangleTypeEntities.size();i++){

            TriangleTypeView triangleTypeView=new TriangleTypeView(CarHomeActivity.this);
            triangleTypeView.setDatas(triangleTypeEntities.get(i));

            //将控件添加进 LinnerLayout布局
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            triangleTypeView.setLayoutParams(params);
            add_triangletype.addView(triangleTypeView);
        }

    }
}
