package com.wata.retrofitdemo;

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

/**
 * Created by TaoWang on 2017/7/13.
 */

public interface Api {

    //-------------------GET-------------------
    @GET("query")
    Call<Result> getData();

    @GET("query")
    Call<Result> getData2(@Query("type") String my_type);// ? 拼接用 @Query

    @GET("query/{type}")
    Call<Result> getData3(@Path("type") String my_type);// 字段替换用 @Path

    @GET("query")
    Call<Result> getData4(@QueryMap() Map<String, String> params);// ? 拼接多个参数用 @QueryMap

    //-------------------POST-------------------
    @POST("query")
    Call<Result> postData(@Body Param param);// 对象(对象会自动转String)当参数

    @FormUrlEncoded// 表单提交
    @POST("query")
    Call<Result> postData2(@Field("type") String my_type, @Field("postid") String my_postid);// 参数传递用 @Field

    @Headers({// 更改请求头
        "Accept: application/vnd.github.v3.full+json",
        "User-Agent: Retrofit-Sample-App",
        "Cache-Control: max-age=640000"
    })
    @POST("query")
    Call<Result> postData3(@Field("type") String my_type, @Field("postid") String my_postid);// 参数传递用 @Field
}
