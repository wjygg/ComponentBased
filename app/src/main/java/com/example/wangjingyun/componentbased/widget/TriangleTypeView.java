package com.example.wangjingyun.componentbased.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.wangjingyun.componentbased.entity.TriangleTypeEntity;

import java.util.List;

import static android.R.attr.width;

/**
 * 绘制小三角
 * Created by Administrator on 2017/10/19.
 */

public class TriangleTypeView extends View {

    private String name;

    private String time;
    //线画笔
    private Paint paint;
    //字体画笔
    private Paint stringPaint;

    //小三角形一半的底边宽度
    private int triangleBottom=30;

    private Direction direction=Direction.DOWN;



    public TriangleTypeView(Context context) {
        this(context,null);
    }

    public TriangleTypeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TriangleTypeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //初始化 画笔
        initPaint();
    }

    private void initPaint() {

        paint=new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setTextSize(20);


        stringPaint=new Paint();
        stringPaint.setColor(Color.RED);
        stringPaint.setStyle(Paint.Style.STROKE);
        stringPaint.setAntiAlias(true);
        stringPaint.setTextSize(50);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=0; int height=0;
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //时间文字宽度
        int timeSize= (int) stringPaint.measureText(time);
        //名字文字宽度
        int nameSize= (int) stringPaint.measureText(name);

         width=Math.max(timeSize,nameSize)+100;

        if(heightMode==MeasureSpec.AT_MOST){
            //高度是包裹的时候默认200px
            height=200;
        }else{
            height=heightSize;
        }

        setMeasuredDimension(width,height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        switch (direction){

            case UP:

                Path path=new Path();
                //移动线到控件中间
                path.moveTo(0,getMeasuredHeight()/2);
                //绘制三角形
                path.lineTo(getMeasuredWidth()/2-triangleBottom,getMeasuredHeight()/2);
                path.lineTo(getMeasuredWidth()/2, getMeasuredHeight()/2+((float) (Math.sin(60)*triangleBottom*2)));
                path.lineTo(getMeasuredWidth()/2+triangleBottom,getMeasuredHeight()/2);
                //绘制剩余的线
                path.lineTo(getMeasuredWidth(),getMeasuredHeight()/2);
                canvas.drawPath(path,paint);

                //绘制三角上面的字体
                int stringSize= (int) stringPaint.measureText(name);
                //文字居中
                int alignCenter = getMeasuredWidth() / 2 - stringSize / 2;
                canvas.drawText(name,alignCenter,stringPaint.getTextSize(),stringPaint);
                //绘制三角形下面的文字
                int timeStringSize= (int) stringPaint.measureText(time);
                //文字居中
                int timeAlignCenter = getMeasuredWidth() / 2 - timeStringSize / 2;
                canvas.drawText(time,timeAlignCenter,getMeasuredHeight()-10,stringPaint);

                break;

            case DOWN:

                Path downPath=new Path();
                //移动线到控件中间
                downPath.moveTo(0,getMeasuredHeight()/2);
                //绘制三角形
                downPath.lineTo(getMeasuredWidth()/2-triangleBottom,getMeasuredHeight()/2);
                downPath.lineTo(getMeasuredWidth()/2, getMeasuredHeight()/2-((float) (Math.sin(60)*triangleBottom*2)));
                downPath.lineTo(getMeasuredWidth()/2+triangleBottom,getMeasuredHeight()/2);
                //绘制剩余的线
                downPath.lineTo(getMeasuredWidth(),getMeasuredHeight()/2);
                canvas.drawPath(downPath,paint);

                //绘制三角上面的字体
                int downStringSize= (int) stringPaint.measureText(time);
                //文字居中
                int downAlignCenter = getMeasuredWidth() / 2 - downStringSize / 2;
                canvas.drawText(time,downAlignCenter,stringPaint.getTextSize(),stringPaint);
                //绘制三角形下面的文字
                int downTimeStringSize= (int) stringPaint.measureText(name);
                //文字居中
                int downTimeAlignCenter = getMeasuredWidth() / 2 - downTimeStringSize / 2;
                canvas.drawText(name,downTimeAlignCenter,getMeasuredHeight()-10,stringPaint);

                break;

        }
    }

    /**
     * 设置数据
     * @param datas
     */
    public void setDatas(TriangleTypeEntity datas){

        name=datas.getName();
        time=datas.getTime();
        if(datas.getDirection()==0)
         this.direction=Direction.UP;
        if(datas.getDirection()==1)
         this.direction=Direction.DOWN;
    }

    public enum Direction{

        UP,DOWN

    }

}
