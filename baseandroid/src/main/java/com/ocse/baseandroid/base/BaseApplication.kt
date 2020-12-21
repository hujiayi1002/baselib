package com.ocse.baseandroid.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.ocse.baseandroid.R
import com.ocse.baseandroid.utils.ToastUtil
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.set
import kotlin.system.exitProcess


open class BaseApplication : Application() {

    companion object {
        var activities = ArrayList<Activity>()
    }

    private var count = 0
    private var isForeground = false
    val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { this }

    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.colorBg, android.R.color.black);//全局设置主题颜色
            ClassicsHeader(context)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator() { context, layout ->
            layout.setPrimaryColorsId(R.color.colorBg, android.R.color.black);//全局设置主题颜色
            ClassicsFooter(context)
        }
    }

    override fun onCreate() {
        super.onCreate()
        // 在调用TBS初始化、创建WebView之前进行如下配置
        val map = HashMap<String, Any>()
        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
        QbSdk.initTbsSettings(map)

        RxJavaPlugins.setErrorHandler {
            it.printStackTrace();//这里处理所有的Rxjava异常
        }
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        val cb = object : QbSdk.PreInitCallback {

            override fun onViewInitFinished(arg0: Boolean) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is $arg0")
            }

            override fun onCoreInitFinished() {
            }
        }
        QbSdk.setDownloadWithoutWifi(true)
        //x5内核初始化接口
        QbSdk.initX5Environment(applicationContext, cb)
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

            }
            override fun onActivityStarted(activity: Activity) {

            }
            override fun onActivityResumed(activity: Activity) {
                activities.add(activity)
                Log.e("TAG", "onActivityCreated: "+activity.localClassName )
                Log.e("TAG", "onActivityCreated: " + activities.size)
                count++
            }
            override fun onActivityPaused(activity: Activity) {

            }
            override fun onActivityStopped(activity: Activity) {
                count--
                activities.remove(activity)
                if (count == 0) {
                    isForeground = false
                    Log.e("TAG", "onActivityDestroyed: " + activities.size)
                    ToastUtil.show("当前APP已经不在前台，请谨慎操作")
                }
            }

            override fun onActivityDestroyed(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

        })

    }

    open fun initX5() {
        // 在调用TBS初始化、创建WebView之前进行如下配置
        val map = HashMap<String, Any>()
        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
        QbSdk.initTbsSettings(map)
    }

    /**
     * 退出应用
     */
    open fun exitApp() {
        // 方式1：android.os.Process.killProcess（）
        android.os.Process.killProcess(android.os.Process.myPid());

        // 方式2：System.exit()
        // System.exit() = Java中结束进程的方法：关闭当前JVM虚拟机
        exitProcess(0);
    }
}