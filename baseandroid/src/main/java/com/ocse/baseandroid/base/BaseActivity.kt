package com.ocse.baseandroid.base

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.ocse.henan.cloudcity.base.BaseViewModelActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer


abstract class BaseActivity<V : ViewDataBinding>(getLayoutId: Int) :
    BaseViewModelActivity(getLayoutId) {
    var hash: Int = 0
    private var lastClickTime: Long = 0
    private var spaceTime: Long = 2000
    open val layout = getLayoutId
    open var dataBinding: V? = null
    private var mCompositeDisposable = CompositeDisposable()
    private var rxPermissions: RxPermissions? = null

    open val mContext by lazy { this@BaseActivity }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, layout)
        rxPermissions = RxPermissions(this)
    }

    abstract fun initView()
    abstract fun initData()


    infix fun View.singleClick(clickAction: () -> Unit) {
        this.setOnClickListener {
            if (this.hashCode() != hash) {
                hash = this.hashCode()
                lastClickTime = System.currentTimeMillis()
                clickAction()
            } else {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastClickTime > spaceTime) {
                    lastClickTime = System.currentTimeMillis()
                    clickAction()
                }
            }
        }
    }


    @SuppressLint("CheckResult")
    open fun getPermission() {
        rxPermissions?.request(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CONTROL_LOCATION_UPDATES,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )?.subscribe(Consumer<Boolean> { aBoolean ->
            if (aBoolean) {
            }
        })
    }

    open fun addPermission(permissions: String) {
        rxPermissions?.request(
            permissions
        )?.subscribe(Consumer<Boolean> { aBoolean ->
            if (aBoolean) {
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy();
        if (dataBinding != null) {
            dataBinding?.unbind()
            dataBinding = null
            mCompositeDisposable.clear()
            rxPermissions = null
        }
    }
}