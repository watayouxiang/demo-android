package com.cniao5.retrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.189:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        api = retrofit.create(Api.class);


    }


    public void requestAPI(View view) {


        Map<String, String> map = new HashMap<>();

        map.put("id", "1");
        map.put("name", "cniao5");


        api.editUser(1, "new_cniao5").enqueue(new Callback<BaseResult>() {
            @Override
            public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {


                Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<BaseResult> call, Throwable t) {

            }
        });


    }

    public void login(View view) {

        api.login(new UserParam("cniao5", "123456")).enqueue(new Callback<BaseResult>() {
            @Override
            public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {


                if (response.isSuccessful()) {


                    int user_id = response.body().getUser_id();

                    api.getUserInfoWithPath(user_id).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {

                            User user = response.body();

                            Toast.makeText(MainActivity.this, "username=" + user.getUsername(), Toast.LENGTH_LONG).show();


                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });

                }

            }

            @Override
            public void onFailure(Call<BaseResult> call, Throwable t) {

            }
        });

    }

    public void loginWithRxJava(View view) {


        api.loginWithRx(new UserParam("cniao5", "123456"))
                .flatMap(new Func1<BaseResult, Observable<User>>() {

                    @Override
                    public Observable<User> call(final BaseResult baseResult) {


                        return api.getUserInfoWithRx(baseResult.getUser_id());
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {

                        Toast.makeText(MainActivity.this, "username=" + user.getUsername(), Toast.LENGTH_LONG).show();
                    }
                });

    }
}
