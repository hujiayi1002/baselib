package com.ocse.baseandroid.utils

import android.util.Log
import com.ocse.baseandroid.BuildConfig


class Logger {
    companion object {
        var TAG = "Logger"

        fun setTag(tag: String) {
            TAG = tag
        }

        fun e(msg: String?) {
            if (BuildConfig.DEBUG&&!msg.isNullOrEmpty()) {
                Log.e(TAG, "<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<->->->->->->->->->->->->->->->->->->->->->")
                val segmentSize = (3.7 * 1024).toInt()//一次只能打印4K长度,所以先打印3k
                var index = 0
                msg?.run {
                    while (msg.length > segmentSize) { // 循环分段打印日志
                        val logContent = msg.substring(0, segmentSize)
                        msg.replace(logContent, "")
                        Log.e(TAG, logContent)
                    }
                    Log.e(TAG, "$msg")
                }
            }

            fun d(msg: String?) {
                if (BuildConfig.DEBUG&&!msg.isNullOrEmpty()) {
                    Log.d(TAG, "<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<->->->->->->->->->->->->->->->->->->->->->")
                    Log.d(TAG, "" + msg)
                }

            }

            fun i(msg: String?) {
                if (BuildConfig.DEBUG&&!msg.isNullOrEmpty()) {
                    Log.i(TAG, "<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-<->->->->->->->->->->->->->->->->->->->->->")
                    Log.i(TAG, "" + msg)
                }

            }

        }
    }
}