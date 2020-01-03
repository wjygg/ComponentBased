package com.example.wangjingyun.componentbased.widget.pixeladaptation

import android.content.Context
import android.graphics.*
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View
import com.example.wangjingyun.componentbased.R
import android.graphics.Bitmap




/**
 *  图像混合模式  图片变成圆形
 *  https://www.jianshu.com/p/467cc46273c6
 */
open class XferMode :View{

    lateinit var mPaint:Paint

    constructor(mContext:Context):this(mContext,null){
    }

    constructor(mContext:Context, attributeSet: AttributeSet?):this(mContext,attributeSet,0){

    }
    constructor(mContext:Context, attributeSet: AttributeSet?, defStyleAttr:Int):super(mContext,attributeSet,defStyleAttr){
     initPaint()
    }

    fun initPaint(){
        //部分api不支持硬件加速 禁用硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE,null)
        mPaint= Paint()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //离屏绘制
        var layerId=canvas!!.saveLayer(0f,0f,measuredWidth.toFloat(),measuredHeight.toFloat(),mPaint)

        canvas.drawBitmap(createDstBitmap(), 0f, 0f, mPaint);
        mPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.DST_IN))
        canvas.drawBitmap(createSrcBitmap(measuredWidth,measuredHeight),0f, 0f, mPaint)
        mPaint.setXfermode(null)

        //离屏绘制
        canvas.restoreToCount(layerId)

    }

    fun  createDstBitmap() : Bitmap {
        return BitmapFactory.decodeResource(getResources(), R.drawable.actionbar_discover_prs);
    }


    fun  createSrcBitmap(width:Int , height:Int ) :Bitmap{
        var bitmap = Bitmap.createBitmap(width.toInt(), height.toInt(), Bitmap.Config.ARGB_8888);
        var canvas = Canvas(bitmap);
        var dstPaint =  Paint(Paint.ANTI_ALIAS_FLAG);
        dstPaint.setColor(Color.parseColor("#ec6941"));
        canvas.drawCircle((width/2).toFloat(), (height/2).toFloat(), (height/2).toFloat(), dstPaint);
        return bitmap;
    }


}

