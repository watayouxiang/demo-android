package com.cniao5.rxjavaop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class Example3Activity extends AppCompatActivity {

    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example3);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.189:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        api = retrofit.create(Api.class);
    }



    public void click(View view) {


        
        Observable.merge(getDatasFromLocal(),getDatasFromNetWork())
                .subscribe(new Subscriber<List<Course>>() {
            @Override
            public void onCompleted() {
                
                Log.d("Example3Activity","onCompleted");
            }

            @Override
            public void onError(Throwable e) {

                e.printStackTrace();
            }

            @Override
            public void onNext(List<Course> courses) {

                
                for (Course c : courses){
                    Log.d("Example3Activity","coursename="+c.getName());
                }
                
            }
        });
        

    }





    private  Observable<List<Course>> getDatasFromLocal(){



        List<Course> list = new ArrayList<>();
        list.add(new Course("菜鸟商城"));
        list.add(new Course("菜鸟新闻"));

        return  Observable.just(list);


    }


    private  Observable<List<Course>> getDatasFromNetWork(){

//        return  api.getCourses().subscribeOn(Schedulers.io());


        List<Course> list = new ArrayList<>();
        list.add(new Course("菜鸟直播"));
        list.add(new Course("菜鸟手机助手"));

        return  Observable.just(list);


    }





}
