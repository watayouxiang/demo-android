package com.wata.rxjava_demo_imagecachelibrary.imgloader.observable;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.wata.rxjava_demo_imagecachelibrary.imgloader.Image;

/**
 * Created by Ivan on 2016/11/23.
 */

public class MemoryCacheObservable extends  CacheObservable {

    int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);// kB
    int cacheSize = maxMemory / 8;

    LruCache<String,Bitmap> mLruCache = new LruCache<String, Bitmap>(cacheSize){
        @Override
        protected int sizeOf(String key, Bitmap value) {
           return  value.getRowBytes() * value.getHeight() /1024;
        }
    };

    @Override
    public Image getDataFromCache(String url) {
        Bitmap bitmap =  mLruCache.get(url);
        if(bitmap !=null){
            return  new Image(url,bitmap);
        }
        return  null;
    }

    @Override
    public void putDataToCache(Image image) {
        mLruCache.put(image.getUrl(),image.getBitmap());
    }
}
