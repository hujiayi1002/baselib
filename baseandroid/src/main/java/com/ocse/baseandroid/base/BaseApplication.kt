package com.ocse.baseandroid.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.ocse.baseandroid.R
import com.ocse.baseandroid.utils.Logger
import com.ocse.baseandroid.utils.ToastUtil
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import io.reactivex.plugins.RxJavaPlugins
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.set
import kotlin.system.exitProcess


open class BaseApplication : Application() {

    companion object {
        var activities = ArrayList<Activity>()
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { this }
    }

    private var count = 0
    private var isForeground = false

    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.bgColor, android.R.color.black);//全局设置主题颜色
            ClassicsHeader(context)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator() { context, layout ->
            layout.setPrimaryColorsId(R.color.bgColor, android.R.color.black);//全局设置主题颜色
            ClassicsFooter(context)
        }
    }

    override fun onCreate() {
        super.onCreate()

        RxJavaPlugins.setErrorHandler {
            it.printStackTrace();//这里处理所有的Rxjava异常
        }

        QbSdk.setDownloadWithoutWifi(true)
        // 在调用TBS初始化、创建WebView之前进行如下配置
        val map = HashMap<String, Any>()
        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
        QbSdk.initTbsSettings(map)

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {
                activities.add(activity)
                Logger.e("onActivityCreated: " + activity.localClassName)
                Logger.e("onActivityCreated: " + activities.size)
                count++
            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {
                count--
                activities.remove(activity)
                if (count == 0) {
                    isForeground = false
                    Logger.e("onActivityDestroyed: " + activities.size)
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
        QbSdk.setDownloadWithoutWifi(true)
        // 在调用TBS初始化、创建WebView之前进行如下配置
        val map = HashMap<String, Any>()
        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
        QbSdk.initTbsSettings(map)
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
//
//        val cb = object : QbSdk.PreInitCallback {
//
//            override fun onViewInitFinished(arg0: Boolean) {
//                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                Logger.e("app onViewInitFinished is $arg0")
//            }
//
//            override fun onCoreInitFinished() {
//            }
//        }
        //x5内核初始化接口
//        QbSdk.initX5Environment(applicationContext, cb)
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