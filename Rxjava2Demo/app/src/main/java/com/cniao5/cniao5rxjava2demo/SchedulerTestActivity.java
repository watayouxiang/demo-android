package com.cniao5.cniao5rxjava2demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SchedulerTestActivity extends AppCompatActivity {


    private TextView mTextView;

    private Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler_test);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.189:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        api = retrofit.create(Api.class);

        mTextView = (TextView) findViewById(R.id.text);



    }



    public void click(View view) {

//
//        Observable.create(new ObservableOnSubscribe<User>() {
//            @Override
//
//            // 主线程里面执行
//            public void subscribe(ObservableEmitter<User> e) throws Exception {
//
//
//
//
//                User user = api.getUserInfoWithPath(1).execute().body();
//
//                e.onNext(user);
//
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//
//                .subscribe(new Observer<User>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//
//
//            }
//
//            @Override
//            public void onNext(User user) {
//
//
//                Log.d("SchedulerTestActivity",""+user);
//
//                mTextView.setText(user.getUsername());
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//
//

    }
}
