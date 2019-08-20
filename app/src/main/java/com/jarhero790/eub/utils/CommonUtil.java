package com.jarhero790.eub.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.Base64;
import android.view.View;
import android.view.WindowInsets;

import java.io.ByteArrayOutputStream;


public class CommonUtil {
    /**
     * 测量View的宽高
     */
    public static void measureWidthAndHeight(View view) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
    }


    /**
     * 把状态栏设成透明
     */
    public static void setStatusBarTransparent(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = context.getWindow().getDecorView();
            decorView.setOnApplyWindowInsetsListener((v, insets) -> {
                WindowInsets defaultInsets = v.onApplyWindowInsets(insets);
                return defaultInsets.replaceSystemWindowInsets(
                        defaultInsets.getSystemWindowInsetLeft(),
                        0,
                        defaultInsets.getSystemWindowInsetRight(),
                        defaultInsets.getSystemWindowInsetBottom());
            });
            ViewCompat.requestApplyInsets(decorView);
            context.getWindow().setStatusBarColor(context.getResources().getColor(android.R.color.transparent));
        }
    }


    public static String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(bytes, Base64.DEFAULT);

    }

}
