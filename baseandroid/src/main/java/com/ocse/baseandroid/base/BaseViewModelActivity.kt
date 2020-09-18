package com.ocse.henan.cloudcity.base

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ocse.baseandroid.view.LoadingView

/**
 * BaseActivity，处理ViewModelProvider的初始化
 * */
open abstract class BaseViewModelActivity(getLayoutId: Int) : AppCompatActivity(getLayoutId) {

    private var viewModelProvider: ViewModelProvider? = null
    private  lateinit var loadingView: LoadingView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        viewModelProvider = getViewModelProvider()
    }

    /**
     * 创建ViewModel对象
     * @param clazz
     * @return
     */
    open fun <T : ViewModel> get(clazz: Class<T>): T {
        return viewModelProvider!![clazz]
    }

    /**
     * 初始化ViewModelProvider对象
     * @return
     */
    private fun getViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelProvider = null
    }


  open  fun showLoading() {
        if (!loadingView.isShowing) {
            loadingView.show()
        }

    }

    open  fun loadingDissmiss() {
        loadingView.dismiss()
    }

    open  fun log(message: String) {
        var TAG = "Tag:${localClassName}"
        Log.e(TAG, "hu--$message")
    }
}