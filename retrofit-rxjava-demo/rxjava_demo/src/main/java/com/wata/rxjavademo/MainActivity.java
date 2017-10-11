package com.wata.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
    }

    public void click(View view){
        Observable<String> observable = getObservable();
        Observer<String> observer = getObserver();
        observable.subscribe(observer);// 订阅被观察者对象

        Observable<String> observable2 = getObservable2();
        getObserver_subscribe(observable2);

        Observable<String> observable3 = getObservable3();
        getObserver_subscribe(observable3);

        Observable<String> observable4 = getObservable4();
        getObserver_subscribe(observable4);

    }

    /**
     * 创建观察者 & 订阅被观察者
     * @param observable
     */
    public void getObserver_subscribe(Observable<String> observable){
        observable.subscribe(new Consumer<String>() {// 相当于onNext方法
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.d("MainActivity","onNext");

                tv.append(s);
                tv.append("/");
            }
        }, new Consumer<Throwable>() {// 相当于onError方法
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.d("MainActivity","onError");
            }
        }, new Action() {// 相当于onComplete方法
            @Override
            public void run() throws Exception {
                Log.d("MainActivity","onComplete");
            }
        });
    }

    /**
     * 创建观察者
     * @return
     */
    public Observer<String> getObserver(){
        Observer<String> observer = new Observer<String>() {
            Disposable dd = null;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d("MainActivity", "onSubscribe");
                dd = d;
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d("MainActivity", "onNext");
                if (s.equals("sleep")) {
                    if (!dd.isDisposed()) {// 判读是否订阅
                        dd.dispose();// 取消订阅
                        Log.d("MainActivity", s + " 取消订阅");
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("MainActivity", "onError");
            }

            @Override
            public void onComplete() {
                Log.d("MainActivity", "onComplete");
            }
        };
        return observer;
    }

    /**
     * 创建被观察者：方式四
     * @return
     */
    public Observable<String> getObservable4(){
        Observable<String> observable = Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "goHome";
            }
        });
        return observable;
    }

    /**
     * 创建被观察者：方式三
     * @return
     */
    public Observable<String> getObservable3(){
        Observable<String> observable = Observable.fromArray("goHome","eat","sleep");
        return observable;
    }

    /**
     * 创建被观察者：方式二
     * @return
     */
    public Observable<String> getObservable2(){
        Observable<String> observable = Observable.just("goHome","eat","sleep");
        return observable;
    }

    /**
     * 创建被观察者：方式一
     * @return
     */
    public Observable<String> getObservable(){
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e)/* 事件发射器 */ throws Exception {
                e.onNext("goHome");
                e.onNext("eat");
                e.onNext("sleep");
                e.onComplete();
            }
        });
        return observable;
    }


}
