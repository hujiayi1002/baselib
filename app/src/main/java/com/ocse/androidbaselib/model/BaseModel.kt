package com.ocse.androidbaselib.model

import androidx.lifecycle.MutableLiveData
import com.ocse.androidbaselib.bean.UserBean
import com.ocse.androidbaselib.retrofit.ApiRetrofit.Companion.instance
import com.ocse.baseandroid.base.BaseViewModel
import com.ocse.baseandroid.net.base.BaseObserver

/**
 * @author hujiayi
 */
class BaseModel : BaseViewModel() {
    val userMutableLiveData by lazy { MutableLiveData<UserBean>() }
    val ss by lazy { MutableLiveData<UserBean>() }

    fun user() {
       instance.login("admin", "123456")
            .subscribe(object : BaseObserver<UserBean>(compositeDisposable) {
                override fun onError(e: Throwable) {
                    super.onError(e)
                    userMutableLiveData.postValue(null)
                }

                override fun _onNext(entity: UserBean) {
                    userMutableLiveData.postValue(entity)
                }
//                    override fun _onNext(entity: UserBean) {
//                        userMutableLiveData.postValue(entity)
//                        saveString("token", entity.access_token)
//                        //getVersion();
//                    }

            })
    }

    fun ss() {
        instance.login("admin", "123456")
            .subscribe(object : BaseObserver<UserBean>(compositeDisposable) {
                override fun onError(e: Throwable) {
                    super.onError(e)
                    ss.postValue(null)
                }

                override fun _onNext(entity: UserBean) {
                    ss.postValue(entity)
                }
//                    override fun _onNext(entity: UserBean) {
//                        userMutableLiveData.postValue(entity)
//                        saveString("token", entity.access_token)
//                        //getVersion();
//                    }
            })

    }
    //        ApiRetrofit.Companion.getInstacne().getversion().subscribe(new BaseObserver<VersionBean>(getCompositeDisposable()) {
    //            @Override
    //            public void _onNext(@NonNull VersionBean entity) {
    //                Log.e("hu",new Gson().toJson(entity));
    //                InstallApkUtils.Companion.checkVersion("http://10.81.108.65:8090/mobile/downloadFj?path="+entity.getData().getLJ(),"1.apk",false);
    //            }
    //        });
    //
    //    }

}