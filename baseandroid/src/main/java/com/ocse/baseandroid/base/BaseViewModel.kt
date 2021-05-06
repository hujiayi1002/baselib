package com.ocse.baseandroid.base


import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


/**
 * 基础ViewModel类，管理LiveData
 * */
open class BaseViewModel : ViewModel() {
    open val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}