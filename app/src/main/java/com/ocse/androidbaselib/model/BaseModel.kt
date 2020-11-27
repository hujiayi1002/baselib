package com.ocse.androidbaselib.model

import com.ocse.androidbaselib.bean.UserBean
import com.ocse.androidbaselib.retrofit.ApiRetrofit.Companion.instance
import com.ocse.baseandroid.base.BaseViewModel
import com.ocse.baseandroid.retrofit.base.BaseObserver
import com.ocse.baseandroid.utils.SharePerferenceUtil.saveString

/**
 * @author hujiayi
 */
class BaseModel : BaseViewModel<Any>() {
    var userMutableLiveData = get(UserBean::class.java)

    val user: Unit
        get() {
            instance.login("admin", "123456")
                .subscribe(object : BaseObserver<UserBean?>(compositeDisposable) {
                    override fun _onNext(entity: UserBean?) {
                        userMutableLiveData.postValue(entity)
                    }
//                    override fun _onNext(entity: UserBean) {
//                        userMutableLiveData.postValue(entity)
//                        saveString("token", entity.access_token)
//                        //                getVersion();
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