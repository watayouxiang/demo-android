package com.cniao5.rxjavaop;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Ivan on 2016/11/14.
 */

public interface Api {




    @GET("courses")
    Observable<List<Course>> getCourses();


}
