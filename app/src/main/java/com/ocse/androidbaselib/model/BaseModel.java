package com.ocse.androidbaselib.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.ocse.androidbaselib.bean.UserBean;
import com.ocse.androidbaselib.bean.VersionBean;
import com.ocse.androidbaselib.retrofit.ApiRetrofit;
import com.ocse.baseandroid.base.BaseViewModel;
import com.ocse.baseandroid.retrofit.base.BaseObserver;
import com.ocse.baseandroid.utils.InstallApkUtils;
import com.ocse.baseandroid.utils.SharePerferenceUtil;


/**
 * @author hujiayi
 */
public class BaseModel extends BaseViewModel {

    public MutableLiveData<UserBean> userMutableLiveData = get(UserBean.class);

    public void getUser() {
        ApiRetrofit.Companion.getInstacne().login("admin", "123456").subscribe(new BaseObserver<UserBean>(getCompositeDisposable()) {
            @Override
            public void _onNext(@NonNull UserBean entity) {
                userMutableLiveData.postValue(entity);
                SharePerferenceUtil.INSTANCE.saveString("token",entity.getAccess_token());
//                getVersion();
            }
        });

    }
//    public void getVersion() {
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
