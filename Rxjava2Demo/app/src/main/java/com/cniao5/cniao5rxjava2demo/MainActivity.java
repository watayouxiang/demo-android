package com.cniao5.cniao5rxjava2demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
    }

    public void click(View view) {

//        Observable<String> observale = getObservable();
////        Observer<String> observer = getObserver();
//
////        observale.subscribe(observer);
//
//        observale.subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//
//
//                Log.d("MainActivity","accept="+s);
//
//                textView.append(s);
//                textView.append("//n");
//            }
//        });

    }


//
//    public Observable<String> getObservable() {
//
//
//        Observable observable = Observable.just("大保健","泡吧","撩妹");
////        Observable observable = Observable.fromArray("大保健","泡吧","撩妹");
//       return  Observable.fromCallable(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                return "大保健";
//            }
//        });
//
////        return  observable;
//
////        return Observable.create(new ObservableOnSubscribe<String>() {
////            @Override
////            public void subscribe(ObservableEmitter<String> e) throws Exception {
////
////
//////                e.onNext("大保健");
//////                e.onNext("泡吧");
////
////                e.onComplete();
//////                e.onError(new);
////
////            }
////        });
//    }
//
//
//
//    public Observer<String>  getObserver(){
//
//
//
//        return   new Observer<String>() {
//
//              Disposable dd =null;
//            @Override
//            public void onSubscribe(Disposable d) {
//
//
//                dd = d;
//
//                Log.d("MainActivity","onSubscribe");
//            }
//
//            @Override
//            public void onNext(String value) {
//                Log.d("MainActivity","onNext");
//
//                if(value.equals("泡妞")){
//                    dd.dispose();
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d("MainActivity","onError");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d("MainActivity","onComplete");
//            }
//        };
//    }



}
