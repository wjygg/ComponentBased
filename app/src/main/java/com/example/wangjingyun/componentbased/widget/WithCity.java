package com.example.wangjingyun.componentbased.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.wangjingyun.componentbased.R;

/**
 * 58  切换控件
 *
 * Created by wangjingyun on 2017/7/24.
 */

public class WithCity extends View {

    private Paint squarePaint;

    private Paint circlePaint;

    private Paint trianglePaint;

    private CurrentType currentType=CurrentType.circle;

    public WithCity(Context context) {
        this(context,null);
    }

    public WithCity(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WithCity(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //初始化 圆形 正方形  三角形  画笔
        initPaint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthMeasureSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMeasureSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        int withSpecSize = getMeasureSpecSize(widthMeasureSpecMode, widthMeasureSpecSize);

        int heightSpecSize = getMeasureSpecSize(heightMeasureSpecMode, heightMeasureSpecSize);

        //宽高相等
        setMeasuredDimension(Math.min(withSpecSize,heightSpecSize),Math.min(withSpecSize,heightSpecSize));
    }

    private int getMeasureSpecSize(int measureSpecMode,int measureSpecSize){

        int  size=0;

        if(measureSpecMode==MeasureSpec.EXACTLY){

            size=measureSpecSize;

        }else{

            size=100;
        }

        return size;
    }



    private void initPaint() {

        circlePaint=new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(getResources().getColor(R.color.blue_30));

        squarePaint=new Paint();
        squarePaint.setAntiAlias(true);
        squarePaint.setStyle(Paint.Style.FILL);
        squarePaint.setColor(getResources().getColor(R.color.orange));

        trianglePaint=new Paint();
        trianglePaint.setAntiAlias(true);
        trianglePaint.setStyle(Paint.Style.FILL);
        trianglePaint.setColor(getResources().getColor(R.color.pink_text_color));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (currentType){

            case circle:

                //绘制圆形
                canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2,circlePaint);
                break;
            case square:
                //绘制正方形
                RectF rectF=new RectF(0,0,getWidth(),getHeight());
                canvas.drawRect(rectF,squarePaint);
                break;

            case triangle:

                //绘制三角形
                Path path = new Path();
                path.moveTo(0,getHeight());// 此点为多边形的起点
                path.lineTo(getWidth()/2, 0);
                path.lineTo(getWidth(), getHeight());
                path.close(); // 使这些点构成封闭的多边形
                canvas.drawPath(path,trianglePaint);
                break;
        }
    }

    public void setCurrentType(){

        switch (currentType){

            case circle:

                currentType=CurrentType.square;

                break;
            case square:

                currentType=CurrentType.triangle;

                break;

            case triangle:

                currentType=CurrentType.circle;

                break;
        }
        //重新绘制
        invalidate();
    }

    public enum CurrentType{

        circle,square,triangle;
    }
}
