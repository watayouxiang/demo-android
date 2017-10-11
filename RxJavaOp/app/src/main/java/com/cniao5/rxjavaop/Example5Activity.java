package com.cniao5.rxjavaop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.cniao5.rxjavaop.imgloader.RxImageLoader;
import com.jakewharton.rxbinding.view.RxView;
import com.squareup.picasso.Picasso;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class Example5Activity extends AppCompatActivity {


    private Button mButton;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example5);

        this.mButton = (Button) this.findViewById(R.id.btn_get);
        this.mImageView =(ImageView) this.findViewById(R.id.image_view);






//
//
//       final Observable<String> memoryObservable  = Observable.create(new Observable.OnSubscribe<String>() {
//           @Override
//           public void call(Subscriber<? super String> subscriber) {
//
//
//               subscriber.onNext(null);
//               subscriber.onCompleted();
//
//           }
//       });
//
//
//
//        final Observable<String> diskObservable  = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//
//
//                subscriber.onNext(null);
//                subscriber.onCompleted();
//
//            }
//        });
//
//
//
//
//        final Observable<String> netWorkObservable  = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//
//
//                subscriber.onNext("network");
//                subscriber.onCompleted();
//
//            }
//        });


        RxView.clicks(mButton).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {


                RxImageLoader.with(Example5Activity.this).load("http://cniao5-imgs.qiniudn.com/o_1b11gepdrdp71lks1ngh1cp0bjf9.jpg").into(mImageView);

//                Observable.concat(memoryObservable,diskObservable,netWorkObservable)
//                        .first(new Func1<String, Boolean>() {
//                            @Override
//                            public Boolean call(String s) {
//                                return  !TextUtils.isEmpty(s);
//                            }
//                        })
//                        .subscribe(new Action1<String>() {
//                        @Override
//                        public void call(String s) {
//
//                            Log.d("Example5Activity","get data from "+s);
//
//                        }
//                    });

            }
        });






    }
}
