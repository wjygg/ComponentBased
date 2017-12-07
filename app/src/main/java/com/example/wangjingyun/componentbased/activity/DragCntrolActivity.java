package com.example.wangjingyun.componentbased.activity;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseActivity;
import com.example.wangjingyun.componentbased.dialog.BaseDialog;
import com.example.wangjingyun.componentbased.dialog.titlebar.BaseTitleBar;
import com.example.wangjingyun.componentbased.entity.TriangleTypeEntity;
import com.example.wangjingyun.componentbased.network.HttpCallBackEntity;
import com.example.wangjingyun.componentbased.utils.StatusBarUtils;
import com.example.wangjingyun.componentbased.widget.BouquetPraiseView;
import com.example.wangjingyun.componentbased.widget.DragControlView;
import com.example.wangjingyun.componentbasesdk.http.HttpUtils;
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

    @ViewById(R.id.dialog)
    TextView dialog;


    @Override
    public void setStatusBar() {
        super.setStatusBar();

        StatusBarUtils.setStatusBarUtils(DragCntrolActivity.this, ContextCompat.getColor(DragCntrolActivity.this,R.color.pink_text_color));
    }

    @Override
    public void initTitle() {
        BaseTitleBar baseTitleBar=new BaseTitleBar.Builder(DragCntrolActivity.this)
                .setAlignText(R.id.tv_align,"测试").setAlignTextColor(R.id.tv_align,R.color.white).setBackGround(R.color.pink_text_color)
                .builder();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dragcontrol_layout;
    }

    @Override
    public void initDatas() {

        HttpUtils.with(DragCntrolActivity.this).url("").execute(new HttpCallBackEntity<TriangleTypeEntity>() {
            @Override
            public void onSuccess(TriangleTypeEntity triangleTypeEntity) {

            }

            @Override
            public void onError(Exception e) {

            }
        });

        dragcontrolview.setOnTouchListener(new DragControlView.DragViewListener(DragCntrolActivity.this));
    }

    @OnClick(R.id.textview)
    public void setTextview(){

        bouquetpraiseview.addView();
    }

    @OnClick(R.id.dialog)
    public void clickDialog(){
        BaseDialog baseDialog = new BaseDialog.Builder(DragCntrolActivity.this, R.style.DialogTheme).setView(R.layout.dialog_dragcntrol_layout)
               .setOnClickListener(R.id.textview, new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Toast.makeText(DragCntrolActivity.this,((TextView)v).getText().toString(),Toast.LENGTH_SHORT).show();
                   }
               }).setText(R.id.textview,"测试").setGravity(Gravity.TOP).builder();

        baseDialog.show();

    }

}
