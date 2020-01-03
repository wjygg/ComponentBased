package com.example.wangjingyun.componentbased.widget.pixeladaptation

import android.content.Context
import android.util.Log

class EventBus{

    private lateinit var mContent:Context

    companion object {

        @Volatile
        private var eventBus: EventBus? = null

        fun getInstance(mContent:Context):EventBus{
            if (eventBus == null) {
                synchronized(EventBus::class.java) {
                    if (eventBus == null) {
                        eventBus = EventBus(mContent)
                    }
                }
            }
            return eventBus!!
        }
    }

    private constructor(mContent:Context) {
        this.mContent=mContent
    }

}
