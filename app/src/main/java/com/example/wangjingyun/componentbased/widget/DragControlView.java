package com.example.wangjingyun.componentbased.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.wangjingyun.componentbased.utils.StatusBarUtils;

/**
 * 拖拽贝塞尔曲线
 * Created by Administrator on 2017/11/7.
 */

public class DragControlView extends View{

    // 两个圆的圆形
    private PointF mFixationPoint, mDragPoint;

    private Paint paint,textPaint;

    private float fixedCircleRadius=17;

    private float dragCircleRadius=24;

    private Context context;



    public DragControlView(Context context) {
        this(context,null);
    }

    public DragControlView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DragControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        initPaint();
    }

    private void initPaint(){

        paint=new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setDither(true);

        textPaint=new Paint();
        textPaint.setTextSize(12);
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(getMeasuredDimension(widthMeasureSpec),getMeasuredDimension(heightMeasureSpec));
    }


    private int getMeasuredDimension(int measureSpec){

        int newWidth=0;

        int size=MeasureSpec.getSize(measureSpec);
        int mode = MeasureSpec.getMode(measureSpec);

        if(mode==MeasureSpec.AT_MOST){
            newWidth=100;
        }else{
            newWidth=size;
        }

        return newWidth;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //获取绘制字体的 baseline

        Paint.FontMetricsInt fontMetricsInt= paint.getFontMetricsInt();
        //(fontMetricsInt.bottom-fontMetricsInt.top)/2 字体高度的一半
        int dy=(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
        //getHeight()/2 控件的一半
        int baseline=getHeight()/2+dy;
        //x偏移量
        int xLine= (int) (getWidth()/2-paint.measureText("17")/2);
        canvas.drawText("17",xLine,baseline,textPaint);

        if (mDragPoint == null || mFixationPoint == null) {
            return;
        }

        //绘制bagview
        canvas.drawCircle(mDragPoint.x,mDragPoint.y,dragCircleRadius,paint);

        //文字居中
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


    public void updateDragPoint(float moveX, float moveY) {
        mDragPoint.x = moveX;
        mDragPoint.y = moveY;
    }

    public void initPoint(float downX, float downY){
        mFixationPoint = new PointF(downX, downY);
        mDragPoint = new PointF(downX, downY);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
    }

    /**
     * @param context 上下文
     * @param values dp值
     * @return
     */
    private float getDesgin(Context context,float values){

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,values, context.getResources().getDisplayMetrics());

    }

    public static class DragViewListener implements OnTouchListener{

        private  WindowManager wmManager;

        private Context context;

        private DragControlView dragControlView;

        public DragViewListener(Context context){

            this.context=context;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    //隐藏自己
                    v.setVisibility(INVISIBLE);
                    //将自己放到windowsmanger上
                    wmManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                    WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
                    wmParams.format= PixelFormat.TRANSLUCENT;
                    dragControlView=new DragControlView(context);
                    float fixedCircleX=event.getRawX();
                    float fixedCircleY=event.getRawY()- StatusBarUtils.getStatusHeight(context);
                    dragControlView.initPoint(fixedCircleX,fixedCircleY);
                    wmManager.addView(dragControlView,wmParams);

                    break;
                case MotionEvent.ACTION_MOVE:

                    float dragCircleX=event.getRawX();
                    float dragCircleY=event.getRawY();
                    dragControlView.updateDragPoint(dragCircleX, dragCircleY);

                    break;
                case MotionEvent.ACTION_UP:
                    //windowmanager 移除view
                    wmManager.removeView(dragControlView);
                    break;

            }
            dragControlView.invalidate();
            return true;
        }
    }
}
