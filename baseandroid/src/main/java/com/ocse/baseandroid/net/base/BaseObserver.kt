package com.ocse.baseandroid.retrofit.base

import android.accounts.AccountsException
import android.accounts.NetworkErrorException
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.ocse.baseandroid.base.BaseApplication
import com.ocse.baseandroid.utils.Logger
import com.ocse.baseandroid.utils.NetworkUtil
import com.ocse.baseandroid.utils.ObtainApplication
import com.ocse.baseandroid.utils.ToastUtil.Companion.show
import com.ocse.baseandroid.view.LoadingView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException

/**
 * @author
 */
abstract class BaseObserver<T>(compositeDisposable: CompositeDisposable) :
    DisposableObserver<T>() {

    private val loading =
        LoadingView.Builder(BaseApplication.activities[BaseApplication.activities.size - 1]).create()

    init {
        compositeDisposable.add(this)
    }

    override fun onStart() {
        super.onStart()
        try {
            loading.show()
        } catch (e: Exception) {
        }
    }

    override fun onNext(t: T) {
        _onNext(t)
        Logger.e( "onNext = " + Gson().toJson(t))
        loading.dismiss()
    }

    override fun onError(e: Throwable) {
        loading.dismiss()
        var reason = e.message
        //网络异常
        if (!NetworkUtil.isConnected(ObtainApplication.getApp())) {
            reason = "暂无网络，请检查网络"
        } else if (e is NetworkErrorException) {
            reason = "网络异常，请检查网络"
            //账户异常
        } else if (e is AccountsException) {
            reason = "账户异常"
            //连接异常--继承于SocketException
        } else if (e is ConnectException) {
            reason = "连接异常"
            //socket异常
        } else if (e is SocketException) {
            reason = "socket异常"
            // http异常
        } else if (e is HttpException) {
            reason = e.message
        } else if (e is JsonSyntaxException
            || e is JsonIOException
            || e is JsonParseException
        ) {
            //数据格式化错误
            reason = "数据格式化错误"
        } else if (e is ClassCastException || e is IllegalStateException) {
            reason = "类型转换错误"
        }
        show(reason)
        Log.e("hu", "onError = " + e.localizedMessage)
        _onError(e)
    }

    override fun onComplete() {
        loading.dismiss()
    }

    /**
     * 获取成功后数据展示
     *
     * @param entity
     * @return
     */
    abstract fun _onNext(entity: T)
    fun _onError(e: Throwable) {}


}