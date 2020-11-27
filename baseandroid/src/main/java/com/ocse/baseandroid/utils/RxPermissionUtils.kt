package com.ocse.baseandroid.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.tbruyelle.rxpermissions2.RxPermissions

class RxPermissionUtils {
    @SuppressLint("CheckResult")
    open fun getPermission(mContext:FragmentActivity) {
        val rxPermissions= RxPermissions(mContext)
        rxPermissions?.request(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CONTROL_LOCATION_UPDATES,
            Manifest.permission.RECORD_AUDIO
        )?.subscribe { aBoolean ->
            if (aBoolean) {
            }
        }
    }

    @SuppressLint("CheckResult")
    open fun addPermission(mContext:FragmentActivity,vararg permissions: String) {
        val rxPermissions= RxPermissions(mContext)
        rxPermissions?.request(
            permissions.contentToString()
        )?.subscribe { aBoolean ->
            if (aBoolean) {
            }
        }
    }
}