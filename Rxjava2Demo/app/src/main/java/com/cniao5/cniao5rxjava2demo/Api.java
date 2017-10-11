package com.cniao5.cniao5rxjava2demo;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Ivan on 2016/11/14.
 */

public interface Api {


    @GET("user/{id}")
    Call<User> getUserInfoWithPath(@Path("id") int user_id);


//    @POST("login/json")
//    Call<BaseResult> login(@Body UserParam param);



    @POST("login/json")
    Observable<BaseResult> login(@Body UserParam param);

}
