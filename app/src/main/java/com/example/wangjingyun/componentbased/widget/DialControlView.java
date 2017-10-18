package com.example.wangjingyun.componentbased.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义刻度盘空间
 * Created by Administrator on 2017/9/28.
 */

public class DialControlView extends View{

    private Paint paint;

    private Paint bluePaint;
    public DialControlView(Context context) {
        this(context,null);
    }

    public DialControlView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DialControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMeasure=getOnMeasure(widthMeasureSpec);
        int heightMeasure=getOnMeasure(heightMeasureSpec);

        setMeasuredDimension(widthMeasure,heightMeasure);
    }

    private int getOnMeasure(int measureSpec){

        int newSize=0;
        int mode = MeasureSpec.getMode(measureSpec);

        int size = MeasureSpec.getSize(measureSpec);

        if(mode==MeasureSpec.AT_MOST){

            newSize=300;
        }else{
            newSize=size;
        }
        return newSize;
    }

    private void initPaint(){

        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);


        bluePaint=new Paint();
        bluePaint.setAntiAlias(true);
        bluePaint.setStyle(Paint.Style.FILL);
        bluePaint.setStrokeWidth(10);
        bluePaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(new RectF(0,0,getWidth(),getHeight()),180,180,false,paint);

        for(int i=1;i<=6;i++){
            canvas.drawLine(0,getHeight()/2,30,getHeight()/2,bluePaint);
            canvas.rotate(i*10+180);
        }

    }
}
