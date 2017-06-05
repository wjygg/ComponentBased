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

    private int SlidingTextSize=50;

    private Paint slidingPaint,slidingChangePaint;

    //当前进度 0-1；
    private float currentThis=0;

    private TRUN trun=TRUN.LEFT_TORIGHT;

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
        //当前实际进度
        float currentWidth=currentThis*getWidth();

        canvas.save();

        //左边往右边滑动
        if (trun==TRUN.LEFT_TORIGHT) {

            //绘制变色
            canvasText(canvas,0,currentWidth,slidingChangePaint);

            //绘制不变色
            canvasText(canvas,currentWidth,getWidth(),slidingPaint);


        }else{

           //绘制不变色
            canvasText(canvas,0,currentWidth,slidingPaint);
            //绘制变色
            canvasText(canvas,currentWidth,getWidth(),slidingChangePaint);
        }

    }
    private  void canvasText(Canvas canvas,float start,float end,Paint paint){

        canvas.clipRect(start,0,end,getHeight());

        int x= getWidth()/2-(int) slidingPaint.measureText(SlidingText)/2;

        Paint.FontMetricsInt fontMetricsInt = slidingPaint.getFontMetricsInt();

        int dy=(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;

        int baseLine=getHeight()/2+dy;

        canvas.drawText(SlidingText,x,baseLine,paint);

        canvas.restore();

    }

    public enum TRUN{

        LEFT_TORIGHT,RIGHT_TOLEFT

    }
    //设置滑动进度 朝向
    public void setCurrentThis(float currentThis,TRUN trun){

        this.currentThis=currentThis;

        this.trun=trun;

        invalidate();
    }

    //设置变化的颜色
    public void setSlidingChangeColor(int SlidingChangeColor){

        this.SlidingChangeColor=SlidingChangeColor;

    }
    //设置默认颜色
    public void setSlidingColor(int SlidingColor){

        this.SlidingColor=SlidingColor;

    }

    public void setSlidingText(String SlidingText){

        this.SlidingText=SlidingText;

    }

    public void setSlidingTextSize(int SlidingTextSize){

        this.SlidingTextSize=SlidingTextSize;

    }




}
