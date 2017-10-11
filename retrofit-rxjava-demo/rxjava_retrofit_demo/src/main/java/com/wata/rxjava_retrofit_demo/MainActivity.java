package com.wata.rxjava_retrofit_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);

        // http://www.kuaidi100.com/query?type=yuantong&postid=11111111111
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.kuaidi100.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    public void click(View view){
        test4();
    }

    public void test4(){
        Observable
                // 创建一个 Obserable
                .create(new ObservableOnSubscribe<Result>() {// 创建 Obserable
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Result> e) throws Exception {
                        Log.d("wata","Observable(create): "+Thread.currentThread().getName());
                        Result result = api.getData("yuantong", "11111111111").execute().body();

                        e.onNext(result);
                    }
                })
                // 接下来的 observable 运行在 io 线程
                .subscribeOn(Schedulers.io())
                // 接下来的 observer 运行在 主线程
                .observeOn(AndroidSchedulers.mainThread())
                // 创建 Observer 并订阅
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Result result) {
                        Log.d("wata","Observer(subscribe): "+Thread.currentThread().getName());
                        tv.setText(result.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.getStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void test3(){
        Observable
                // 创建一个 Obserable
                .create(new ObservableOnSubscribe<Result>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Result> e) throws Exception {
                        Log.d("wata",Thread.currentThread().getName());
                        Result result = api.getData("yuantong", "11111111111").execute().body();

                        e.onNext(result);
                    }
                })
                // 指定接下来的 Obserable 运行在 io线程
                .subscribeOn(Schedulers.io())
                // 创建一个 Observer 对象, 并订阅
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Result result) {
                        Log.d("wata",Thread.currentThread().getName());
                        Log.d("wata", result.toString());

                        // 必须在主线程更新 UI
                        tv.setText(result.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.getStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void test2(){
        ObservableOnSubscribe<Result> observableOnSubscribe = new ObservableOnSubscribe<Result>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Result> e) throws Exception {
                Log.d("wata",Thread.currentThread().getName());

                //android.os.NetworkOnMainThreadException, 主线程不能访问网络
                Result result = api.getData("yuantong", "11111111111").execute().body();

                e.onNext(result);
            }
        };
        // 1. 创建 Observable 对象
        Observable<Result> observable = Observable.create(observableOnSubscribe);

        // 2. 创建 Observer 对象
        Observer<Result> observer = new Observer<Result>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Result result) {
                Log.d("wata",Thread.currentThread().getName());
                Log.d("wata", "---------------------");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        };

        // 3. 订阅
        observable.subscribe(observer);
    }

    public void test(){
        ObservableOnSubscribe<Param> observableOnSubscribe = new ObservableOnSubscribe<Param>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Param> e) throws Exception {
                Param param = new Param("yuantong", "11111111111");
                e.onNext(param);
            }
        };
        // 1. 创建 Observable 对象
        Observable<Param> observable = Observable.create(observableOnSubscribe);

        // 2. 创建 Observer 对象
        Observer<Param> observer = new Observer<Param>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Param param) {
                tv.append(param.toString());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        // 3. 订阅
        observable.subscribe(observer);
    }


}
