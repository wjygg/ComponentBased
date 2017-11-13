package com.example.wangjingyun.componentbased.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * 拖拽贝塞尔曲线
 * Created by Administrator on 2017/11/7.
 */

public class DragControlView extends View{

    // 两个圆的圆形
    private PointF mFixationPoint, mDragPoint;

    private Paint paint;

    //固定圆x
    private float fixedCircleX;
    //固定圆y
    private float fixedCircleY;
    //拖动圆x
    private float dragCircleX;
    //拖动圆y
    private float dragCircleY;

    private float fixedCircleRadius=17;

    private float dragCircleRadius=24;

    public DragControlView(Context context) {
        this(context,null);
    }

    public DragControlView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DragControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint(){

        paint=new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setDither(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDragPoint == null || mFixationPoint == null) {
            return;
        }

        //绘制拖动圆
        canvas.drawCircle(mDragPoint.x,mDragPoint.y,dragCircleRadius,paint);


        float y=mDragPoint.y-mFixationPoint.y;
        float x=mDragPoint.x-mFixationPoint.x;

        //两个点之间的 距离
        double pointValue=Math.sqrt(x*x+y*y);

        float fixedRadius= (float) (fixedCircleRadius-(pointValue/20));

        if(fixedRadius<=3) //小于3 不显示

                return;
            //绘制固定圆
            canvas.drawCircle(mFixationPoint.x,mFixationPoint.y,fixedRadius,paint);

       //绘制贝塞尔曲线
        double acos = Math.acos(x / pointValue);

        //p1点
        double p1x = Math.sin(acos) * fixedRadius+mFixationPoint.x;
        double p1y = mFixationPoint.y-Math.cos(acos) *fixedRadius;

        //p2点
        double p2x = Math.sin(acos) * dragCircleRadius + mDragPoint.x;
        double p2y = mDragPoint.y-Math.cos(acos) * dragCircleRadius;

        //p3点
        double p3x = mFixationPoint.x - Math.sin(acos) * fixedRadius;
        double p3y = Math.cos(acos) * fixedRadius + mFixationPoint.y;


        //p4点
        double p4x = mDragPoint.x - Math.sin(acos) * dragCircleRadius;
        double p4y =  Math.cos(acos) * dragCircleRadius+mDragPoint.y;

        //p1-p2之间控制点
        float controlPointX = (mDragPoint.x + mFixationPoint.x)/ 2 ;
        float controlPointy = (mDragPoint.y + mFixationPoint.y)/ 2;

        Path path=new Path();
        path.moveTo((float) p1x,(float)p1y);
        path.quadTo(controlPointX,controlPointy,(float) p2x,(float)p2y);
        path.lineTo((float) p4x,(float)p4y);
        path.quadTo(controlPointX,controlPointy,(float) p3x,(float)p3y);
        path.close();
        canvas.drawPath(path,paint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                fixedCircleX=event.getX();
                fixedCircleY=event.getY();
                initPoint(fixedCircleX,fixedCircleY);
                break;

            case MotionEvent.ACTION_MOVE:

                dragCircleX=event.getX();
                dragCircleY=event.getY();
                updateDragPoint(dragCircleX, dragCircleY);
                break;

            case MotionEvent.ACTION_UP:
                break;

        }
        invalidate();

        return true;
    }

    private void updateDragPoint(float moveX, float moveY) {
        mDragPoint.x = moveX;
        mDragPoint.y = moveY;
    }

    private void initPoint(float downX, float downY){
        mFixationPoint = new PointF(downX, downY);
        mDragPoint = new PointF(downX, downY);
    }


    /**
     * @param context 上下文
     * @param values dp值
     * @return
     */
    private float getDesgin(Context context,float values){

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,values, context.getResources().getDisplayMetrics());

    }
}
