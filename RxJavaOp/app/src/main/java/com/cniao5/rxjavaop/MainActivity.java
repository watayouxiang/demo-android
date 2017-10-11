package com.cniao5.rxjavaop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    private Api api;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.189:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        api = retrofit.create(Api.class);

        this.mEditText = (EditText) findViewById(R.id.edit_search);


        RxTextView.textChanges(this.mEditText)
                .debounce(200, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        // 过滤数据

                        Log.d("MainActivity","filter="+charSequence);
                        return charSequence.toString().trim().length()> 0;
                    }
                })

                .switchMap(new Func1<CharSequence, Observable<List<String>>>() {
                    @Override
                    public Observable<List<String>> call(CharSequence charSequence) {


                        Log.d("MainActivity","flatMap=="+charSequence);

                        // search
                         List<String> list = new ArrayList<String>();
                        list.add("abc");
                        list.add("ada");

                        return Observable.just(list);
                    }
                })

//                .flatMap(new Func1<CharSequence, Observable<List<String>>>() {
//                    @Override
//                    public Observable<List<String>> call(CharSequence charSequence) {
//
//                        Log.d("MainActivity","flatMap=="+charSequence);
//
//                        // search
//                         List<String> list = new ArrayList<String>();
//                        list.add("abc");
//                        list.add("ada");
//
//                        return Observable.just(list);
//                    }
//                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {


                        Log.d("MainActivity","subscribe"+strings);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        throwable.printStackTrace();
                    }
                });



    }
}
