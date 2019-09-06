package com.jarhero790.eub.record.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jarhero790.eub.record.TCEditerUtil;
import com.jarhero790.eub.record.view.AnimatedGifEncoder;
import com.tencent.liteav.basic.log.TXCLog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by vinsonswang on 2018/10/24.
 */

public class GifUtil {
    private static final String TAG = "----------";

    public static String createGifByBitmaps(String filePath, List<Bitmap> bitmapList, int delayMs, int width, int height) throws IOException {
        if (bitmapList == null || bitmapList.size() == 0) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        AnimatedGifEncoder gifEncoder = new AnimatedGifEncoder();
        gifEncoder.start(baos);
        gifEncoder.setRepeat(0);
        gifEncoder.setDelay(delayMs);
        TXCLog.e(TAG, "start make gif");
        for (Bitmap bitmap : bitmapList) {
            Bitmap resizeBitmap = TCEditerUtil.zoomImg(bitmap, width, height);
            Bitmap newresizeBitmap = GifUtil.compressImage(resizeBitmap);
            gifEncoder.addFrame(newresizeBitmap);
        }
        gifEncoder.finish();
        TXCLog.e(TAG, "finish make gif");
        FileOutputStream fos = new FileOutputStream(filePath);
        baos.writeTo(fos);
        baos.flush();
        fos.flush();
        baos.close();
        fos.close();
        return filePath;
    }


    /**
     * 压缩图片
     * 该方法引用自：http://blog.csdn.net/demonliuhui/article/details/52949203
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于400kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

}
