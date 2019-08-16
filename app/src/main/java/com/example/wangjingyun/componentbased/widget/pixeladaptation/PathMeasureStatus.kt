package com.example.wangjingyun.componentbased.widget.pixeladaptation

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.wangjingyun.componentbased.R

class PathMeasureStatus : View {

    lateinit var mPaint: Paint

    lateinit var mContext: Context

    var pos:FloatArray = FloatArray(2)

    var tan:FloatArray = FloatArray(2)

    var currentFloat:Float=0f

    var mMatrix:Matrix= Matrix()

    lateinit var mBitmap:Bitmap

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


    fun initPaint(){

        mPaint= Paint()
        mPaint.strokeWidth=5f
        mPaint.style=Paint.Style.STROKE
        mPaint.color=Color.BLACK
        mPaint.isAntiAlias=true


        var option=BitmapFactory.Options()
        option.inSampleSize=4
        mBitmap=BitmapFactory.decodeResource(resources, R.mipmap.arrow,option)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.translate(measuredWidth/2.toFloat(),measuredHeight/2.toFloat())

        var path: Path =Path()

        path.addCircle(0f,0f,100f,Path.Direction.CW)

        /*canvas.drawPath(path,mPaint)

        var pathMeasure:PathMeasure= PathMeasure()
        pathMeasure.setPath(path,true)

        Log.d("tag",(pathMeasure.length).toString())*/

        //pathmeasure 的getSegment 方法 截取一段线到 dst 中
        /*var dst: Path =Path()
        pathMeasure.getSegment(0f,200f,dst,true)
        mPaint.color=Color.BLUE
        canvas.drawPath(dst,mPaint)*/

        //pathMeasure 的nextContour 方法 跳转到下一段曲线
       /* path.addRect(-200f,-200f,200f,200f,Path.Direction.CW) //Path.Direction.CW 顺时针
        var pathMeasure:PathMeasure= PathMeasure(path,true)
        pathMeasure.nextContour()
        canvas.drawPath(path,mPaint)
        Log.d("tag",(pathMeasure.length).toString())*/

        //pathMeasure 的 getPosTan 方法 获取 某一长度的位置以及该位置的正切值
        canvas.drawPath(path, mPaint);
        currentFloat+=0.01.toFloat()
        if(currentFloat>1){
            currentFloat=0f
        }
        var mPathMeasure:PathMeasure= PathMeasure(path,false)
        mPathMeasure.getPosTan(mPathMeasure.length*currentFloat,pos,tan)
        //获取旋转的角度
        var degrees = Math.atan2(tan[1].toDouble(), tan[0].toDouble()) * 180.0 / Math.PI;
        mMatrix.reset()
        mMatrix.postRotate(degrees.toFloat(),(mBitmap.width/2).toFloat(),(mBitmap.height/2).toFloat())
        mMatrix.postTranslate(pos[0]-mBitmap.width / 2,pos[1]-mBitmap.height/2)
        canvas.drawBitmap(mBitmap,mMatrix, mPaint);
        invalidate()
    }

}