package com.example.wangjingyun.componentbased.widget.pixeladaptation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


/**
 * canvas 操作
 */
class CanvasStatus : View {

    lateinit var mPaint:Paint

    constructor(mContext:Context):super(mContext){

    }

    constructor(mContext:Context,attributeSet: AttributeSet):super(mContext,attributeSet){

    }
    constructor(mContext:Context,attributeSet: AttributeSet,defStyleAttr:Int):super(mContext,attributeSet,defStyleAttr){

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //canvas 平移操作
        canvas!!.drawRect(0f,0f,measuredWidth.toFloat(),measuredHeight.toFloat(),mPaint)
        canvas.translate(200f,200f)
        mPaint.color= Color.BLUE
        canvas!!.drawRect(0f,0f,measuredWidth.toFloat(),measuredHeight.toFloat(),mPaint)
        //canvas 缩放操作

        canvas.scale()
    }

    fun initPaint(){
        mPaint= Paint()
        mPaint.isAntiAlias=true
        mPaint.style=Paint.Style.FILL
        mPaint.color= Color.BLACK
    }

}