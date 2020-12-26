package com.ocse.baseandroid.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.ConcurrentHashMap


/**
 * 基础ViewModel类，管理LiveData
 * */
open class BaseViewModel<T> : ViewModel() {
    private var maps: MutableMap<String, MutableLiveData<T>> = ConcurrentHashMap()
    val compositeDisposable by lazy { CompositeDisposable() }

    /**
     * 通过指定的数据实体类获取对应的MutableLiveData类
     * @param clazz
     * @param <T>
     * @return
     */


     fun get(clazz: T): MutableLiveData<T> {
        return get(null, clazz)
    }

    /**
     *  通过指定的key或者数据实体类获取对应的MutableLiveData类
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */

    fun get(key: String?, clazz: T): MutableLiveData<T> {
        val keyName = if (key.isNullOrEmpty()) {
            clazz.toString()
        } else {
            key
        }
        var mutableLiveData = maps[keyName]
        //1.判断集合是否已经存在livedata对象
        if (mutableLiveData != null) {
            return mutableLiveData
        }
        //2.如果集合中没有对应实体类的Livedata对象，就创建并添加至集合中
        mutableLiveData = MutableLiveData()
        maps[keyName] = mutableLiveData
        return mutableLiveData
    }

    /**
     * 在对应的FragmentActivity销毁之后调用
     */
    override fun onCleared() {
        super.onCleared()
        maps.clear()
        compositeDisposable.clear()
    }
}