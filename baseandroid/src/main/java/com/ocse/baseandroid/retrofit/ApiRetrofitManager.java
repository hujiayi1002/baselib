package com.ocse.baseandroid.retrofit;

import com.ocse.baseandroid.retrofit.base.BaseRetrofit;
import com.ocse.baseandroid.utils.ToastUtil;

public class ApiRetrofitManager {
    public static void init(String baseUrl) {
        if ("/".equals(baseUrl.substring(baseUrl.length()-1))){
            BaseRetrofit.getInstance().setURL(baseUrl);
        }else {
            ToastUtil.Companion.show("必须以/结尾");
        }

    }
}
