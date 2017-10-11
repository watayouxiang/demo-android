package com.wata.rxjavaoperation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Example0Activity extends AppCompatActivity {

    private EditText mEditText;
    private TextView mTextView;
    private Button mButton;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example0);
        mEditText = (EditText) findViewById(R.id.et);
        mTextView = (TextView) findViewById(R.id.tv);
        mButton = (Button) findViewById(R.id.btn);

        // http://www.kuaidi100.com/query?type=yuantong&postid=11111111111
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.kuaidi100.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        api = retrofit.create(Api.class);

        //test();
        //test3();
        //test4();
        test5();
    }

    /**
     * 发送验证码倒计时
     */
    private void test5(){
        mButton.setText("发送验证码");
        this.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //------------------------------
                final int count = 10;
                Observable
                        // param0：没有延迟
                        // param1：每一秒钟执行一次
                        // param2：单位是秒
                        .interval(0,1,TimeUnit.SECONDS)
                        // 执行 60 秒后停止
                        .take(count + 1)
                        // map操作符用于转换：将Long转成Long
                        // 原本是 0,1,2,...,59
                        // 现在转成 60,59,...,1
                        .map(new Function<Long, Long>() {
                            @Override
                            public Long apply(Long aLong) throws Exception {
                                return count-aLong;
                            }
                        })
                        // 把下面的 Observer 放到UI线程执行
                        .observeOn(AndroidSchedulers.mainThread())
                        // 在执行倒计时的时候，按钮不可点击
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                mButton.setEnabled(false);
                            }
                        })
                        // 创建一个Observer，并订阅
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                Log.d("wata", "onNext:" + aLong);
                                mButton.setText("剩余：" + aLong + "秒");
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                throwable.printStackTrace();
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {
                                mButton.setText("重新倒计时");
                                mButton.setEnabled(true);
                            }
                        }, new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {

                            }
                        });
                //------------------------------
            }
        });
    }

    /**
     * 合并网络数据和本地数据
     */
    private void test4(){
        this.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable
                        // 合并两个Obserable中的数据(数据可能交错的), Concat操作符可以让数据按顺序输出
                        .merge(getDateFromLocal(),getDateFromNetwork())
                        // 合并的数据会返回在Observer
                        .subscribe(new Consumer<Result>() {
                            @Override
                            public void accept(Result result) throws Exception {
                                Log.d("wata",result.message);
                            }
                        });
            }
        });

    }

    private Observable<Result> getDateFromNetwork(){
        return api.getData_obserable("yuantong","11111111111").subscribeOn(Schedulers.io());
    }

    private Observable<Result> getDateFromLocal(){
        return Observable.just(new Result("我是本地数据"));
    }

    /**
     * 防止按钮重复点击
     */
    private void test3(){
        RxView.clicks(this.mButton)
                // throttleFirst操作符：一秒钟只能点击一次
                // 一秒钟内有多次点击事件，但只会取其中的一次
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
                        Log.d("wata","点到了：" + System.currentTimeMillis());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 关键词搜索2
     */
    private void test(){
        RxTextView
                // 得到一个 obserable 对象
                .textChanges(this.mEditText)
                // 500 毫秒只能发送一次数据
                .debounce(500, TimeUnit.MILLISECONDS)
                // 接下来的 obserable 运行在 io线程
                .subscribeOn(Schedulers.io())
                // 用 filter 过滤为空的数据
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(CharSequence charSequence) throws Exception {
                        Log.d("wata", "Obserable(filter): "+Thread.currentThread().getName());
                        return charSequence.toString().trim().length() > 0;
                    }
                })
                // 接下来的 obserable 运行在 主线程
                .subscribeOn(AndroidSchedulers.mainThread())
                // switchMap： 当一个请求发出出去了但是结果没有返回来，这时候又来了一个新的请求，
                // 那么switchMap会把上一次请求清空，以新的请求为准。
                // 返回值是一个 Obserable 对象
                .switchMap(new Function<CharSequence, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(CharSequence charSequence) throws Exception {
                        Log.d("wata", "Obserable(switchMap): "+Thread.currentThread().getName());

                        List<String> list = new ArrayList<String>();
                        list.add("a");
                        list.add("account");
                        list.add("available");
                        list.add("appreciate");
                        list.add("assume");

                        return Observable.just(list);
                    }
                })
                // 接下来的 observer 运行在主线程
                .observeOn(AndroidSchedulers.mainThread())
                // 创建一个 observer 对象，并订阅
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> list) throws Exception {
                        Log.d("wata", "Observer(subscribe): "+Thread.currentThread().getName());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    /**
     * 关键词搜索1
     */
    private void test2(){
        RxTextView
                // 得到一个 obserable 对象
                .textChanges(this.mEditText)
                // 500 毫秒只能发送一次数据
                .debounce(500, TimeUnit.MILLISECONDS)
                // 接下来的 obserable 运行在 io线程
                .subscribeOn(Schedulers.io())
                // 用 filter 过滤为空的数据
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(CharSequence charSequence) throws Exception {
                        Log.d("wata", "Obserable(filter): "+Thread.currentThread().getName());
                        return charSequence.toString().trim().length() > 0;
                    }
                })
                // 接下来的 obserable 运行在 主线程
                .subscribeOn(AndroidSchedulers.mainThread())
                // flatMap 过滤数据，并返回一个 Obserable 对象
                .flatMap(new Function<CharSequence, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(CharSequence charSequence) throws Exception {
                        Log.d("wata", "Obserable(flatMap): "+Thread.currentThread().getName());

                        List<String> list = new ArrayList<String>();
                        list.add("a");
                        list.add("account");
                        list.add("available");
                        list.add("appreciate");
                        list.add("assume");

                        return Observable.just(list);
                    }
                })
                // 接下来的 observer 运行在主线程
                .observeOn(AndroidSchedulers.mainThread())
                // 创建一个 observer 对象，并订阅
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> list) throws Exception {
                        Log.d("wata", "Observer(subscribe): "+Thread.currentThread().getName());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

}
