package com.jarhero790.eub.utils;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageCutUtils {

    public static final int GET_IMAGE_BY_CAMERA = 5001;
    public static final int GET_IMAGE_FROM_PHONE = 5002;
    public static final int CROP_IMAGE = 5003;
    public static Uri cropImageUri;
    public static Uri imageUriFromCamera;
    public static  int TYPE ;// 1."1"表示车友圈背景图,2."0"表示头像等,3."2"表示空间活动的logo

    /** 调用相机 */
    public static void openCameraImage(final Activity activity, int type) {
        TYPE = type;
        ImageCutUtils.imageUriFromCamera = ImageCutUtils.createImagePathUri(activity);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		if (!MyApplication.getInstance().getMolber().split(" ")[0].equals("Hisense")) {
        intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageCutUtils.imageUriFromCamera);
        activity.startActivityForResult(intent, ImageCutUtils.GET_IMAGE_BY_CAMERA);
//		} else {
//			activity.startActivityForResult(intent, ImageCutUtils.GET_IMAGE_BY_CAMERA);
//		}
    }

    /** 调用相册 */
    public static void openLocalImage(final Activity activity, int type) {
        TYPE = type;
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // intent.setType("image/*");
        // intent.setAction(Intent.ACTION_GET_CONTENT);
        // intent.addCategory(Intent.CATEGORY_OPENABLE);
        // intent.putExtra("return-data", true);

        activity.startActivityForResult(intent, ImageCutUtils.GET_IMAGE_FROM_PHONE);
    }

    /** 图片剪切 */
    public static void cropImage(Activity activity, Uri srcUri) {
        try {

            ImageCutUtils.cropImageUri = ImageCutUtils.createImagePathUri(activity);
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(srcUri, "image/*");
            intent.putExtra("crop", "true");
            if (TYPE == 0) {
                // aspectX aspectY 是裁剪框宽高的比例
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);// 去黑边
                // outputX outputY 是裁剪后生成图片的宽高
//				intent.putExtra("outputX", 800);
//				intent.putExtra("outputY", 800);
            } else if (TYPE == 1) {
                intent.putExtra("aspectX", 16);
                intent.putExtra("aspectY", 9);
                intent.putExtra("scale", true);// 去黑边
                // outputX outputY 是裁剪后生成图片的宽高
                intent.putExtra("outputX", 800);
                intent.putExtra("outputY", 450);
            }else if (TYPE == 2) {
                intent.putExtra("aspectX", 10);
                intent.putExtra("aspectY", 16);
                intent.putExtra("scale", true);// 去黑边
                // outputX outputY 是裁剪后生成图片的宽高
                intent.putExtra("outputX", 500);
                intent.putExtra("outputY", 800);
            }

            intent.putExtra("scaleUpIfNeeded", true);// 去黑边
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            // return-data为true时,会直接返回bitmap数据,但是大图裁剪时会出现问题,推荐下面为false时的方式
            // return-data为false时,不会返回bitmap,但需要指定一个MediaStore.EXTRA_OUTPUT保存图片uri
            intent.putExtra("return-data", false);//false
            intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageCutUtils.cropImageUri);
            activity.startActivityForResult(intent, ImageCutUtils.CROP_IMAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建一条图片地址uri,用于保存拍照后的照片
     *
     * @param context
     * @return 图片的uri
     */
    private static Uri createImagePathUri(Context context) {
        Uri imageFilePath = null;
        try {

            String status = Environment.getExternalStorageState();
            SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
            long time = System.currentTimeMillis();
            String imageName = timeFormatter.format(new Date(time));
            // ContentValues是我们希望这条记录被创建时包含的数据信息
            ContentValues values = new ContentValues(3);
            values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
            values.put(MediaStore.Images.Media.DATE_TAKEN, time);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
            if (status.equals(Environment.MEDIA_MOUNTED)) {
                // 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
                imageFilePath = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        values);
            } else {
                imageFilePath = context.getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                        values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageFilePath;
    }

}



