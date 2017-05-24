package com.example.wangjingyun.componentbased.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import com.example.wangjingyun.componentbased.R;

/**
 * 自定义qq计步器
 * Created by wangjingyun on 2017/5/21.
 */

public class QQView extends View {

    private int QQViewInnerColor=Color.RED;

    private int QQViewOutnerColor=Color.BLUE;

    private int QQViewBorderWidth=20;

    private int QQViewTextColor=Color.RED;

    private int QQViewTextSize=20;

    public QQView(Context context) {
        this(context,null);
    }

    public QQView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public QQView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.QQView);
        QQViewInnerColor=typedArray.getColor(R.styleable.QQView_QQViewInnerColor,QQViewInnerColor);
        QQViewOutnerColor=typedArray.getColor(R.styleable.QQView_QQViewOutnerColor,QQViewOutnerColor);
        QQViewBorderWidth= (int) typedArray.getDimension(R.styleable.QQView_QQViewBorderWidth,QQViewBorderWidth);
        QQViewTextColor=typedArray.getColor(R.styleable.QQView_QQViewTextColor,QQViewTextColor);
        QQViewTextSize=typedArray.getDimensionPixelSize(R.styleable.QQView_QQViewTextSize,QQViewTextSize);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int mWidth=MeasureSpec.getSize(widthMeasureSpec);

        int mHeight=MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(mWidth>mHeight?mHeight:mWidth,mWidth>mHeight?mHeight:mWidth);
    }


}
