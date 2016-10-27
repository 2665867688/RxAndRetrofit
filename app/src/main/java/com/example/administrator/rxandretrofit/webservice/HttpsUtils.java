package com.example.administrator.rxandretrofit.webservice;

import com.example.administrator.rxandretrofit.constant.Const;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求封装
 * Created by shimy on 2016/10/26.
 * 数据请求，文件上传，文件下载
 */

public class HttpsUtils {
    private static HttpsUtils httpsUtils;
    private Retrofit retrofit;
    private Retrofit retrofitGson;

    /**
     * 数据请求
     */
    public static synchronized HttpsUtils getInstance() {
        if (httpsUtils == null) {
            httpsUtils = new HttpsUtils();
        }
        return httpsUtils;
    }

    public HttpsUtils() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Const.sServiceUrl)
                .addConverterFactory(new Converter.Factory() {
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return new Converter<ResponseBody, String>() {
                            @Override
                            public String convert(ResponseBody value) throws IOException {
                                return value.string();
                            }
                        };
                    }
                })
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        retrofitGson = new Retrofit.Builder()
                .baseUrl(Const.sServiceUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public synchronized ApiService createService() {
        ApiService ApiService = retrofit.create(ApiService.class);
        return ApiService;
    }

    public synchronized ApiService createServiceGson() {
        ApiService ApiServiceGson = retrofitGson.create(ApiService.class);
        return ApiServiceGson;
    }
}
