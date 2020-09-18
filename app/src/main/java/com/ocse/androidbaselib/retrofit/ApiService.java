package com.ocse.androidbaselib.retrofit;


import com.ocse.androidbaselib.bean.UserBean;
import com.ocse.androidbaselib.bean.VersionBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * @author hujiayi
 */
public interface ApiService {
    /**
     * 登录
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("oauth/token")
    Observable<UserBean> login(@FieldMap Map<String, Object> map);


    /**
     * 登录
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/getAppVersion")
    Observable<VersionBean> getAppVersion(@FieldMap Map<String, Object> map);
}
