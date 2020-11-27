package com.ocse.baseandroid.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Rect
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import java.lang.reflect.Field

class ScreenUtils private constructor() {
    companion object {
        private val sMetricsFields: List<Field>? = null

        /**
         * 获得状态栏的高度
         *
         * @return
         */
        val statusHeight: Int
            get() {
                var statusHeight = -1
                try {
                    val clazz =
                        Class.forName("com.android.internal.R\$dimen")
                    val `object` = clazz.newInstance()
                    val height = clazz.getField("status_bar_height")[`object`].toString().toInt()
                    statusHeight =
                        ObtainApplication.getApp()!!.resources.getDimensionPixelSize(height)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return statusHeight
            }

        private fun getNavBarHeight(resources: Resources): Int {
            val resourceId =
                resources.getIdentifier("navigation_bar_height", "dimen", "android")
            return if (resourceId != 0) {
                resources.getDimensionPixelSize(resourceId)
            } else {
                0
            }
        }

        /**
         * 获得屏幕高度
         *
         * @param
         * @return
         */
        val screenHeight: Int
            get() {
                val wm = ObtainApplication. getApp()!!
                    .getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val outMetrics = DisplayMetrics()
                wm.defaultDisplay.getMetrics(outMetrics)
                return outMetrics.heightPixels
            }

        /**
         * 获得屏幕宽度
         *
         * @param
         * @return
         */
        val screenWidth: Int
            get() {
                val wm = ObtainApplication.getApp()!!
                    .getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val outMetrics = DisplayMetrics()
                wm.defaultDisplay.getMetrics(outMetrics)
                return outMetrics.widthPixels
            }

        /**
         * Value of pt to value of px.
         *
         * @param ptValue The value of pt.
         * @return value of px
         */
        fun dp2Px(ptValue: Float): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                ptValue, ObtainApplication. getApp()!!.resources.displayMetrics
            ).toInt()
        }

        /**
         * Value of px to value of pt.
         *
         * @param pxValue The value of px.
         * @return value of pt
         */
        fun px2dp(pxValue: Float): Int {
            val scale =
                ObtainApplication. getApp()!!.resources.displayMetrics.density
            return (pxValue / scale).toInt()
        }

        /**
         * 获取当前屏幕截图，不包含状态栏
         *
         * @param activity
         * @return
         */
        fun snapShotWithoutStatusBar(activity: Activity): Bitmap? {
            val view = activity.window.decorView
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache()
            val bmp = view.drawingCache
            val frame = Rect()
            activity.window.decorView.getWindowVisibleDisplayFrame(frame)
            val statusBarHeight = frame.top
            val width = screenWidth
            val height = screenHeight
            var bp: Bitmap? = null
            bp = Bitmap.createBitmap(
                bmp, 0, statusBarHeight, width, height
                        - statusBarHeight
            )
            view.destroyDrawingCache()
            return bp
        }
    }

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }
}