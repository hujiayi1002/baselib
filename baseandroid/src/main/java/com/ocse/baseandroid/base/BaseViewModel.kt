package com.ocse.baseandroid.base


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers


/**
 * 基础ViewModel类，管理LiveData
 * */
open class BaseViewModel : ViewModel() {
    open val compositeDisposable by lazy { CompositeDisposable() }
    //异常LiveData
    val errorLiveData = MutableLiveData<Throwable>()
    fun launch(
        block: suspend () -> Unit,
        error: suspend (Throwable) -> Unit,
        complete: suspend () -> Unit
    ) {

    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


}