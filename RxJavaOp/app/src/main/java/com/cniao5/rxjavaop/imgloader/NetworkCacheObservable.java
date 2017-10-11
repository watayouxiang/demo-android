package com.cniao5.rxjavaop.imgloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Ivan on 2016/11/23.
 */

public class NetworkCacheObservable extends  CacheObservable {


    @Override
    public Image getDataFromCache(String url) {

        Bitmap bitmap = downloadImage(url);
        if(bitmap !=null){

            return  new Image(url,bitmap);
        }

        return null;
    }

    @Override
    public void putDataToCache(Image image) {

    }



    private Bitmap downloadImage(String url){



        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {

            final URLConnection con = new URL(url).openConnection();
            inputStream = con.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return  bitmap;

    }

}
