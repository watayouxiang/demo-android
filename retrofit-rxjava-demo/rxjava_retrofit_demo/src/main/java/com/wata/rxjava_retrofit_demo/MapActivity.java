package com.wata.rxjava_retrofit_demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by TaoWang on 2017/7/14.
 */

public class MapActivity extends AppCompatActivity {

    private Button mButton;
    private TextView mTextView;
    private Api api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mTextView = (TextView) findViewById(R.id.tv);
        mButton = (Button) findViewById(R.id.btn);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_flatMap();
            }
        });

        // http://www.kuaidi100.com/query?type=yuantong&postid=11111111111
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.kuaidi100.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    private Param getParam(){
        return new Param("yuantong","11111111111");
    }

    /**
     * 登录后获取用户信息／网络请求嵌套网络请求
     */
    public void click_flatMap(){
        Toast.makeText(getApplicationContext(),"click_flatMap",Toast.LENGTH_SHORT).show();

        //FlatMap: 返回一个 Observable
        Observable.just(getParam()).flatMap(new Function<Param, ObservableSource<Result>>() {
            @Override
            public ObservableSource<Result> apply(@NonNull Param param) throws Exception {
                Log.d("wata", "1 = "+Thread.currentThread().getName());
                // retrofit 和 rxjava 结合使用
                return api.postData_obserable(param);
            }
        }).flatMap(new Function<Result, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Result result) throws Exception {
                Log.d("wata", "2 = "+Thread.currentThread().getName());
                String string = result.status.equals("200")?result.data.toString():result.message;
                return Observable.just(string);
            }
        }).subscribeOn(Schedulers.io())// 把Obseravle切换到 io线程
                .observeOn(AndroidSchedulers.mainThread())// 把Observer切换到 主线程
                .subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.d("wata", "3 = "+Thread.currentThread().getName());
                mTextView.setText(s);
            }
        });
    }

    public void click_map(){
        // map操作符：把 Integer 转成 String
        Observable.just(1).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return integer + "";
            }
        });
    }
}
