package com.cniao5.rxjavaop;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;

public class Example4Activity extends AppCompatActivity {


    private EditText mEditMobi;
    private Button mButtonSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example4);

        this.mEditMobi = (EditText) this.findViewById(R.id.edit_mobi);
        this.mButtonSend = (Button) findViewById(R.id.btn_send);


    }

    public void click(View view) {




        final int count =10;

        Observable.interval(0,1,TimeUnit.SECONDS)
                .take(count+1)
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {

                        return count-aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                        mButtonSend.setEnabled(false);
                        mButtonSend.setTextColor(Color.BLACK);

                    }
                })
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

        //                Log.d()
                        Log.d("Example4Activity","onCompleted");

                        mButtonSend.setEnabled(true);
                        mButtonSend.setTextColor(Color.WHITE);
                        mButtonSend.setText("发生验证码");
                    }

                    @Override
                    public void onError(Throwable e) {

                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Long num) {
                        Log.d("Example4Activity","onNext:"+num);

                        mButtonSend.setText("剩余 "+ num +" 秒");

                    }
                });


    }
}
