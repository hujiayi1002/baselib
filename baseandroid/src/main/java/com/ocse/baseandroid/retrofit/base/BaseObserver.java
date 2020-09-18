package com.ocse.baseandroid.retrofit.base;

import android.accounts.AccountsException;
import android.accounts.NetworkErrorException;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.ocse.baseandroid.utils.NetworkUtil;
import com.ocse.baseandroid.utils.ObtainApplication;
import com.ocse.baseandroid.utils.ToastUtil;

import java.net.ConnectException;
import java.net.SocketException;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * @author
 */
public abstract class BaseObserver<T> extends DisposableObserver<T> {

    public BaseObserver(CompositeDisposable compositeDisposable) {
        compositeDisposable.add(this);
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        String reason = e.getMessage();
        //网络异常
        if (!NetworkUtil.isConnected(ObtainApplication.getApp())) {
            reason = "暂无网络，请检查网络";
        } else if (e instanceof NetworkErrorException) {
            reason = "网络异常，请检查网络";
            //账户异常
        } else if (e instanceof AccountsException) {
            reason = "账户异常";
            //连接异常--继承于SocketException
        } else if (e instanceof ConnectException) {
            reason = "连接异常";
            //socket异常
        } else if (e instanceof SocketException) {
            reason = "socket异常";
            // http异常
        } else if (e instanceof HttpException) {
            reason = e.getMessage();
        } else if (e instanceof JsonSyntaxException
                || e instanceof JsonIOException
                || e instanceof JsonParseException) {
            //数据格式化错误
            reason = "数据格式化错误";
        } else if (e instanceof ClassCastException||e instanceof IllegalStateException) {
            reason = "类型转换错误";
        }
        ToastUtil.Companion.show(reason);
        Log.e("TAG", "Observer--Error--" + e.getMessage());
        _onError(e);
    }

    @Override
    public void onComplete() {
        //ToDo Something
    }

    /**
     * 获取成功后数据展示
     *
     * @param entity
     * @return
     */
    public abstract void _onNext(@NonNull T entity);

    public void _onError(@NonNull Throwable e) {
    }
}
