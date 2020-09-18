package com.ocse.baseandroid.retrofit.base;


import android.util.Log;

import androidx.annotation.NonNull;

import com.ocse.baseandroid.utils.SharePerferenceUtil;
import com.ocse.baseandroid.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author hujiayi
 */
public class BaseRetrofit {


    private String URL;
    /**
     * 统一header
     */
    private Map<String, Object> mHeaderMap = new HashMap<>();
    private static BaseRetrofit baseRetrofit;
    private volatile static Retrofit retrofit;
    public static OkHttpClient.Builder clientBuilder;


    public Retrofit setRetrofit() throws Exception {
        if (null == getURL() || getURL().isEmpty()) {
            throw new Exception("请先设置BaseUrl");
        }

        if (retrofit == null) {
            clientBuilder.retryOnConnectionFailure(true)
                    .addInterceptor(mHeaderInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    //基地址
                    .baseUrl(URL)
                    .client(clientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public Retrofit getRetrofit() {
        try {
            setRetrofit();
        } catch (Exception e) {
            ToastUtil.Companion.show(e.getMessage());
        }
        return retrofit;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }

    /**
     * 添加统一的请求头
     *
     * @param map
     * @return
     */
    public BaseRetrofit addHeader(final Map<String, Object> map) {
        if (map != null && !map.isEmpty()) {
            String token = SharePerferenceUtil.INSTANCE.getString("token");
            if (token == null || "".equals(token)) {
                map.put("csrf-csrf", "csrf-csrf");
                map.put("Content-Type", "pplication/x-www-form-urlencoded");
            }
            mHeaderMap.putAll(map);
        }
        return this;
    }

    public static BaseRetrofit getInstance() {
        clientBuilder = new OkHttpClient.Builder();

        if (baseRetrofit == null) {
            synchronized (BaseRetrofit.class) {
                if (baseRetrofit == null) {
                    baseRetrofit = new BaseRetrofit();
                }
            }
        }
        return baseRetrofit;
    }

    /**
     * 对外暴露 OkHttpClient,方便自定义
     *
     * @return
     */
    public static OkHttpClient.Builder getOkHttpClientBuilder() {
        return clientBuilder;
    }

    /**
     * 创建Service
     *
     * @param apiService
     * @param <T>
     * @return
     */
    public <T> T createService(Class<T> apiService) {

        return getRetrofit().create(apiService);
    }


    /**
     * header拦截器
     */
    //最大重试次数
    public int maxRetry;
    //假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）
    private int retryNum = 0;
    private Interceptor mHeaderInterceptor = chain -> {
        Request.Builder request = chain.request().newBuilder();
        if (mHeaderMap.size() > 0) {
            for (Map.Entry<String, Object> entry : mHeaderMap.entrySet()) {
                request.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
//            默认重试一次，若需要重试N次，则要实现以下代码。
//            Response response = chain.proceed(chain.request());
//            Log.i("Retry", "num:" + retryNum);
//            while (!response.isSuccessful() && retryNum < maxRetry) {
//                retryNum++;
//                Log.i("Retry", "num:" + retryNum);
//                response = chain.proceed(chain.request());
//            }
        return chain.proceed(request.build());
    };
    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            //打印retrofit日志
            Log.e("hu", "retrofitBack = " + message);
        }
    }).setLevel(HttpLoggingInterceptor.Level.BODY);

    /**
     * 线程切换
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> switchSchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return switchSchedulers(upstream);
            }
        };
    }

    /**
     * 线程切换
     *
     * @param observable
     * @param <T>
     * @return
     */
    public static <T> Observable<T> switchSchedulers(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
