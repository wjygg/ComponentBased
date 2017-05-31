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
 * Created by wangjngyun on 2017/5/31.
 */

public class SlidingDiscolorationTextView extends View{

    private int SlidingChangeColor= Color.BLUE;

    private int SlidingColor=Color.BLACK;

    private String SlidingText;

    private int SlidingTextSize=15;

    private Paint slidingPaint,slidingChangePaint;

    private float currentThis;

    public SlidingDiscolorationTextView(Context context) {
        this(context,null);
    }

    public SlidingDiscolorationTextView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SlidingDiscolorationTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlidingDiscolorationTextView);
        SlidingChangeColor=typedArray.getColor(R.styleable.SlidingDiscolorationTextView_SlidingChangeColor,SlidingChangeColor);
        SlidingColor = typedArray.getColor(R.styleable.SlidingDiscolorationTextView_SlidingColor, SlidingColor);
        SlidingText=typedArray.getString(R.styleable.SlidingDiscolorationTextView_SlidingText);
        SlidingTextSize=typedArray.getDimensionPixelSize(R.styleable.SlidingDiscolorationTextView_SlidingTextSize,SlidingTextSize);
        typedArray.recycle();

        initSlidingPaint();

        initSlidingChangePaint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMeasureSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMeasureSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        int widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        if(widthMeasureSpecMode==MeasureSpec.AT_MOST){

            widthMeasureSpecSize= (int) (slidingPaint.measureText(SlidingText)+getPaddingLeft()+getPaddingRight());

        }

        if(heightMeasureSpecMode==MeasureSpec.AT_MOST){

            Rect bounds=new Rect();

            slidingPaint.getTextBounds(SlidingText,0,SlidingText.length(),bounds);

            heightMeasureSpecSize=bounds.height()+getPaddingBottom()+getPaddingTop();

        }

        setMeasuredDimension(widthMeasureSpecSize,heightMeasureSpecSize);
    }


    private void initSlidingPaint() {

        slidingPaint=new Paint();
        slidingPaint.setAntiAlias(true);
        slidingPaint.setColor(SlidingColor);
        slidingPaint.setTextSize(SlidingTextSize);
        slidingPaint.setStyle(Paint.Style.FILL);
    }

    private void initSlidingChangePaint(){

        slidingChangePaint=new Paint();

        slidingChangePaint.setStyle(Paint.Style.FILL);

        slidingChangePaint.setAntiAlias(true);

        slidingChangePaint.setTextSize(SlidingTextSize);

        slidingChangePaint.setColor(SlidingChangeColor);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制不变色文字

        canvas.save();

        canvas.clipRect(0,0,100,getHeight());

        int x= getWidth()/2-(int) slidingPaint.measureText(SlidingText)/2;

        Paint.FontMetricsInt fontMetricsInt = slidingPaint.getFontMetricsInt();

        int dy=(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;

        int baseLine=getHeight()/2+dy;

        canvas.drawText(SlidingText,x,baseLine,slidingPaint);

        canvas.restore();

        canvas.clipRect(100,0,getWidth(),getHeight());

        canvas.drawText(SlidingText,x,baseLine,slidingChangePaint);

    }
}