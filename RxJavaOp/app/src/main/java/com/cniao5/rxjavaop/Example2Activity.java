package com.cniao5.rxjavaop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;

public class Example2Activity extends AppCompatActivity {


    private EditText mEditEmail;
    private EditText mEditPasswrod;

    private Button  mBtnReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example2);


        this.mEditEmail = (EditText) this.findViewById(R.id.edit_email);
        this.mEditPasswrod = (EditText) this.findViewById(R.id.edit_password);
        this.mBtnReg = (Button) this.findViewById(R.id.btn_reg);


       Observable<CharSequence> observableEmail= RxTextView.textChanges(this.mEditEmail);
       Observable<CharSequence> observablePassword= RxTextView.textChanges(this.mEditPasswrod).skip(1);


        Observable.combineLatest(observableEmail, observablePassword, new Func2<CharSequence, CharSequence, Boolean>() {


            @Override
            public Boolean call(CharSequence email, CharSequence pwd) {

               Log.d("Example2Activity",email +" = " + pwd +" == " + Thread.currentThread().getName());

               return isEmailValid(email.toString()) && isPasswordValid(pwd.toString());


            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean result) {


                mBtnReg.setEnabled(result);


            }
        });



    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}
