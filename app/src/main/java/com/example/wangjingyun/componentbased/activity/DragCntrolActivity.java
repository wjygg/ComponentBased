package com.example.wangjingyun.componentbased.activity;

import android.widget.TextView;
import android.widget.Toast;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseActivity;
import com.example.wangjingyun.componentbased.utils.StatusBarUtils;
import com.example.wangjingyun.componentbased.widget.BouquetPraiseView;
import com.example.wangjingyun.componentbased.widget.DragControlView;
import com.example.wangjingyun.componentbasesdk.ioc.OnClick;
import com.example.wangjingyun.componentbasesdk.ioc.ViewById;

/**
 *
 * Created by Administrator on 2017/11/7.
 *
 */

public class DragCntrolActivity extends BaseActivity{

    @ViewById(R.id.bouquetpraiseview)
    BouquetPraiseView bouquetpraiseview;

    @ViewById(R.id.dragcontrolview)
    DragControlView dragcontrolview;

    @ViewById(R.id.textview)
    TextView textview;


    @Override
    public int getLayoutId() {
        return R.layout.activity_dragcontrol_layout;
    }

    @Override
    public void initDatas() {

        dragcontrolview.setOnTouchListener(new DragControlView.DragViewListener(DragCntrolActivity.this));
    }


    @OnClick(R.id.textview)
    public void setTextview(){

        bouquetpraiseview.addView();
    }

}
