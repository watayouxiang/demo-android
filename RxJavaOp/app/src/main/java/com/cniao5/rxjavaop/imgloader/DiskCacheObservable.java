package com.cniao5.rxjavaop.imgloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by Ivan on 2016/11/23.
 */

public class DiskCacheObservable extends  CacheObservable {


    private  DiskLruCache mDiskLruCache;


    private Context mContext;
    //DiskLruCache中对于图片的最大缓存值.
    private int maxSize=20*1024*1024;


    public DiskCacheObservable(Context context){

        this.mContext = context;
        initDiskLruCache();

    }

    @Override
    public Image getDataFromCache(String url) {


        Bitmap bitmap = getDataFromDiskLruCache(url);
        if(bitmap !=null){
            return  new Image(url,bitmap);
        }

        return null;
    }

    @Override
    public void putDataToCache(final Image image) {


        Observable.create(new Observable.OnSubscribe<Image>() {
            @Override
            public void call(Subscriber<? super Image> subscriber) {
                putDataToDiskLruCache(image);

            }
        }).subscribeOn(Schedulers.io()).subscribe();


    }






    /**
     * 初始化DiskLrucache
     */
    private void initDiskLruCache(){
        try {
            File cacheDir=DiskCacheUtil.getDiskCacheDir(this.mContext,"imge_cache");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            int versionCode=DiskCacheUtil.getAppVersionCode(mContext);
            mDiskLruCache=DiskLruCache.open(cacheDir, versionCode, 1, maxSize);

        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    private Bitmap getDataFromDiskLruCache(String url){



        FileDescriptor fileDescriptor = null;
        FileInputStream fileInputStream = null;
        DiskLruCache.Snapshot snapShot = null;
        try {
            // 生成图片URL对应的key
            final String key = DiskCacheUtil.getMd5String(url);
            // 查找key对应的缓存
            snapShot = mDiskLruCache.get(key);

            if (snapShot != null) {
                fileInputStream = (FileInputStream) snapShot.getInputStream(0);
                fileDescriptor = fileInputStream.getFD();
            }
            // 将缓存数据解析成Bitmap对象
            Bitmap bitmap = null;
            if (fileDescriptor != null) {
                bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            }

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileDescriptor == null && fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                }
            }
        }
        return null;

    }


    private void putDataToDiskLruCache(Image img){


        try {
            //第一步:获取将要缓存的图片的对应唯一key值.
            String key = DiskCacheUtil.getMd5String(img.getUrl());
            //第二步:获取DiskLruCache的Editor
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);

            if (editor!=null) {
                //第三步:从Editor中获取OutputStream
                OutputStream outputStream=editor.newOutputStream(0);
                //第四步:下载网络图片且保存至DiskLruCache图片缓存中

                boolean isSuccessfull=download(img.getUrl(), outputStream);

                if (isSuccessfull) {
                    editor.commit();
                }else{
                    editor.abort();
                }
                mDiskLruCache.flush();
            }
        } catch (Exception e) {

        }
    }


    private boolean  download(String urlString, OutputStream outputStream){


        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {

            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;

        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;

    }





}
