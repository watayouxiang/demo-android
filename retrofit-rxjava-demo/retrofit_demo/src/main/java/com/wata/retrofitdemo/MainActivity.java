package com.wata.retrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Api api;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);

        String url = "http://www.kuaidi100.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())// 配置Gson解析泛型
                .build();
        api = retrofit.create(Api.class);
    }

    public void requestAPI1(View view){
        getData();
    }
    public void requestAPI2(View view){
        getData2();
    }
    public void requestAPI3(View view){
        getData3();
    }
    public void requestAPI4(View view){
        getData4();
    }
    public void requestAPI5(View view){
        postData();
    }
    public void requestAPI6(View view){
        postData2();
    }

    public void postData2(){
        // http://www.kuaidi100.com/query
        // type=yuantong&postid=11111111111
        Call<Result> call = api.postData2("yuantong","11111111111");
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                tv.setText(response.body().toString());
                tv.append("--------[ POST - baseUrl: http://www.kuaidi100.com/query, param: type=yuantong&postid=11111111111 ]");
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                String errorMsg = "error";
                Toast.makeText(MainActivity.this,errorMsg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void postData(){
        // http://www.kuaidi100.com/query
        // type=yuantong&postid=11111111111
        Call<Result> call = api.postData(new Param("yuantong", "11111111111"));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                tv.setText(response.body().toString());
                tv.append("--------[ POST - baseUrl: http://www.kuaidi100.com/query, param: type=yuantong&postid=11111111111 ]");
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                String errorMsg = "error";
                Toast.makeText(MainActivity.this,errorMsg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData(){
        //http://www.kuaidi100.com/query
        Call<Result> call = api.getData();
        call.enqueue(new Callback<Result>() {
            /**
             * 运行在主线程
             */
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                tv.setText(response.body().toString());
                tv.append("--------[ GET - URL: http://www.kuaidi100.com/query ]");
            }

            /**
             * 运行在主线程
             */
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                String errorMsg = "error";
                Toast.makeText(MainActivity.this,errorMsg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData2(){
        //http://www.kuaidi100.com/query?type=yuantong
        Call<Result> call = api.getData2("yuantong");
        call.enqueue(new Callback<Result>() {
            /**
             * 运行在主线程
             */
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                tv.setText(response.body().toString());
                tv.append("--------[ GET - URL: http://www.kuaidi100.com/query?type=yuantong ]");
            }

            /**
             * 运行在主线程
             */
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                String errorMsg = "error";
                Toast.makeText(MainActivity.this,errorMsg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData3(){
        //http://www.kuaidi100.com/query/yuantong
        Call<Result> call = api.getData3("yuantong");
        call.enqueue(new Callback<Result>() {
            /**
             * 运行在主线程
             */
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //tv.setText(response.body().toString());
                tv.setText("--------[ GET - URL: http://www.kuaidi100.com/query/yuantong ]");
            }

            /**
             * 运行在主线程
             */
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                String errorMsg = "error";
                Toast.makeText(MainActivity.this,errorMsg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData4(){
        //http://www.kuaidi100.com/query?type=yuantong&postid=11111111111
        Map<String, String> map = new HashMap<>();
        map.put("type","yuantong");
        map.put("postid","11111111111");
        Call<Result> call = api.getData4(map);
        call.enqueue(new Callback<Result>() {
            /**
             * 运行在主线程
             */
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                tv.setText(response.body().toString());
                tv.append("--------[ GET - URL: http://www.kuaidi100.com/query?type=yuantong&postid=11111111111 ]");
            }

            /**
             * 运行在主线程
             */
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                String errorMsg = "error";
                Toast.makeText(MainActivity.this,errorMsg,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
