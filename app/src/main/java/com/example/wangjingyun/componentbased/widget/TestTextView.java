package com.example.wangjingyun.componentbased.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.wangjingyun.componentbased.R;

/**
 * Created by Administrator on 2017/5/24.
 */

public class TestTextView extends View {

    private int testTextTextColor= Color.BLACK;

    private int testTextTextSize=15;

    private String testTextText;

    private Paint paint;
    public TestTextView(Context context) {
        this(context,null);
    }

    public TestTextView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TestTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.TestTextView);

        testTextText=typedArray.getString(R.styleable.TestTextView_TestTextViewText);

        testTextTextColor=typedArray.getColor(R.styleable.TestTextView_TestTextViewTextColor,testTextTextColor);

        testTextTextSize=typedArray.getDimensionPixelSize(R.styleable.TestTextView_TestTextViewTextSize,testTextTextSize);

        typedArray.recycle();

        initPaint();

    }

    private void initPaint() {

        paint=new Paint();
        //抗锯齿
        paint.setAntiAlias(true);
        //实心画笔
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(testTextTextColor);
        paint.setTextSize(testTextTextSize);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //确定的值
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        //宽度包裹的时候
        if(widthMode==MeasureSpec.AT_MOST){

            Rect bounds=new Rect();
            //获取字符串的宽度
            paint.getTextBounds(testTextText,0,testTextText.length(),bounds);
            //字符串的长度赋值给宽度
            widthSize= (int) (paint.measureText(testTextText)+getPaddingLeft()+getPaddingRight());
        }

        //高度包裹的时候
        if(heightMode==MeasureSpec.AT_MOST){

            Rect bounds=new Rect();
            //获取字符串的高度
            paint.getTextBounds(testTextText,0,testTextText.length(),bounds);
            //字符串的长度赋值给宽度
            heightSize=bounds.height()+getPaddingBottom()+getPaddingTop();
        }

        setMeasuredDimension(widthSize,heightSize);

    }


    @Override
    protected void onDraw(Canvas canvas) {

        //获取绘制字体的 baseline

        Paint.FontMetricsInt fontMetricsInt= paint.getFontMetricsInt();
        //(fontMetricsInt.bottom-fontMetricsInt.top)/2 字体高度的一半
        int dy=(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
        //getHeight()/2 控件的一半
        int baseline=getHeight()/2+dy;



        Rect bounds=new Rect();
        //获取字符串的宽度
        paint.getTextBounds(testTextText,0,testTextText.length(),bounds);
        //x偏移量
        int x= (int) (getWidth()/2-paint.measureText(testTextText)/2);

        canvas.drawText(testTextText,x,baseline,paint);
    }
}
