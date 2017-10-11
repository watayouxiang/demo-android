package com.wata.rxjava_demo_imagecachelibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.view.RxView;
import com.wata.rxjava_demo_imagecachelibrary.imgloader.RxImageLoader;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.btn_get);
        mImageView = (ImageView) findViewById(R.id.iv_image);

        test2();
    }

    private void test2(){
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("wata","click");
                RxImageLoader.with(MainActivity.this).load("http://cniao5-imgs.qiniudn.com/o_1b11gepdrdp71lks1ngh1cp0bjf9.jpg").into(mImageView);
            }
        });
    }

    /**
     * 图片缓存框架：
     * 首先取内存数据，如果没有取磁盘数据，如果还没有才请求网络获取数据
     */
    private void test(){
        final Observable<String> memoryObserable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                e.onNext("memory");
                e.onNext("");
                e.onComplete();
            }
        });

        final Observable<String> diskObserable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                e.onNext("disk");
                e.onNext("");
                e.onComplete();
            }
        });

        final Observable<String> netWorkObserable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("network");
                e.onComplete();
            }
        });

        RxView
                // 创建一个 Observable 并发送一个点击事件
                .clicks(mButton)
                // 创建一个 Observer
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        //--------------------------
                        Observable
                                // 这个操作符作用类似于merge，用于将多个Observable的数据源合并到一起
                                .concat(memoryObserable,diskObserable,netWorkObserable)
                                // 过滤掉为空的数据源
                                .filter(new Predicate<String>() {
                                    @Override
                                    public boolean test(String s) throws Exception {
                                        return !TextUtils.isEmpty(s);
                                    }
                                })
                                // 创建一个 Observer
                                .subscribe(new Consumer<String>() {
                                    @Override
                                    public void accept(String s) throws Exception {
                                        Log.d("wata","get Data From: "+s);
                                    }
                                });
                        //--------------------------
                    }
                });
    }
}
