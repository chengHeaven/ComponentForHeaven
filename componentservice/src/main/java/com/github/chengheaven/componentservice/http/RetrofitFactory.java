package com.github.chengheaven.componentservice.http;

import com.github.chengheaven.componentservice.customer.WebViewCookie;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    private static ApiService sInstance = null;

    private static OkHttpClient sOkHttpClient = getOkHttpClient();

    public static ApiService getInstance() {
        if (sInstance == null) {
            synchronized (RetrofitFactory.class) {
                if (sInstance == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(ApiService.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(sOkHttpClient)
                            .build();

                    sInstance = retrofit.create(ApiService.class);
                }
            }
        }
        return sInstance;
    }

    private static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient().newBuilder()
                .addInterceptor(logging)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .cookieJar(new WebViewCookie())
                .build();
    }
}
