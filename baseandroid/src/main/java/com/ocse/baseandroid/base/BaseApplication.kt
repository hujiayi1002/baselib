package com.ocse.baseandroid.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.Process.killProcess
import android.util.Log
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.set


open class BaseApplication : Application() {

    companion object{
        var activities = ArrayList<Activity>()
    }
    private var count = 0
    private var isForeground = false
    val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { this }
    override fun onCreate() {
        super.onCreate()
        // 在调用TBS初始化、创建WebView之前进行如下配置
        val map = HashMap<String, Any>()
        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
        QbSdk.initTbsSettings(map)
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityDestroyed(activity: Activity) {
                count--
                activities.remove(activity)
                if (count == 0) {
                    isForeground = true
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

                activities.add(activity)

                Log.e("TAG", "onActivityCreated: "+activity.localClassName )
                Log.e("TAG", "onActivityCreated: "+activities.size )

                count++
            }

            override fun onActivityResumed(activity: Activity) {
            }
        })

    }

    /**
     * 退出应用
     */
    open fun exitApp() {
        // 方式1：android.os.Process.killProcess（）
        android.os.Process.killProcess(android.os.Process.myPid());

        // 方式2：System.exit()
        // System.exit() = Java中结束进程的方法：关闭当前JVM虚拟机
        System.exit(0);
    }
}