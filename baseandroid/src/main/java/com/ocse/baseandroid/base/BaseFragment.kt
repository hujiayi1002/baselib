package com.ocse.henan.cloudcity.base

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
/**
 * BaseFragment，处理ViewModelProvider的初始化
 * */
class BaseFragment:Fragment(){
    private  var viewModelProvider: ViewModelProvider?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelProvider = getViewModelProvider();

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
    private fun getViewModelProvider():ViewModelProvider{
        return ViewModelProvider(this)
    }
    override fun onDestroy() {
        super.onDestroy()
        viewModelProvider = null
    }

}