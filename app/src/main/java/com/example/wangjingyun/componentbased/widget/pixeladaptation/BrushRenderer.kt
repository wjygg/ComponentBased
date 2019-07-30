package com.example.wangjingyun.componentbased.widget.pixeladaptation

import android.content.Context
import android.graphics.Canvas
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View

/**
 * 自定义 paint 渲染器
 */
class BrushRenderer: View {

    var paint : Paint ?=null

    constructor(mContext:Context):super(mContext) {

    }

    constructor(mContext:Context, mAttributeSet: AttributeSet):super(mContext,mAttributeSet) {

    }

    constructor(mContext:Context, mAttributeSet: AttributeSet,defStyleAttr: Int):super(mContext,mAttributeSet,defStyleAttr) {

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


    }

}