package com.jarhero790.eub.message.souye;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jarhero790.eub.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GuanPanView extends View {

    Paint paint=new Paint();


    public GuanPanView(@NonNull Context context) {
        super(context);
        init();
    }

    public GuanPanView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public void init(){
      paint.setAntiAlias(true);
      paint.setDither(true);
    }
    public void mergeThumbnailBitmap(String url){

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imgurl=null;
                try {
                    imgurl=new URL(url);
                    HttpURLConnection conn= (HttpURLConnection) imgurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is=conn.getInputStream();
                    Bitmap bitmap2=BitmapFactory.decodeStream(is);
                    is.close();

//                    Bitmap bitmap1= BitmapFactory.decodeResource(getResources(),R.mipmap.guangpan_icon);
//                    Canvas canvas=new Canvas(bitmap1);
//                    float w = bitmap1.getWidth();
//                    float h = bitmap1.getHeight();
//                    Matrix m = new Matrix();
//                    m.setScale(w/bitmap2.getWidth(),h/bitmap2.getHeight());
//                    paint.setAlpha(150);
//                    canvas.drawBitmap(bitmap1,m,paint);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Bitmap firstBitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.guangpan_icon);
        Bitmap bitmap1 = Bitmap.createBitmap(firstBitmap.getWidth(), firstBitmap
                .getHeight(), firstBitmap.getConfig());

        Canvas canvas=new Canvas(bitmap1);
        float w = bitmap1.getWidth();
        float h = bitmap1.getHeight();
        Matrix m = new Matrix();
        m.setScale(w/5,h/5);
        paint.setAlpha(150);
        canvas.drawBitmap(firstBitmap, 0, 0, null);
//        canvas.drawBitmap(bitmap1,m,paint);
    }

}
