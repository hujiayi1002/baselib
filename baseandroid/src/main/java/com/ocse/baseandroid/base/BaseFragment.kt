package com.ocse.baseandroid.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ocse.baseandroid.view.LoadingView

/**
 * BaseFragment，处理ViewModelProvider的初始化
 * */
open class BaseFragment : Fragment() {
    private var viewModelProvider: ViewModelProvider? = null
    private lateinit var loadingView: LoadingView
    private var hash: Int = 0
    private var lastClickTime: Long = 0
    private var spaceTime: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelProvider = getViewModelProvider()
        loadingView = LoadingView(activity)

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

    open fun showLoading() {
        if (!loadingView.isShowing) {
            loadingView.show()
        }

    }

    open fun loadingDismiss() {
        loadingView.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelProvider = null
    }

}