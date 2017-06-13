package com.example.wangjingyun.componentbased.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.wangjingyun.componentbased.R;

/**
 * Created by Administrator on 2017/6/13.
 *
 *
 * 自定义环形比例图
 */

public class CircularScaleDiagramView extends View {

    private String circularScaleDiagramViewText;

    private int circularScaleDiagramViewTextSize=30;

    private int circularScaleDiagramViewTextColor= Color.BLACK;

    private int circularScaleDiagramViewCircleColor=Color.RED;

    private int circularScaleDiagramViewArcColor=Color.BLUE;

    private int circularScaleDiagramViewArcWidth=30;

    private float currentThis;

    private float current;

    private Paint textPaint ;

    private Paint circlePaint;

    private Paint arcPaint;

    public CircularScaleDiagramView(Context context) {
        this(context,null);
    }

    public CircularScaleDiagramView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CircularScaleDiagramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularScaleDiagramView);

        circularScaleDiagramViewText=typedArray.getString(R.styleable.CircularScaleDiagramView_CircularScaleDiagramViewText);

        circularScaleDiagramViewTextSize=typedArray.getDimensionPixelSize(R.styleable.CircularScaleDiagramView_CircularScaleDiagramViewTextSize,circularScaleDiagramViewTextSize);

        circularScaleDiagramViewTextColor=typedArray.getColor(R.styleable.CircularScaleDiagramView_CircularScaleDiagramViewTextColor,circularScaleDiagramViewTextColor);

        circularScaleDiagramViewCircleColor=typedArray.getColor(R.styleable.CircularScaleDiagramView_CircularScaleDiagramViewCircleColor,circularScaleDiagramViewCircleColor);

        circularScaleDiagramViewArcColor=typedArray.getColor(R.styleable.CircularScaleDiagramView_CircularScaleDiagramViewArcColor,circularScaleDiagramViewArcColor);

        circularScaleDiagramViewArcWidth=typedArray.getDimensionPixelSize(R.styleable.CircularScaleDiagramView_CircularScaleDiagramViewArcWidth,circularScaleDiagramViewArcWidth);
        typedArray.recycle();
        initPaint();
    }

    private void initPaint() {

        //内部字体
        textPaint=new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(circularScaleDiagramViewTextColor);
        textPaint.setTextSize(circularScaleDiagramViewTextSize);
        textPaint.setStyle(Paint.Style.FILL);
        //内部圆
        circlePaint=new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(circularScaleDiagramViewCircleColor);
        circlePaint.setStyle(Paint.Style.FILL);

        //圆弧
        arcPaint=new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setColor(circularScaleDiagramViewArcColor);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(circularScaleDiagramViewArcWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMeasureSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMeasureSpecSize = MeasureSpec.getSize(heightMeasureSpec);


        if(widthMeasureSpecMode==MeasureSpec.AT_MOST){

            widthMeasureSpecSize=300;

        }
        if(heightMeasureSpecMode==MeasureSpec.AT_MOST){

            heightMeasureSpecSize=300;
        }

        setMeasuredDimension(widthMeasureSpecSize,heightMeasureSpecSize);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int radius=getWidth()/2-circularScaleDiagramViewArcWidth/2;

        //绘制外层圆弧
        RectF rectf=new RectF(getWidth()/2-radius,getWidth()/2-radius,getWidth()/2+radius,getWidth()/2+radius);
        canvas.drawArc(rectf,270,currentThis,false,arcPaint);

        //绘制内部圆

        canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2-circularScaleDiagramViewArcWidth-10,circlePaint);

        //绘制内部文字文字居中
        int x= getWidth()/2-(int) textPaint.measureText(Integer.toString((int)current))/2;

        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();

        int dy=(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;

        int baseLine=getHeight()/2+dy;

        canvas.drawText(Integer.toString((int)current),x,baseLine,textPaint);

    }

    public void setCurrent(float current ){

        this.current=current;

        float newcurrent = (current / 100) * 360;

        this.currentThis=newcurrent;

        invalidate();
    }


}
