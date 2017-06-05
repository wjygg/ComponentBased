package com.example.wangjingyun.componentbased.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.wangjingyun.componentbased.R;


/**
 * 字母策划控件
 * Created by wangjingyun on 2017/6/5.
 *
 */

public class SlidingLettersView extends View {

    private int SlidingLettersTextSize;

    private int SlidingLettersTextInnerColor= Color.BLACK;

    private int SlidingLettersTextOutnerColor=Color.RED;

    private Paint slidingLettersTextInnerPaint;

    private Paint slidingLettersTextOutnerPaint;

    private String [] letters=new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    public SlidingLettersView(Context context) {
        this(context,null);
    }

    public SlidingLettersView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SlidingLettersView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typeArray=context.obtainStyledAttributes(attrs, R.styleable.SlidingLettersView);
        SlidingLettersTextSize=typeArray.getDimensionPixelSize(R.styleable.SlidingLettersView_SlidingLettersTextSize,SlidingLettersTextSize);
        SlidingLettersTextInnerColor=typeArray.getColor(R.styleable.SlidingLettersView_SlidingLettersTextInnerColor,SlidingLettersTextInnerColor);
        SlidingLettersTextOutnerColor=typeArray.getColor(R.styleable.SlidingLettersView_SlidingLettersTextOutnerColor,SlidingLettersTextOutnerColor);
        typeArray.recycle();
        initPaint();
    }

    private void initPaint() {

        slidingLettersTextInnerPaint=new Paint();
        slidingLettersTextInnerPaint.setTextSize(SlidingLettersTextSize);
        slidingLettersTextInnerPaint.setStyle(Paint.Style.FILL);
        slidingLettersTextInnerPaint.setAntiAlias(true);
        slidingLettersTextInnerPaint.setColor(SlidingLettersTextInnerColor);

        slidingLettersTextOutnerPaint=new Paint();
        slidingLettersTextOutnerPaint.setTextSize(SlidingLettersTextSize);
        slidingLettersTextOutnerPaint.setStyle(Paint.Style.FILL);
        slidingLettersTextOutnerPaint.setColor(SlidingLettersTextOutnerColor);
        slidingLettersTextOutnerPaint.setAntiAlias(true);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMeasureSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMeasureSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        int widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec);

        if(widthMeasureSpecMode==MeasureSpec.AT_MOST){

            widthMeasureSpecSize= (int) slidingLettersTextInnerPaint.measureText("A")+getPaddingLeft()+getPaddingRight();

        }
        setMeasuredDimension(widthMeasureSpecSize,heightMeasureSpecSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //一个的高度
        int averageValue=(getHeight()-getPaddingTop()-getPaddingBottom())/letters.length;


        for(int i=0;i<letters.length;i++){

            int x= (int) (getWidth()/2-slidingLettersTextInnerPaint.measureText(letters[i])/2);

            Paint.FontMetricsInt fontMetricsInt = slidingLettersTextInnerPaint.getFontMetricsInt();

            int dy=(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;

            int baseline=averageValue*i+averageValue/2+dy;

            canvas.drawText(letters[i],x,baseline,slidingLettersTextInnerPaint);
        }



    }
}
