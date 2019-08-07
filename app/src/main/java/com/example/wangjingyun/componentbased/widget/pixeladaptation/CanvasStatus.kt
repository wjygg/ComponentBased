package com.example.wangjingyun.componentbased.widget.pixeladaptation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View


/**
 * canvas 操作
 */
class CanvasStatus : View {

    lateinit var mPaint:Paint

    lateinit var mContext: Context

    constructor(mContext:Context):super(mContext){
        this.mContext=mContext
        initPaint()
    }

    constructor(mContext:Context,attributeSet: AttributeSet):super(mContext,attributeSet){
        this.mContext=mContext
        initPaint()
    }
    constructor(mContext:Context,attributeSet: AttributeSet,defStyleAttr:Int):super(mContext,attributeSet,defStyleAttr){
        this.mContext=mContext
        initPaint()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //canvas 平移操作
        canvas!!.drawRect(0f,0f,measuredWidth.toFloat(),measuredHeight.toFloat(),mPaint)
        canvas.translate(200f,200f)
        mPaint.color= Color.BLUE
        canvas!!.drawRect(0f,0f,measuredWidth.toFloat(),measuredHeight.toFloat(),mPaint)
        //canvas 缩放操作
        canvas.scale(0.5f, 0.5f);
        //canvas 旋转操作
        canvas.rotate(45f);
        //canvas 切割操作
        canvas.clipRect(200, 200,700, 700); //画布被裁剪


        //canvas 矩阵
        var matrix:Matrix= Matrix()
        //matrix.setTranslate(50,50);
        //matrix.setRotate(45);
        matrix.setScale(0.5f, 0.5f);
        canvas.setMatrix(matrix);
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(0f,0f,700f,700f, mPaint);

        /**
         * 1.canvas内部对于状态的保存存放在栈中
         * 2.可以多次调用save保存canvas的状态，并且可以通过getSaveCount方法获取保存的状态个数
         * 3.可以通过restore方法返回最近一次save前的状态，也可以通过restoreToCount返回指定save状态。指定save状态之后的状态全部被清除
         * 4.saveLayer可以创建新的图层，之后的绘制都会在这个图层之上绘制，直到调用restore方法
         * 注意：绘制的坐标系不能超过图层的范围， saveLayerAlpha对图层增加了透明度信息
         */
        var state:Int = canvas.save();
        canvas.restore()
        canvas.restoreToCount(state);

        canvas.drawRect(200f,200f, 700f,700f, mPaint);
        var  layerId = canvas.saveLayer(0f,0f, 700f, 700f, mPaint);
        mPaint.setColor(Color.GRAY);
        var mtx = Matrix();
        mtx.setTranslate(100f,100f);
        canvas.setMatrix(mtx);
        canvas.drawRect(0f,0f,700f,700f, mPaint); //由于平移操作，导致绘制的矩形超出了图层的大小，所以绘制不完全
        canvas.restoreToCount(layerId);

        mPaint.setColor(Color.RED);
        canvas.drawRect(0f,0f,100f,100f, mPaint);
    }

    fun initPaint(){
        mPaint= Paint()
        mPaint.isAntiAlias=true
        mPaint.style=Paint.Style.FILL
        mPaint.color= Color.BLACK
    }

}