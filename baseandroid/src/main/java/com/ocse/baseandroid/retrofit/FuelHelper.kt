package com.ocse.baseandroid.retrofit

import android.util.Log
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Request
import com.ocse.baseandroid.utils.SharePreferenceUtil
import java.util.HashMap

//https://github.com/kittinunf/fuel

object FuelHelper {

    fun initFuel(url:String) {
        //服务器接口地址
        FuelManager.instance.basePath = url
        //超时时间20秒
        FuelManager.instance.timeoutInMillisecond = 15000
        //添加header拦截器
        FuelManager.instance.addRequestInterceptor(tokenInterceptor())
        //添加请求日志拦截器
        FuelManager.instance.addRequestInterceptor(loggingRequestInterceptor())
        //foldRight 是 List 的一个扩展函数 从右往左，对列表中的每一个元素执行 operation 操作，
        // 每个操作的结果是下一次操作的入参，第一次 operation 的初始值是 initial。
        //requestInterceptors.foldRight({r: Request -> r}){f,acc-> f(acc)}
    }


    /**
     * @Description :日志拦截器
     * @Return :
     * @Params :
     */
    private fun <T> loggingRequestInterceptor() =
        { next: (T) -> T ->
            { t: T ->
                Log.e("huError", t.toString())
                next(t)
            }
        }

    private fun addParms(params :HashMap<String, Any>) = { next: (Request) -> Request ->
        { req: Request ->
            val params = HashMap<String, Any>()
            req.header(params)//变量替换
            next(req)
        }
    }
    /**
     * @Description :添加header
     * @Return :
     * @Params :
     */
    private fun tokenInterceptor() = { next: (Request) -> Request ->
        { req: Request ->
            var token = SharePreferenceUtil.getString("token")
            Log.e("token",token.toString())
            val params = HashMap<String, Any>()
            if (token.isNullOrEmpty()) {
                params["csrf-csrf"] = "csrf-csrf"
                params["Content-Type"] = "application/x-www-form-urlencoded"
            } else {
                params["csrf-csrf"] = "csrf-csrf"
                params["Authorization"] = "Bearer  $token"
            }
            req.header(params)//变量替换
            next(req)
        }
    }
}