package com.ocse.baseandroid.utils

object DensityUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dp2px(dpValue: Float): Int {
        val scale =
            ObtainApplication.getApp()!!.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dp(pxValue: Float): Int {
        val scale =
            ObtainApplication.getApp()!!.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }
}