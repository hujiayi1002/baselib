package com.ocse.androidbaselib

import com.ocse.baseandroid.base.BaseApplication
import com.ocse.baseandroid.net.retrofit.ApiRetrofitManager
import com.ocse.baseandroid.net.fuel.FuelHelper.initFuel

class App:BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        initFuel("")
        ApiRetrofitManager.init("http://10.81.108.88:8090/")

    }
}