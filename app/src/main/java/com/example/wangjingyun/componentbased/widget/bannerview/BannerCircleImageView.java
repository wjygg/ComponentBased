package com.example.wangjingyun.componentbased.widget.bannerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/8/31.
 */

public class BannerCircleImageView extends android.support.v7.widget.AppCompatImageView{

    private DotColor dotColor=DotColor.WHITE;

    private Paint bitmapPaint;

    public BannerCircleImageView(Context context) {
        this(context,null);
    }

    public BannerCircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BannerCircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
    }

    private void initPaint(){
        bitmapPaint=new Paint();
        bitmapPaint.setStyle(Paint.Style.FILL);
        bitmapPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        switch (dotColor){

            case WHITE:
                bitmapPaint.setColor(Color.WHITE);

                break;

            case RED:
                bitmapPaint.setColor(Color.RED);

                break;

            case BLACK:
                bitmapPaint.setColor(Color.BLACK);
                break;

            case YELLOW:
                bitmapPaint.setColor(Color.YELLOW);
                break;

            default:
                break;
        }

        canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,getMeasuredWidth()/2,bitmapPaint);

    }

    public void setImageColor(DotColor dotColor){

       this.dotColor=dotColor;

        invalidate();
    }


    public enum  DotColor{

        RED,WHITE,YELLOW,BLACK;
    }
}
