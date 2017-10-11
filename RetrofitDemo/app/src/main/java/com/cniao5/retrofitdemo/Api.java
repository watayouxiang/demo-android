package com.cniao5.retrofitdemo;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Ivan on 2016/10/7.
 */

public interface Api {


    @POST("login/json")
    Call<BaseResult> login(@Body UserParam param);


    @POST("login/json")
    Observable<BaseResult> loginWithRx(@Body UserParam param);


    @GET("user/{id}")
    Observable<User> getUserInfoWithRx(@Path("id") int user_id);

    @GET("user/info")
    Call<User> getUserInfo(@Query("id") int user_id);


    @GET("user/info")
    Call<User> getUserInfoWithMap(@QueryMap Map<String, String> params);


    @GET("user/{id}")
    Call<User> getUserInfoWithPath(@Path("id") int user_id);


    @POST("user/new")
    Call<BaseResult> saveUser(@Body User user);


    @FormUrlEncoded
    @Headers({"User-Agent: www.cniao5.com", "my_name:cniao5"})
    @POST("user/edit")
    Call<BaseResult> editUser(@Field("id") int user_id, @Field("username") String user_name);


}
