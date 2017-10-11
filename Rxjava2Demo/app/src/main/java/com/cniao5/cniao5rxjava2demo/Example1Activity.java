package com.cniao5.cniao5rxjava2demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;



public class Example1Activity extends AppCompatActivity {


    private EditText mEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example1);

        this.mEditText = (EditText) this.findViewById(R.id.edit_search);





//
//        RxTextView.textChanges(this.mEditText)
//                .subscribe(new Observer<CharSequence>() {
//            @Override
//            public void onCompleted() {
//                Log.d("Example1Activity","onCompleted-------");
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                Log.d("Example1Activity","onError-------");
//                throwable.printStackTrace();
//            }
//
//            @Override
//            public void onNext(CharSequence charSequence) {
//
//                Log.d("Example1Activity","change="+charSequence);
//
//            }
//        });

    }



}
