package com.wata.rxjavaoperation;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by TaoWang on 2017/7/13.
 */

public interface Api {

    @GET("query")
    Call<Result> getData(@Query("type") String my_type, @Query("postid") String my_postid);

    @GET("query")
    Observable<Result> getData_obserable(@Query("type") String my_type, @Query("postid") String my_postid);

    @GET("query")
    Call<Result> getData(@QueryMap() Map<String, String> params);

    @POST("query")
    Call<Result> postData(@Body Param param);

    @POST("query")
    Observable<Result> postData_obserable(@Body Param param);

    @FormUrlEncoded
    @POST("query")
    Call<Result> postData(@Field("type") String my_type, @Field("postid") String my_postid);

}
