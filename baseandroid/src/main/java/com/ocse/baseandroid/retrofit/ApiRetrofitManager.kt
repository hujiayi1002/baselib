package com.ocse.baseandroid.retrofit

import com.ocse.baseandroid.retrofit.base.BaseRetrofit.Companion.instance
import com.ocse.baseandroid.utils.ToastUtil.Companion.show

/**
 * @author hujiayi
 */
object ApiRetrofitManager {
    fun init(baseUrl: String) {
        try {
            if ("/" == baseUrl.substring(baseUrl.length - 1)) {
                instance!!.uRL = baseUrl
            } else {
                show("必须以/结尾")
            }
        } catch (e: Exception) {
            show(e.message)
        }
    }
}