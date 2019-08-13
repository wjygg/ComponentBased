package com.example.wangjingyun.componentbased.widget.pixeladaptation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class SplashView : View {

    lateinit var mContext: Context

    lateinit var mPaint: Paint

    var radius :Float=0f //半径

    var mCurrentRotateAngle:Float=0f

    lateinit var valueAnimator:ValueAnimator

    var rotateState:RotateState?=null

    var intArray:Array<Int> = arrayOf( Color.parseColor("#FF9600"),Color.parseColor("#02D1AC"),Color.parseColor("#FFD200"),Color.parseColor("#00C6FF"),Color.parseColor("#FF3892"),Color.parseColor("#FF9600"))

    constructor(mContext:Context):super(mContext){
        this.mContext=mContext
        initPaint()
    }

    constructor(mContext: Context, attributeSet: AttributeSet):super(mContext,attributeSet){
        this.mContext=mContext
        initPaint()
    }

    constructor(mContext: Context, attributeSet: AttributeSet,defStyleAttr:Int):super(mContext,attributeSet,defStyleAttr){
        this.mContext=mContext
        initPaint()
    }

    fun initPaint(){
        mPaint= Paint()
        mPaint.style=Paint.Style.FILL
        mPaint.isAntiAlias=true

        radius=pxToDp(25f)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(getMeasureSpec(widthMeasureSpec),getMeasureSpec(heightMeasureSpec))
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //圆之间角度
        var angle=Math.PI*2/6
        var x:Float=measuredWidth/2.toFloat() //圆心x
        var y:Float=measuredHeight/2.toFloat() //圆心y
        for(intArray in intArray.withIndex()){
            var circlAngle=intArray.index*angle+mCurrentRotateAngle

            var rx= cos(circlAngle)*radius+x

            var ry= sin(circlAngle)*radius+y

            mPaint.color=intArray.value
            //绘制小圆点
            canvas!!.drawCircle(rx.toFloat(),ry.toFloat(),pxToDp(5f),mPaint)
        }

        //旋转动画
        if(rotateState==null)
         rotateState=RotateState()

    }

    fun getMeasureSpec(measureSpec: Int):Int{
        var newSize=0
        var measureSpecModel=MeasureSpec.getMode(measureSpec)
        var measureSpecSize=MeasureSpec.getSize(measureSpec)

        if(measureSpecModel==MeasureSpec.AT_MOST){

            newSize=pxToDp(300f).toInt()
        }else{
            newSize=measureSpecSize
        }
        return newSize
    }




    fun pxToDp(value: Float):Float{

       return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,mContext.resources.displayMetrics )
    }



    inner class RotateState { //非静态内部类

        constructor(){
            valueAnimator=ValueAnimator.ofFloat(0f, (Math.PI *2).toFloat())
            valueAnimator.repeatCount=2
            valueAnimator.setDuration(1200)
            valueAnimator.interpolator=LinearInterpolator()
            /*valueAnimator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
                override fun onAnimationUpdate(animation: ValueAnimator?) {
                }
            })*/
            valueAnimator.addUpdateListener {
                mCurrentRotateAngle=it.animatedValue as Float
                invalidate()
            }
            valueAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)

                }
            })
            valueAnimator.start()
        }


    }

}