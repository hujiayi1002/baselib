package com.ocse.baseandroid.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import com.ocse.baseandroid.utils.ObtainApplication
import com.tencent.smtt.export.external.interfaces.SslError
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

/**
 * @author 11729
 */
class X5WebView @SuppressLint("SetJavaScriptEnabled") constructor(
    arg0: Context?,
    arg1: AttributeSet?
) : WebView(arg0, arg1) {
    private val client: WebViewClient =
        object : WebViewClient() {
            /**
             * 防止加载网页时调起系统浏览器
             */
            override fun shouldOverrideUrlLoading(
                view: WebView,
                url: WebResourceRequest
            ): Boolean {
                //网页在webView中打开
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) { //安卓5.0的加载方法
                    view.loadUrl(url.toString())
                } else { //5.0以上的加载方法
                    view.loadUrl(url.url.toString())
                }
                return true
            }

            override fun onReceivedSslError(
                webView: WebView,
                sslErrorHandler: SslErrorHandler,
                sslError: SslError
            ) {
                sslErrorHandler.proceed()
                super.onReceivedSslError(webView, sslErrorHandler, sslError)
            }
        }

    private fun initWebViewSettings() {
        val webSetting = this.settings
        //禁用滑动按钮
        if (this.x5WebViewExtension != null) {
            this.x5WebViewExtension.isHorizontalScrollBarEnabled = false //水平不显示滚动按钮
            this.x5WebViewExtension.isVerticalScrollBarEnabled = false //垂直不显示滚动按钮
        }
        webSetting.javaScriptEnabled = true
        webSetting.javaScriptCanOpenWindowsAutomatically = true
        webSetting.allowFileAccess = true
        //        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true)
        webSetting.builtInZoomControls = true
        webSetting.useWideViewPort = true
        webSetting.setSupportMultipleWindows(true)
        webSetting.loadWithOverviewMode = true
        webSetting.setAppCacheEnabled(true)
        webSetting.defaultTextEncodingName = "utf-8"
        // webSetting.setDatabaseEnabled(true);
        webSetting.domStorageEnabled = true
        webSetting.loadsImagesAutomatically = true
        webSetting.setGeolocationEnabled(true)
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE)
        val appCachePath: String = ObtainApplication.getApp().cacheDir.absolutePath
        webSetting.setAppCachePath(appCachePath)
        webSetting.allowFileAccess = true
        webSetting.setAppCacheEnabled(true)
        webSetting.userAgentString = "User-Agent:Android"
        webSetting.pluginState = WebSettings.PluginState.ON_DEMAND
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.cacheMode = WebSettings.LOAD_NO_CACHE
        // 清除缓存和记录
        clearCache(true)
        clearHistory()
        //this.addJavascriptInterface(new JSScript(), "android");

        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        // settings 的设计
    }

    init {
        this.webViewClient = client
        initWebViewSettings()
        this.view.isClickable = true
    }
}