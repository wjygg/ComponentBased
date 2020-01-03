package com.example.wangjingyun.componentbased.widget


import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

import com.example.wangjingyun.componentbased.R

/**
 * Created by Administrator on 2017/5/24.
 * https://blog.csdn.net/xlh1191860939/article/details/79412319
 */

class TestTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var testTextTextColor = Color.BLACK

    private var testTextTextSize = 15

    private val testTextText: String?

    private var paint: Paint? = null

    init {


        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TestTextView)

        testTextText = typedArray.getString(R.styleable.TestTextView_TestTextViewText)

        testTextTextColor = typedArray.getColor(R.styleable.TestTextView_TestTextViewTextColor, testTextTextColor)

        testTextTextSize = typedArray.getDimensionPixelSize(R.styleable.TestTextView_TestTextViewTextSize, testTextTextSize)

        typedArray.recycle()

        initPaint()

    }

    private fun initPaint() {

        paint = Paint()
        //抗锯齿
        paint!!.isAntiAlias = true
        //实心画笔
        paint!!.style = Paint.Style.FILL
        paint!!.color = testTextTextColor
        paint!!.textSize = testTextTextSize.toFloat()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

        //确定的值
        var widthSize = View.MeasureSpec.getSize(widthMeasureSpec)

        var heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        //宽度包裹的时候
        if (widthMode == View.MeasureSpec.AT_MOST) {

            val bounds = Rect()
            //获取字符串的宽度
            paint!!.getTextBounds(testTextText, 0, testTextText!!.length, bounds)
            //字符串的长度赋值给宽度
            widthSize = (paint!!.measureText(testTextText) + paddingLeft.toFloat() + paddingRight.toFloat()).toInt()
        }

        //高度包裹的时候
        if (heightMode == View.MeasureSpec.AT_MOST) {

            val bounds = Rect()
            //获取字符串的高度
            paint!!.getTextBounds(testTextText, 0, testTextText!!.length, bounds)
            //字符串的长度赋值给宽度
            heightSize = bounds.height() + paddingBottom + paddingTop
        }

        setMeasuredDimension(widthSize, heightSize)

    }


    override fun onDraw(canvas: Canvas) {

        //获取绘制字体的 baseline

        val fontMetricsInt = paint!!.fontMetricsInt
        //(fontMetricsInt.bottom-fontMetricsInt.top)/2 字体高度的一半
        val dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom
        //getHeight()/2 控件的一半
        val baseline = height / 2 + dy


        val bounds = Rect()
        //获取字符串的宽度
        paint!!.getTextBounds(testTextText, 0, testTextText!!.length, bounds)
        //x偏移量
        val x = (width / 2 - paint!!.measureText(testTextText) / 2).toInt()

        canvas.drawText(testTextText, x.toFloat(), baseline.toFloat(), paint!!)
    }
}
