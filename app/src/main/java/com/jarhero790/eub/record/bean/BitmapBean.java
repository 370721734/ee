package com.jarhero790.eub.record.bean;

import android.graphics.Bitmap;

public class BitmapBean {
    private Bitmap bitmap;

    public BitmapBean(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
