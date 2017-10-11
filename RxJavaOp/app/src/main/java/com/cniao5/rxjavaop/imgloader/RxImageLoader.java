package com.cniao5.rxjavaop.imgloader;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * Created by Ivan on 2016/11/23.
 */

public class RxImageLoader {


    static  RxImageLoader singleton;

    private String mUrl ;

    private RequestCreator mRequestCreator;



    private  RxImageLoader(Builder builder){

        mRequestCreator = new RequestCreator(builder.mContext);
    }


    public static RxImageLoader with(Context context){


        if(singleton == null){

            synchronized (RxImageLoader.class){

                if(singleton == null){

                    singleton = new Builder(context).build();
                }
            }
        }

        return singleton;


    }



    public RxImageLoader load(String url){


        this.mUrl = url;

        return singleton;

    }


    public void into(final ImageView imgview){




            Observable.concat(mRequestCreator.getImageFromMemory(mUrl),
                    mRequestCreator.getImageFromDisk(mUrl),
                    mRequestCreator.getImageFromNetwork(mUrl))

                .first(new Func1<Image, Boolean>() {

                    @Override
                    public Boolean call(Image image) {


                        return image !=null;
                    }
                })
                .subscribe(new Observer<Image>() {


                    @Override
                    public void onCompleted() {

                        Log.d("RxImageLoader","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Image image) {

                        imgview.setImageBitmap(image.getBitmap());
                    }
                });



    }



    public static class Builder {


        private  Context mContext;

        public Builder(Context context){

            this.mContext = context;

        }



        public RxImageLoader build(){

            return  new RxImageLoader(this);
        }


    }
}
