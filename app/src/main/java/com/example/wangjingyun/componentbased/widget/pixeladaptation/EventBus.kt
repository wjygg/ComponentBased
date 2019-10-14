package com.example.wangjingyun.componentbased.widget.pixeladaptation

class EventBus private constructor(){

   companion object {

       private var eventBus= EventBus()

       fun getInstance():EventBus{
           return eventBus
       }
   }
}
