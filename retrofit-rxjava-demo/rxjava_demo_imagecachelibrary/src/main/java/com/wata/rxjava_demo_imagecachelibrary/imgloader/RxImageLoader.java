package com.wata.rxjava_demo_imagecachelibrary.imgloader;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;

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
        Observable
                .concat(mRequestCreator.getImageFromMemory(mUrl),
                        mRequestCreator.getImageFromDisk(mUrl),
                        mRequestCreator.getImageFromNetwork(mUrl))
                .filter(new Predicate<Image>() {
                    @Override
                    public boolean test(Image image) throws Exception {
                        return image !=null;
                    }
                })
                .subscribe(new Observer<Image>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Image image) {
                        imgview.setImageBitmap(image.getBitmap());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d("RxImageLoader","onCompleted");
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
