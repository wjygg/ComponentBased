package com.example.wangjingyun.componentbased.widget.pixeladaptation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnticipateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class SplashView : View {

    lateinit var mContext: Context

    lateinit var mPaint: Paint

    lateinit var mHolePaint:Paint

    lateinit var HolePaint:Paint


    var mCurrentRotateAngle:Float=0f

    lateinit var valueAnimator:ValueAnimator

    var rotateState:RotateState?=null

    var smallCircleRadius:Float=0f

    var radius :Float=0f

    var maxRadius:Float=0f

    var mCurrentHoleRadius:Float=0f //扩散圆半径

    var intArray:Array<Int> = arrayOf( Color.parseColor("#FF9600"),Color.parseColor("#02D1AC"),Color.parseColor("#FFD200"),Color.parseColor("#00C6FF"),Color.parseColor("#FF3892"),Color.parseColor("#FF9600"))

    var mBackgroundColor:Int = Color.WHITE

    var stateType:StateType=StateType.ROTATESTATE

    var screenWidth:Int=0

    var screenHeight:Int=0

    var mDistance:Double=0.0

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

        smallCircleRadius=pxToDp(5f)
        radius=pxToDp(25f)//目前半径
        maxRadius=pxToDp(50f)//扩散半径

        //获取宽高
        initDisplayMetrics()
        //获取对角线半径
        mDistance = Math.hypot(screenWidth.toDouble(), screenHeight.toDouble()) / 2


        mHolePaint=Paint()
        mHolePaint.isAntiAlias=true
        mHolePaint.style=Paint.Style.STROKE
        mHolePaint.color=mBackgroundColor

        HolePaint=Paint()
        HolePaint.isAntiAlias=true
        HolePaint.style=Paint.Style.STROKE
        HolePaint.strokeWidth=0f
        HolePaint.color=mBackgroundColor
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(getMeasureSpec(widthMeasureSpec),getMeasureSpec(heightMeasureSpec))
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

      /*  var x:Float=measuredWidth/2.toFloat() //圆心x
        var y:Float=measuredHeight/2.toFloat() //圆心y
        mHolePaint.strokeWidth=10f
        canvas!!.drawCircle(x,y,95f,mHolePaint)

        canvas!!.drawRect(Rect(x.toInt()-100,y.toInt()-100,x.toInt()+100,y.toInt()+100),HolePaint)*/
        if(stateType==StateType.MERGINSTATE||stateType==StateType.ROTATESTATE){
            canvas!!.drawColor(mBackgroundColor)
            drawCircle(canvas!!)
        }else{
            var x:Float=measuredWidth/2.toFloat() //圆心x
            var y:Float=measuredHeight/2.toFloat() //圆心y
            var mStrokeWidth=mDistance-mCurrentHoleRadius
            var radius = mStrokeWidth /2 + mCurrentHoleRadius;
            mHolePaint.strokeWidth=mStrokeWidth.toFloat()
            canvas!!.drawCircle(x,y,radius.toFloat(),mHolePaint)
        }

        //旋转动画
        if(rotateState==null) rotateState=RotateState()

    }

    fun drawCircle(mCanvas: Canvas){
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
            mCanvas!!.drawCircle(rx.toFloat(),ry.toFloat(),smallCircleRadius,mPaint)

        }
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


    //旋转动画
    inner class RotateState{//非静态内部类

        constructor():super(){
            stateType=StateType.ROTATESTATE
            valueAnimator=ValueAnimator.ofFloat(0f, (Math.PI *2).toFloat())
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
                    MerginState()
                }
            })
            valueAnimator.start()
        }
    }

    //扩散动画
    inner class MerginState{

        constructor():super(){
            stateType=StateType.MERGINSTATE
            valueAnimator=ValueAnimator.ofFloat(radius,maxRadius,smallCircleRadius)
            valueAnimator.setDuration(1200)
            valueAnimator.interpolator=LinearInterpolator()
            valueAnimator.addUpdateListener {
                radius=it.animatedValue as Float
                invalidate()
            }
            valueAnimator.addListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    ExpandState()
                }
            })
            valueAnimator.start()
        }
    }

    //绘制水波纹效果
    inner class ExpandState{//非静态内部类

        constructor():super(){
            stateType=StateType.EXPANDSTATE
            valueAnimator=ValueAnimator.ofFloat(smallCircleRadius, mDistance.toFloat())
            valueAnimator.setDuration(2000)
            valueAnimator.interpolator=AnticipateInterpolator()
            /*valueAnimator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {0
                override fun onAnimationUpdate(animation: ValueAnimator?) {
                }
            })*/
            valueAnimator.addUpdateListener {
                mCurrentHoleRadius=it.animatedValue as Float
                invalidate()
            }
            valueAnimator.start()
        }
    }

    enum class StateType{
        //旋转 扩散聚合 水波纹
        ROTATESTATE,MERGINSTATE,EXPANDSTATE
    }

    fun pxToDp(value: Float):Float{

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,mContext.resources.displayMetrics )
    }

    fun initDisplayMetrics(){

        if(screenWidth==0||screenHeight==0){
            var windowManager:WindowManager =context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            if(windowManager!=null){
                var displayMetrics=DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(displayMetrics)
                if(displayMetrics.widthPixels>displayMetrics.heightPixels){
                    screenWidth=displayMetrics.heightPixels
                    screenHeight=displayMetrics.widthPixels
                }else{
                    screenWidth=displayMetrics.widthPixels
                    screenHeight=displayMetrics.heightPixels
                }
            }
        }
    }
}