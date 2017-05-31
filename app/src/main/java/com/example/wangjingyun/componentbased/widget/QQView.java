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
 * 自定义qq计步器
 * Created by wangjingyun on 2017/5/21.
 */

public class QQView extends View {

    private int QQViewInnerColor=Color.RED;

    private int QQViewOutnerColor=Color.BLUE;

    private int QQViewBorderWidth=20;

    private int QQViewTextColor=Color.RED;

    private int QQViewTextSize=20;

    private String QQViewText;

    private Paint paint,textPaint,innerPaint;

    private float currentInt=0;

    private float maxInt=0;
    public QQView(Context context) {
        this(context,null);
    }

    public QQView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public QQView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.QQView);
        QQViewText=typedArray.getString(R.styleable.QQView_QQViewText);
        QQViewInnerColor=typedArray.getColor(R.styleable.QQView_QQViewInnerColor,QQViewInnerColor);
        QQViewOutnerColor=typedArray.getColor(R.styleable.QQView_QQViewOutnerColor,QQViewOutnerColor);
        QQViewBorderWidth= (int) typedArray.getDimension(R.styleable.QQView_QQViewBorderWidth,QQViewBorderWidth);
        QQViewTextColor=typedArray.getColor(R.styleable.QQView_QQViewTextColor,QQViewTextColor);
        QQViewTextSize=typedArray.getDimensionPixelSize(R.styleable.QQView_QQViewTextSize,QQViewTextSize);
        typedArray.recycle();

        initPaint();
    }

    private void initPaint() {
        //外圆弧
        paint=new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(QQViewBorderWidth);
        paint.setColor(QQViewOutnerColor);
        paint.setStrokeCap(Paint.Cap.ROUND);

        //内圆弧
        innerPaint=new Paint();
        innerPaint.setStyle(Paint.Style.STROKE);
        innerPaint.setAntiAlias(true);
        innerPaint.setStrokeWidth(QQViewBorderWidth);
        innerPaint.setColor(QQViewInnerColor);
        innerPaint.setStrokeCap(Paint.Cap.ROUND);

        //字体
        textPaint=new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setColor(QQViewTextColor);
        textPaint.setTextSize(QQViewTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int mWidth=getMeasureSpecLength(widthMeasureSpec);

        int mHeight=getMeasureSpecLength(heightMeasureSpec);

        setMeasuredDimension(mWidth,mHeight);



    }
    private int getMeasureSpecLength(int measureSpec){

        int size=0;
        int measureSpecMode = MeasureSpec.getMode(measureSpec);
        int measureSpecSize = MeasureSpec.getSize(measureSpec);

        if(measureSpecMode==MeasureSpec.EXACTLY){
            size= measureSpecSize;
        }else{
            size=300;
        }
        return  size;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int cententX=getWidth()/2;

        int radis=getWidth()/2-QQViewBorderWidth;

        RectF rectF=new RectF(cententX-radis,cententX-radis,cententX+radis,cententX+radis);

        canvas.drawArc(rectF,135,270,false,paint);


        //画内圆弧

        canvas.drawArc(rectF,135,(int)(currentInt/maxInt*270),false,innerPaint);

        //画文字
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int dy=(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
        int baseLine=getHeight()/2+dy;
        int x= (int) (getWidth()/2-textPaint.measureText(String.valueOf((int)(currentInt)))/2);
        canvas.drawText(String.valueOf((int)(currentInt)),x,baseLine,textPaint);
    }


    public synchronized  void setCurrent(float currentInt,float maxInt){

        this.currentInt=currentInt;
        this.maxInt=maxInt;


        invalidate();
     }
}


