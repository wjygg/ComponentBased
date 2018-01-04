package com.example.wangjingyun.componentbased.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * 开始  加载  安装
 * Created by Administrator on 2017/12/27.
 */

public class StateButton extends View {

    private Context context;

    private float defHeightAndWidth;

    //描边的宽度
    private static final int RECT_PAINT_WIDTH=3;
    //描边的宽度
    private float rectPaintWidth;


    //圆角矩形的弧度
    private static final int RECT_RADIAN_WIDTH=20;
    private float rectRadianWidth;

    //文字大小
    private static final int TEXT_SIZE=20;
    private float textSize;


    //圆角矩形画笔 字体画笔 填充圆角矩形的画笔
    private Paint rectPaint,textPaint,rectFillPaint;

    private String[] name=new String[]{"开始","安装"};

    private State state=State.PROGRESS;

    public StateButton(Context context) {
        this(context, null);
    }

    public StateButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        defHeightAndWidth=getPxToDp(200);
        rectPaintWidth=getPxToDp(RECT_PAINT_WIDTH);
        rectRadianWidth=getPxToDp(RECT_RADIAN_WIDTH);
        textSize=getPxToDp(TEXT_SIZE);
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(getSize(widthMeasureSpec),getSize(heightMeasureSpec));
    }

    private int getSize(int measureSpec) {

        int newSize=0;

        int measureMode = MeasureSpec.getMode(measureSpec);

        int measureSize = MeasureSpec.getSize(measureSpec);

        if(measureMode==MeasureSpec.EXACTLY){

            newSize=measureSize;
        }else{
            newSize= (int) defHeightAndWidth;
        }
        return newSize;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制一个圆角矩形
        RectF rectF=new RectF(rectPaintWidth,rectPaintWidth,getMeasuredWidth()-rectPaintWidth,getMeasuredHeight()-rectPaintWidth);
        canvas.drawRoundRect(rectF,rectRadianWidth,rectRadianWidth,rectPaint);

        //绘制文字
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int dy=(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
        int y=getMeasuredHeight()/2+dy;
        int x= (int) (getMeasuredWidth()/2-textPaint.measureText(name[0])/2);

        switch (state){

            case START:

                canvas.drawText(name[0],x,y,textPaint);

                break;

            case PROGRESS:

                // 1绘制 填充进度圆角矩形
                RectF rectF1=new RectF(rectPaintWidth,rectPaintWidth,getMeasuredWidth()-rectPaintWidth,getMeasuredHeight()-rectPaintWidth);
                canvas.drawRoundRect(rectF1,rectRadianWidth,rectRadianWidth,rectFillPaint);

                // 2 绘制进度文字
                canvas.drawText("45%",x,y,textPaint);

                break;

            case INSTALL:

                canvas.drawText(name[1],x,y,textPaint);
                break;
        }
    }


    private void initPaint(){

        rectPaint=new Paint();
        //宽度 2dp
        rectPaint.setStrokeWidth(rectPaintWidth);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setColor(Color.RED);
        rectPaint.setAntiAlias(true);

        textPaint=new Paint();
        textPaint.setTextSize(textSize);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.RED);
        textPaint.setAntiAlias(true);

        rectFillPaint=new Paint();
        rectFillPaint.setStyle(Paint.Style.FILL);
        rectFillPaint.setColor(Color.BLUE);
        rectFillPaint.setAntiAlias(true);
    }

    private float getPxToDp(float values){

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,values,context.getResources().getDisplayMetrics());
    }

    /**
     * 开始 加载 安装
     */
    enum State{

        START,PROGRESS,INSTALL;
    }

    /**
     *
     * @param currentProgress 当前进度
     * @param maxProgress 总进度
     */
    public synchronized void setCurrentProgress(float currentProgress,float maxProgress){

        //百分数
        float percentage = currentProgress / maxProgress ;

    }



}
