package com.example.wangjingyun.componentbased.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.wangjingyun.componentbased.R;

/**
 * Created by wangjngyun on 2017/5/31.
 */

public class SlidingDiscolorationTextView extends View{

    private int SlidingChangeColor= Color.BLUE;

    private int SlidingColor=Color.BLACK;

    private String SlidingText;

    private int SlidingTextSize=15;

    private Paint slidingPaint;

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

        initPaint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
        
        
        
    }
    
    private int   getMeasureSpecLength(int measureSpec){

        int size=0;

        int measureSpecMode = MeasureSpec.getMode(measureSpec);
        int measureSpecSize = MeasureSpec.getSize(measureSpec);

        if(measureSpecMode==MeasureSpec.EXACTLY){

            size=measureSpecSize;

            
        }else{



        }

        return size;
        
    }

    private void initPaint() {

        slidingPaint=new Paint();
        slidingPaint.setAntiAlias(true);
        slidingPaint.setColor(SlidingColor);
        slidingPaint.setTextSize(SlidingTextSize);
        slidingPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制不变色文字

        int x= getWidth()/2-(int) slidingPaint.measureText(SlidingText)/2;

        Paint.FontMetricsInt fontMetricsInt = slidingPaint.getFontMetricsInt();

        int dy=(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;

        int baseLine=getWidth()/2+dy;

        canvas.drawText(SlidingText,x,baseLine,slidingPaint);

    }
}
