package com.example.wangjingyun.componentbased.widget.pixeladaptation;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class SceenLinearLayout extends LinearLayout{

    private boolean flag;

    public SceenLinearLayout(Context context) {
        super(context);
    }

    public SceenLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SceenLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //计算宽高之前获取缩放比例的值
        if (!flag) { //防止比例的计算多次调用
            float scaleX = PixelaDaptationUtils.getInstance(getContext()).getWidthCoefficient();
            float scaleY = PixelaDaptationUtils.getInstance(getContext()).getHeightCoefficient();
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                LayoutParams params = (LayoutParams) child.getLayoutParams();
                params.width = (int) (params.width * scaleX);
                params.height = (int) (params.height * scaleY);
                params.leftMargin = (int) (params.leftMargin * scaleX);
                params.rightMargin = (int) (params.rightMargin * scaleX);
                params.topMargin = (int) (params.leftMargin * scaleY);
                params.bottomMargin = (int) (params.leftMargin * scaleY);
            }
            flag = true;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
