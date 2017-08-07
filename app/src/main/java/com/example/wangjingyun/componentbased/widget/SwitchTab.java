package com.example.wangjingyun.componentbased.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.wangjingyun.componentbased.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/7.
 */

public class SwitchTab extends LinearLayout {

    private List<String> tabName=new ArrayList<String>();

    private List<RadioButton> radioButtonId=new ArrayList<RadioButton>();
    private  SwitchTabClick listener;

    public SwitchTab(Context context) {
        this(context,null);
    }

    public SwitchTab(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwitchTab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    /**
     * 动态画 radiogroup 表单
     */
    private void initLayout(Context context) {

        tabName.add("入园");
        tabName.add("病假");
        tabName.add("事假");
        //创建viewgroup

        RadioGroup radioGroup=new RadioGroup(context);
        radioGroup.setGravity(Gravity.CENTER);
        radioGroup.setBackground(getResources().getDrawable(R.drawable.bg_stork_tabyellow));
        radioGroup.setPadding(1,1,1,1);
        radioGroup.setOrientation(RadioGroup.HORIZONTAL);
        LinearLayout.LayoutParams radioLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        radioGroup.setLayoutParams(radioLayoutParams);

        for(int i=0;i<tabName.size();i++){

            RadioGroup.LayoutParams layoutParams=new RadioGroup.LayoutParams(0,RadioGroup.LayoutParams.WRAP_CONTENT,1);

            final RadioButton radioButton=new RadioButton(context);
            radioButtonId.add(radioButton);
            radioButton.setPadding(px2dip(context,40),px2dip(context,40),px2dip(context,40),px2dip(context,40));
            radioButton.setText(tabName.get(i));
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setTextSize(px2sp(context,50));
            radioButton.setButtonDrawable(android.R.color.transparent);
            radioButton.setTextColor(getResources().getColorStateList(R.color.swithtab_textcolor));
            radioButton.setBackground(getResources().getDrawable(R.drawable.swithtab_drawable));

            radioButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    for(RadioButton rb:radioButtonId){

                        if(radioButton.getId()!=rb.getId()){

                            rb.setChecked(false);
                        }else{
                            rb.setChecked(true);
                        }
                    }
                    if(listener!=null){

                        listener.tabClick(radioButton.getText().toString());
                    }
                }
            });
            radioButton.setLayoutParams(layoutParams);
            if(i==0){

                radioButton.setChecked(true);
            }
            radioGroup.addView(radioButton);

        }

        addView(radioGroup);

    }


    public  int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue/scale + 0.5f);

    }

    public int px2sp(Context context, float pxValue) {
        final float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue/fontScale+0.5f);
    }

    public void setSwitchTabClickListener(SwitchTabClick listener){

        this.listener=listener;
    }

    public interface SwitchTabClick{

       void tabClick(String result);
    }


}
