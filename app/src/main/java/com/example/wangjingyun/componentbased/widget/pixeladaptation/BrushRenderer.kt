package com.example.wangjingyun.componentbased.widget.pixeladaptation

import android.content.Context
import android.graphics.*
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View

/**
 * 自定义 paint 渲染器
 * https://blog.csdn.net/samlss/article/details/80807110#bitmapshader位图图像着色器
 */
class BrushRenderer: View {

    lateinit var paint : Paint

    var list:List<String> =ArrayList<String>()

    var array:Array<String> = arrayOf("测试")


    constructor(mContext:Context):super(mContext) {

        initPaint()
    }

    constructor(mContext:Context, mAttributeSet: AttributeSet):super(mContext,mAttributeSet) {
        initPaint()
    }

    constructor(mContext:Context, mAttributeSet: AttributeSet,defStyleAttr: Int):super(mContext,mAttributeSet,defStyleAttr) {
        initPaint()
    }

    private fun initPaint(){
        paint= Paint()
        paint.style=Paint.Style.FILL
        paint.isAntiAlias=true
    }

    override fun onDraw(canvas: Canvas?){
        super.onDraw(canvas)
        //线性渲染器
        var linearGradient:LinearGradient=LinearGradient(0f,0f, measuredWidth.toFloat(),measuredHeight.toFloat(),
                intArrayOf(Color.BLUE,Color.BLACK),floatArrayOf(0.3f,1f), Shader.TileMode.CLAMP)
        //bitmap shader



        paint.setShader(linearGradient)
        canvas!!.drawRect(Rect(0,0, measuredWidth,measuredHeight),paint)
    }

}