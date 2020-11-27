package com.ocse.baseandroid.utils

import android.os.Handler
import android.os.Message
import java.util.*

class HandlerUtil private constructor() {
    private fun init() {
        if (mReceiveMsgListenerList == null) {
            mReceiveMsgListenerList =
                ArrayList()
        }
        if (handler == null) {
            handler = object : Handler() {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    if (mReceiveMsgListenerList != null && mReceiveMsgListenerList!!.size > 0) {
                        for (listener in mReceiveMsgListenerList!!) {
                            listener.handlerMessage(msg)
                        }
                    }
                }
            }
        }
    }

    /**
     * 注册 Handler
     *
     * @param listener 收到消息回调接口
     */
    fun register(listener: OnReceiveMessageListener) {
        init()
        mReceiveMsgListenerList!!.add(listener)
    }

    /**
     * 取消注册 Handler
     *
     * @param index 收到消息回调接口的下标
     */
    fun unregister(index: Int) {
        if (mReceiveMsgListenerList != null && index >= 0 && index < mReceiveMsgListenerList!!.size
        ) {
            mReceiveMsgListenerList!!.removeAt(index)
        }
    }

    /**
     * 取消注册 Handler
     *
     * @param listener 收到消息回调接口
     */
    fun unregister(listener: OnReceiveMessageListener?) {
        if (mReceiveMsgListenerList != null && listener != null) {
            mReceiveMsgListenerList!!.remove(listener)
        }
    }

    /**
     * 获取所有事件
     *
     * @return 事件列表
     */
    val receiveMsgListenerList: List<OnReceiveMessageListener>?
        get() = mReceiveMsgListenerList

    /**
     * 清除所有事件
     */
    fun clear() {
        if (mReceiveMsgListenerList != null && mReceiveMsgListenerList!!.size > 0) {
            mReceiveMsgListenerList!!.clear()
        }
    }

    /**
     * 收到消息回调接口
     */
    interface OnReceiveMessageListener {
        fun handlerMessage(msg: Message?)
    }

    private object HandlerUtilHolder {
         val INSTANCE = HandlerUtil()
    }

    companion object {
        /**
         * 获取 Handler 对象
         *
         * @return
         */
        var handler: Handler? = null
            private set
        private var mReceiveMsgListenerList: MutableList<OnReceiveMessageListener>? =
            null

        val instance: HandlerUtil
            get() = HandlerUtilHolder.INSTANCE
    }

    init {
        init()
    }
}