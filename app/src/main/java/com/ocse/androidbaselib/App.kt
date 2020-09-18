package com.ocse.androidbaselib

import android.app.Application
import com.ocse.baseandroid.base.BaseApplication
import com.ocse.baseandroid.retrofit.ApiRetrofitManager
import com.ocse.baseandroid.retrofit.FuelHelper.initFuel

class App:BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        initFuel("")
        ApiRetrofitManager.init("http://10.81.108.65:8090/")
}
}