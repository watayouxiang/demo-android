package com.wata.rxjava_demo_imagecachelibrary.imgloader;

import android.content.Context;
import android.util.Log;

import com.wata.rxjava_demo_imagecachelibrary.imgloader.observable.DiskCacheObservable;
import com.wata.rxjava_demo_imagecachelibrary.imgloader.observable.MemoryCacheObservable;
import com.wata.rxjava_demo_imagecachelibrary.imgloader.observable.NetworkCacheObservable;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by Ivan on 2016/11/23.
 */

public class RequestCreator {

    private MemoryCacheObservable mMemoryCacheObservable;
    private DiskCacheObservable mDiskCacheObservable;
    private NetworkCacheObservable mNetworkCacheObservable;

    public  RequestCreator(Context context){
        mMemoryCacheObservable = new MemoryCacheObservable();
        mDiskCacheObservable = new DiskCacheObservable(context);
        mNetworkCacheObservable = new NetworkCacheObservable();
    }

    public Observable<Image> getImageFromMemory(String url){
        return mMemoryCacheObservable
                .getImage(url)
                .filter(new Predicate<Image>() {
                    @Override
                    public boolean test(Image image) throws Exception {
                        return image !=null;
                    }
                })
                .doOnNext(new Consumer<Image>() {
                    @Override
                    public void accept(Image image) throws Exception {
                        Log.d("RequestCreator","get data from memory");
                    }
                });
    }

    public Observable<Image> getImageFromDisk(String url){
        return  mDiskCacheObservable
                .getImage(url)
                .filter(new Predicate<Image>() {
                    @Override
                    public boolean test(Image image) throws Exception {
                        return image !=null;
                    }
                })
                .doOnNext(new Consumer<Image>() {
                    @Override
                    public void accept(Image image) throws Exception {
                        Log.d("RequestCreator","get data from disk");
                        mMemoryCacheObservable.putDataToCache(image);
                    }
                });
    }

    public Observable<Image> getImageFromNetwork(String url){
        return  mNetworkCacheObservable
                .getImage(url)
                .filter(new Predicate<Image>() {
                    @Override
                    public boolean test(Image image) throws Exception {
                        return image !=null;
                    }
                })
                .doOnNext(new Consumer<Image>() {
                    @Override
                    public void accept(Image image) throws Exception {
                        Log.d("RequestCreator","get data from network");

                        if(image !=null){
                            mDiskCacheObservable.putDataToCache(image);
                            mMemoryCacheObservable.putDataToCache(image);
                        }
                    }
                });
    }


}
