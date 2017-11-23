package com.example.wangjingyun.componentbased.activity;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseActivity;
import com.example.wangjingyun.componentbased.dialog.BaseDialog;
import com.example.wangjingyun.componentbased.dialog.titlebar.BaseTitleBar;
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

    @ViewById(R.id.dialog)
    TextView dialog;

    @Override
    public void initTitle() {
        BaseTitleBar baseTitleBar=new BaseTitleBar.Builder(DragCntrolActivity.this,(ViewGroup)findViewById(R.id.ll_ly))
                .setAlignText(R.id.tv_align,"测试").setAlignTextColor(R.id.tv_align,R.color.white).setBackGround(R.color.pink_text_color)
                .builder();

    }

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
