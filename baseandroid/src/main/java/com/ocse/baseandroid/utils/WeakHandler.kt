package com.ocse.baseandroid.utils

import android.app.Activity
import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference

abstract class WeakHandler internal constructor(activityWeakReference: Activity) :
    Handler() {
    var activityWeakReference: WeakReference<Activity> = WeakReference(activityWeakReference)
    override fun handleMessage(msg: Message) {
        val activity = activityWeakReference.get()
        if (null != activity) {
            handleMessageWhenNotServive(msg)
        } else {
            handleMessageWhenServive(msg, activityWeakReference.get())
        }
    }

    //当引用对象存在（未被GC回收）时，调用此方法
    abstract fun handleMessageWhenServive(msg: Message?, host: Activity?)

    //当引用对象不存在（已被GC回收）时，调用此方法，非必须重写
    private fun handleMessageWhenNotServive(msg: Message?) {}

}