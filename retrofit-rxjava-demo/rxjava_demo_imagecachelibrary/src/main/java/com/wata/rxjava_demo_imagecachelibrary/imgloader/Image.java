package com.wata.rxjava_demo_imagecachelibrary.imgloader;

import android.graphics.Bitmap;

/**
 * Created by Ivan on 2016/11/23.
 */

public class Image {

    private String url;
    private Bitmap bitmap;

    public Image(String url, Bitmap bitmap) {
        this.url = url;
        this.bitmap = bitmap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
