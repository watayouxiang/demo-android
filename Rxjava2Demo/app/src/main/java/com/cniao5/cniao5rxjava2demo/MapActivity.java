package com.cniao5.cniao5rxjava2demo;

import android.os.UserHandle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapActivity extends AppCompatActivity {

    private Api api;

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.189:5000/")
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJa)
                .build();


        api = retrofit.create(Api.class);

        mTextView = (TextView) this.findViewById(R.id.text);
    }

//
//    private  int login(String usernamae){
//
//        ///
//        int user_id = 1; //
//
//        return  user_id;
//
//    }
//    private  User getUserInfo(int user_id){
//
//        //
////        return  user;
//    }





    private UserParam getUserParam(){

        UserParam pa = new UserParam("cniao5","123456");
        return pa;
    }

    public void click_map(View view) {





        Observable.just(getUserParam())
                .flatMap(new Function<UserParam, ObservableSource<BaseResult>>() {
            @Override
            public ObservableSource<BaseResult> apply(UserParam userParam) throws Exception {


               return  api.login(userParam);
            }
        }).flatMap(new Function<BaseResult, ObservableSource<User>>() {
            @Override
            public ObservableSource<User> apply(BaseResult baseResult) throws Exception {

               User user =  api.getUserInfoWithPath(baseResult.getUser_id()).execute().body();
                return Observable.just(user);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {


                mTextView.setText(user.getUsername());


            }
        });










        // 登录
        // 获取用户信息





//
//        Observable<String> observableMap= observable.map(new Function<Integer,String>() {
//
//            @Override
//            public String apply(Integer integer) throws Exception {
//
//
//                return 1+"--cniao5.com";
//            }
//        });
//
//
//
//
//        observableMap.subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//
//            }
//        });



    }
}
