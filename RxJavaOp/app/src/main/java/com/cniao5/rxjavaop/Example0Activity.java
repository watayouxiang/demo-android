package com.cniao5.rxjavaop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.Observer;

public class Example0Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example0);


        Button button = (Button) this.findViewById(R.id.btn);

        RxView.clicks(button).debounce(1, TimeUnit.SECONDS).subscribe(new Observer<Void>() {
            @Override
            public void onCompleted() {
                
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Void aVoid) {

                
                Log.d("Example0Activity","这是按钮点击:" +System.currentTimeMillis());
            }
        });
        
    }

    
    
  
}
