package com.example.administrator.rxandretrofit;

import com.example.administrator.rxandretrofit.bean.Root;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/10/26.
 * https://api.douban.com/v2/book/search?q=小王子&tag=""&start=1&count=3
 */

public interface ApiService {
    @GET("/")
    Call<String> getBaidu();
    @GET("book/search")
    Call<String> getSearchBooks(@Query("q") String name,
                                            @Query("tag") String tag, @Query("start") int start,
                                            @Query("count") int count);    @GET("book/search")
    Call<Root> getSearchBooks2(@Query("q") String name,
                              @Query("tag") String tag, @Query("start") int start,
                              @Query("count") int count);

    /**
     * rxjava + retrofit
     */
    @GET("book/search")
    Observable<Root> getSearchBooks3(@Query("q") String name,
                                     @Query("tag") String tag, @Query("start") int start,
                                     @Query("count") int count);
}
