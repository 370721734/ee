package com.jarhero790.eub.message.my;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.jarhero790.eub.GlobalApplication;
import com.jarhero790.eub.MainActivity;
import com.jarhero790.eub.R;
import com.jarhero790.eub.activity.FensiActivity;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.message.LoginNewActivity;
import com.jarhero790.eub.message.bean.JsonBean;
import com.jarhero790.eub.message.message.AboutActivity;
import com.jarhero790.eub.message.message.PrivateJiuBaoActivity;
import com.jarhero790.eub.message.net.RetrofitManager;

import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.GetJsonDataUtil;
import com.jarhero790.eub.utils.ImageCutUtils;
import com.jarhero790.eub.utils.SharePreferenceUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {

    private static final int CUT_REQUEST = 4;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.iv_userimage)
    CircleImageView ivUserimage;
    @BindView(R.id.iv_pho)
    ImageView ivPho;
    @BindView(R.id.tv_name)
    EditText tvName;
    @BindView(R.id.iv_edit_name)
    ImageView ivEditName;
    @BindView(R.id.tv_sign)
    EditText tvSign;
    @BindView(R.id.iv_edit_sign)
    ImageView ivEditSign;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.iv_edit_address)
    ImageView ivEditAddress;
    @BindView(R.id.tv_exit)
    TextView tvExit;
    @BindView(R.id.tv_xieyi)
    TextView tvXieyi;
    @BindView(R.id.man)
    RadioButton man;
    @BindView(R.id.woman)
    RadioButton woman;
    @BindView(R.id.rgroup)
    RadioGroup rgroup;
    @BindView(R.id.iv_userimagetwo)
    CircleImageView ivUserimagetwo;
    @BindView(R.id.rlv)
    RecyclerView recyclerView;
    @BindView(R.id.t3)
    TextView t3;
    @BindView(R.id.t1)
    TextView t1;
    @BindView(R.id.t2)
    TextView t2;

    private String signs;
    private String nicknames;
    private String sexs;
    private String citys;
    private String headimgurls;


    private List<JsonBean> options1Items = new ArrayList<>();

    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();

    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private Thread thread;

    private static final int REQUEST_IMAGE = 3;
    private static final int RESULT_REQUEST_CODE = 3;
    public static final String DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmp/";
    File outputFile;

    static {
        new File(DIR).mkdirs();
    }

    private static final int MSG_LOAD_DATA = 0x0001;

    private static final int MSG_LOAD_SUCCESS = 0x0002;

    private static final int MSG_LOAD_FAILED = 0x0003;


    GridSingImageAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private int maxSelectNum = 1;
    private int themeId = R.style.picture_default_style;



    private static boolean isLoaded = false;
    @SuppressLint("HandlerLeak")

    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {

            switch (msg.what) {

                case MSG_LOAD_DATA:

                    if (thread == null) {//如果已创建就不再重新创建子线程了


//                        Toast.makeText(SettingActivity.this, "Begin Parse Data", Toast.LENGTH_SHORT).show();

                        thread = new Thread(new Runnable() {

                            @Override

                            public void run() {

                                // 子线程中解析省市区数据

                                initJsonData();

                            }

                        });

                        thread.start();

                    }

                    break;


                case MSG_LOAD_SUCCESS:

//                    Toast.makeText(SettingActivity.this, "Parse Succeed", Toast.LENGTH_SHORT).show();

                    isLoaded = true;

                    break;


                case MSG_LOAD_FAILED:

//                    Toast.makeText(SettingActivity.this, "Parse Failed", Toast.LENGTH_SHORT).show();

                    break;

            }

        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(SettingActivity.this);
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String sign = intent.getStringExtra("sign");
        String sex = intent.getStringExtra("sex");
        String headim = intent.getStringExtra("heading");
        String addres = intent.getStringExtra("city");




        if (headim != null) {
            ivUserimage.setVisibility(View.VISIBLE);
            ivUserimagetwo.setVisibility(View.GONE);
            Glide.with(this).load(headim).apply(new RequestOptions().placeholder(R.mipmap.about_icon).error(R.mipmap.about_icon)).into(ivUserimage);
        }

        if (addres != null){
            tvAddress.setText(addres);
        }else {
            tvAddress.setText("深圳市龙岗区");
        }


//        Log.e("--------", name + sign + sex);

        if (name != null)
            tvName.setText(name);

        if (sign != null)
            tvSign.setText(sign);


        if (sex != null) {
            if (sex.equals("1")) {
                rgroup.check(R.id.man);
                sexs = "1";
            } else if (sex.equals("2")) {
                rgroup.check(R.id.woman);
                sexs = "2";
            } else {
                rgroup.check(R.id.man);
                sexs = "1";
            }
        }


        FullyGridLayoutManager manager = new FullyGridLayoutManager(SettingActivity.this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridSingImageAdapter(SettingActivity.this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridSingImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
//                    Log.e("---------","3333333333333");

                    PictureSelector.create(SettingActivity.this)
                            .openGallery(PictureMimeType.ofImage())
                            .theme(R.style.picture_default_style)
                            .imageSpanCount(4)
                            .selectionMode(PictureConfig.SINGLE)
                            .previewImage(true)
                            .isCamera(false)
                            .enableCrop(true)
                            .compress(true)
                            .circleDimmedLayer(true)
                            .minimumCompressSize(100)
                            .forResult(PictureConfig.CHOOSE_REQUEST);






//                    LocalMedia media = selectList.get(position);
//                    String pictureType = media.getPictureType();
//                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
//                    switch (mediaType) {
//                        case 1:
//                            // 预览图片 可自定长按保存路径
//                            //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
//                            PictureSelector.create(SettingActivity.this).themeStyle(themeId).openExternalPreview(position, selectList);
//                            break;
//                    }
                }
            }
        });

    }

    @OnClick({R.id.back, R.id.save, R.id.iv_pho, R.id.iv_edit_name, R.id.iv_edit_sign, R.id.iv_edit_address, R.id.tv_exit, R.id.tv_xieyi, R.id.tv_address, R.id.man, R.id.woman,R.id.tv_lixi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.save:
                nicknames = tvName.getText().toString();
                signs = tvSign.getText().toString();
                citys = tvAddress.getText().toString();
//                Log.e("-------------img:", headimgurls);
                String newhead = "data:image/png;base64," + headimgurls;
                editinfo(signs, nicknames, sexs, citys, newhead);


                break;
            case R.id.iv_pho:
                /**
                 //                Intent intent=new Intent();
                 outputFile = new File(DIR, System.currentTimeMillis() + ".jpg");
                 //                intent.setAction(Intent.ACTION_PICK);//
                 //                intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,"image/*");
                 //                intent.putExtra("output", Uri.fromFile(outputFile));
                 //                startActivityForResult(intent,REQUEST_IMAGE);
                 Intent intent = new Intent();
                 intent.setAction(Intent.ACTION_GET_CONTENT);// 打开图库获取图片
                 intent.setAction(Intent.ACTION_PICK);// 打开图库获取图片
                 intent.setType("image/*");// 这个参数是确定要选择的内容为图片
                 intent.putExtra("return-data", true);// 是否要返回，如果设置false取到的值就是空值
                 intent.putExtra("output", Uri.fromFile(outputFile));
                 startActivityForResult(intent, REQUEST_IMAGE);
                 */


                PictureSelector.create(SettingActivity.this)
                        .openGallery(PictureMimeType.ofImage())
                        .theme(R.style.picture_default_style)
                        .imageSpanCount(4)
                        .selectionMode(PictureConfig.SINGLE)
                        .previewImage(true)
                        .isCamera(false)
                        .enableCrop(true)
                        .compress(true)
                        .circleDimmedLayer(true)
                        .minimumCompressSize(100)
                        .forResult(PictureConfig.CHOOSE_REQUEST);


                break;
            case R.id.iv_edit_name:
                break;
            case R.id.iv_edit_sign:
                break;
            case R.id.iv_edit_address:
            case R.id.tv_address:

                showPickerView();

                break;
            case R.id.tv_exit:
                exitmeth();

                break;
            case R.id.tv_xieyi:
                startActivity(new Intent(SettingActivity.this, XieYiActivity.class));
                break;
            case R.id.man:
                sexs = "1";
                break;
            case R.id.woman:
                sexs = "2";
                break;
            case R.id.tv_lixi:
                startActivity(new Intent(SettingActivity.this, AboutActivity.class));
                break;

        }
    }

    private void exitmeth() {
        if (!SharePreferenceUtil.getUserid(AppUtils.getContext()).equals("")){
//            Log.e("---------------exit-",SharePreferenceUtil.getUserid(AppUtils.getContext()));
            RetrofitManager.getInstance().getDataServer().logout(SharePreferenceUtil.getUserid(AppUtils.getContext()))
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()){
                                try {
                                    String json=response.body().string();
//                                    Log.e("----------",json);
                                    JSONObject object=new JSONObject(json);
                                    int code=object.optInt("code");
                                    String msg=object.optString("msg");
                                    if (code==200){
                                        SharePreferenceUtil.setBooleanSp(SharePreferenceUtil.IS_LOGIN, false, SettingActivity.this);
                                        SharePreferenceUtil.setToken("", SettingActivity.this);
                                        SharePreferenceUtil.clearToken(SettingActivity.this);
                                        SharePreferenceUtil.clearSharePref(SharePreferenceUtil.IS_LOGIN, SettingActivity.this);
                                        startActivity(new Intent(SettingActivity.this, LoginNewActivity.class));
                                        SharePreferenceUtil.clearUserid(SettingActivity.this);
//                                        Toast.makeText(SettingActivity.this,msg,Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(SettingActivity.this,msg,Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });

        }

    }


    private void showPickerView() {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(SettingActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置

                String opt1tx = options1Items.size() > 0 ?

                        options1Items.get(options1).getPickerViewText() : "";


                String opt2tx = options2Items.size() > 0

                        && options2Items.get(options1).size() > 0 ?

                        options2Items.get(options1).get(options2) : "";


                String opt3tx = options2Items.size() > 0

                        && options3Items.get(options1).size() > 0

                        && options3Items.get(options1).get(options2).size() > 0 ?

                        options3Items.get(options1).get(options2).get(options3) : "";


//                String tx = opt1tx + opt2tx + opt3tx;

//                Toast.makeText(SettingActivity.this, tx, Toast.LENGTH_SHORT).show();

                tvAddress.setText(city(opt1tx, opt2tx) + opt3tx);
            }
        })

//                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
//            @Override
//            public void onOptionsSelectChanged(int options1, int options2, int options3) {
//                String str = "options1: " + options1 + "options2: " + options2 + "options3: " + options3;
//                Toast.makeText(SettingActivity.this, str, Toast.LENGTH_SHORT).show();
//            }
//        })
                .setBgColor(Color.WHITE)
                .setSubmitText("确定")
                .setCancelText("取消")//取消按钮文字
//                .setTitleText("城市选择")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
//                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
//                .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
//                .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
//                        .setLinkage(false)//设置是否联动，默认true
//                .setLabels("省", "市", "区")//设置选择的三级单位
//                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .setCyclic(false, false, false)//循环与否
//                .setSelectOptions(1, 1, 1)  //设置默认选中项
//                .setOutSideCancelable(false)//点击外部dismiss default true
//                .isDialog(true)//是否显示为对话框样式
//                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();

        if (options1Items.size() > 0 && options2Items.size() > 0 && options3Items.size() > 0) {
            pvOptions.setPicker(options1Items, options2Items, options3Items);
            pvOptions.show();
        }

    }

//    private Uri imageUri;
//    private String photoPath;
//    Bitmap bitmaptu;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
//            imageUri = data.getData();

//            Log.e("---------1", imageUri + "");
//            ivUserimage.setImageURI(imageUri);

//            if (imageUri != null) {
//                try {
//                    bitmaptu = getBitmapFormUri(this, imageUri);
//                    ivUserimage.setVisibility(View.GONE);
//                    ivUserimagetwo.setVisibility(View.VISIBLE);
//                    ivUserimagetwo.setImageBitmap(bitmaptu);
//                    headimgurls = CommonUtil.convertIconToString(bitmaptu);
////                    Log.e("-------------img2:", headimgurls);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }

//            ImageCutUtils.cropImage(SettingActivity.this,imageUri);
//            ContentResolver contentResolver = this.getContentResolver();
//            try {
//                Bitmap bitmaptu = BitmapFactory.decodeStream(contentResolver.openInputStream(ImageCutUtils.cropImageUri));
//
//                ivUserimagetwo.setImageBitmap(bitmaptu);
//                String path = Environment.getExternalStorageDirectory().getAbsolutePath();//手机设置的存储位置
//                File file = new File(path);
//                File imageFile = new File(file, System.currentTimeMillis() + ".png");
//
//
//                if (!file.exists()) {
//                    file.mkdirs();
//                }
//                imageFile.createNewFile();
//                FileOutputStream outputStream = new FileOutputStream(imageFile);
//                bitmaptu.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
//                headimgurls=CommonUtil.convertIconToString(bitmaptu);
//                outputStream.flush();
//                outputStream.close();

//            } catch (Exception e) {
//                e.printStackTrace();
//            }


//            String[] filePathColumn={MediaStore.Audio.Media.DATA};
//            Cursor cursor=getContentResolver().query(imageUri,filePathColumn,null,null,null);
//            cursor.moveToFirst();
//            photoPath=cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
//            cursor.close();
//            crop(imageUri);
//            ivImg.setImageURI(imageUri);
//            Log.e("-------imageuri",imageUri+"");
        } else if (requestCode == RESULT_REQUEST_CODE) {
//            imageUri = data.getData();
//            Log.e("---------2", imageUri + "");

//            setImageBitmap();
//            ivImg.setImageBitmap(bitmap);
//            bitmapToString = bitmapToString(bitmap);
        } else if (requestCode == CUT_REQUEST) {
//            imageUri=data.getData();
//            Log.e("---------3", imageUri + "");
            //-3: null????
//            ivUserimage.setImageBitmap(bitmaptu);
        } else if (requestCode == ImageCutUtils.CROP_IMAGE) {
            if (ImageCutUtils.cropImageUri != null) {
//                ivUserimage.setImageBitmap(bitmaptu);
                try {
//                    String fileName = getRealPathFromURI(ImageCutUtils.cropImageUri);
//                    String status = Environment.getExternalStorageState();
//                    String status = Environment.getExternalStorageDirectory().getAbsolutePath();

//                    ContentResolver contentResolver=this.getContentResolver();
                    try {
//                        Bitmap bitmap= BitmapFactory.decodeStream(contentResolver.openInputStream(ImageCutUtils.cropImageUri));
//                        ivUserimage.setImageBitmap(bitmap);


//                        Bitmap bit=BitmapFactory.decodeFile(status+fileName);
//                        Log.e("---------",status+fileName);
//                        ivUserimage.setImageBitmap(bit);

//                        ivUserimage.setImageURI(ImageCutUtils.cropImageUri);
//                        Log.e("----------",""+ImageCutUtils.cropImageUri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // upLoadFile(fileName);
//                    List<ImageItem> itemList = new ArrayList<ImageItem>();
//                    ImageItem imageItem = new ImageItem();
//                    imageItem.setImagePath(fileName);
//                    itemList.add(imageItem);
//                    AsyncHttpClient client = new AsyncHttpClient();
//                    RequestParams map = new RequestParams();
//                    map = CommonUtils.compressionImage("1", null, itemList);
//                    client.post(NewInterface.UPLOAD_PHOTOS, map, upload);//图片的上传方法
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == PictureConfig.CHOOSE_REQUEST && resultCode == RESULT_OK) {
//            List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
            // 图片选择结果回调
            selectList = PictureSelector.obtainMultipleResult(data);
            if (selectList.size() > 0) {
//                for (int i = 0; i < selectList.size(); i++) {
//                    Log.e("----compresspath12:--", selectList.get(i).getCompressPath());
//                }

                Bitmap bitmap=BitmapFactory.decodeFile(selectList.get(0).getCompressPath());
                headimgurls = CommonUtil.convertIconToString(bitmap);

                adapter.setList(selectList);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @SuppressWarnings("deprecation")
//    public String getRealPathFromURI(Uri contentUri) {
//        String[] proj = {MediaStore.Images.Media.DATA};
//        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }

//    private void setImageBitmap() {
//        //获取imageview的宽和高
//        int targetWidth = ivImg.getWidth();
//        int targetHeight = ivImg.getHeight();
//
//        //根据图片路径，获取bitmap的宽和高
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(photoPath, options);
//        int photoWidth = options.outWidth;
//        int photoHeight = options.outHeight;
//
//        //获取缩放比例
//        int inSampleSize = 1;
//        if (photoWidth > targetWidth || photoHeight > targetHeight) {
//            int widthRatio = Math.round((float) photoWidth / targetWidth);
//            int heightRatio = Math.round((float) photoHeight / targetHeight);
//            inSampleSize = Math.min(widthRatio, heightRatio);
//        }
//
//        //使用现在的options获取Bitmap
//        options.inSampleSize = inSampleSize;
//        options.inJustDecodeBounds = false;
//        bitmap = BitmapFactory.decodeFile(photoPath, options);
//        ivImg.setImageBitmap(bitmap);
//    }

    /**
     * 剪切图片
     */
    private void crop(Uri uri) {
        // 裁剪图片意图

//        Intent intent=new Intent(Intent.ACTION_PICK,uri);
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");


        Intent intent = new Intent("com.android.camera.action.CROP", uri);
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.setDataAndType(uri, "image/*");


        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 60);//250
        intent.putExtra("outputY", 60);
        // 图片格式
        intent.putExtra("outputFormat", "PNG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        intent.putExtra("output", Uri.fromFile(outputFile));

        startActivityForResult(intent, CUT_REQUEST);
//        startActivityForResult(intent, RESULT_REQUEST_CODE);//同样的在onActivityResult中处理剪裁好的图片
    }

    private String city(String s1, String s2) {
        if (s1.equals(s2)) {
            return s2;
        } else {
            return s1 + s2;
        }
    }

    private void initJsonData() {//解析数据


        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */

        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据


        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体


        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。

         */

        options1Items = jsonBean;


        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份

            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）

            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）


            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市

                String cityName = jsonBean.get(i).getCityList().get(c).getName();

                cityList.add(cityName);//添加城市

                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表


                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃

                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null

                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {

                    city_AreaList.add("");

                } else {

                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());

                }*/

                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());

                province_AreaList.add(city_AreaList);//添加该省所有地区数据

            }


            /**
             * 添加城市数据

             */

            options2Items.add(cityList);


            /**
             * 添加地区数据

             */

            options3Items.add(province_AreaList);

        }


        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);


    }
private Dialog dialog;
    private void editinfo(String sign, String nickname, String sex, String city, String headimg) {
        dialog = new Dialog(this, R.style.progress_dialog);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        RetrofitManager.getInstance().getDataServer().editinfo(SharePreferenceUtil.getToken(AppUtils.getContext()), sign, nickname, sex, city, headimg)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            try {
                                String json = response.body().string();
//                                Log.e("--------a=>", json);
//                                {"code":200,"data":null,"msg":"\u6210\u529f"}
                                JSONObject object = new JSONObject(json);
                                int code = object.optInt("code");
                                String msg = object.optString("msg");
                                if (code == 200) {
                                    Toast.makeText(SettingActivity.this, msg, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SettingActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        Toast.makeText(SettingActivity.this,"网络请求异常",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
    }


    public ArrayList<JsonBean> parseData(String result) {//Gson 解析

        ArrayList<JsonBean> detail = new ArrayList<>();

        try {

            JSONArray data = new JSONArray(result);

            Gson gson = new Gson();

            for (int i = 0; i < data.length(); i++) {

                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);

                detail.add(entity);

            }

        } catch (Exception e) {

            e.printStackTrace();

            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);

        }

        return detail;

    }


    @Override

    protected void onDestroy() {

        super.onDestroy();

        if (mHandler != null) {

            mHandler.removeCallbacksAndMessages(null);

        }

        PictureFileUtils.deleteCacheDirFile(this);

    }

    /**
     * 通过uri获取图片并进行压缩
     *
     * @param uri
     */
    public static Bitmap getBitmapFormUri(Activity ac, Uri uri) throws FileNotFoundException, IOException {
        InputStream input = ac.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        float hh = 200f;//这里设置高度为800f
        float ww = 200f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return compressImage(bitmap);//再进行质量压缩
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 200) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            startActivity(new Intent(this, MainActivity.class));
        }
        return super.onKeyDown(keyCode, event);

    }

    private GridSingImageAdapter.onAddPicClickListener onAddPicClickListener = new GridSingImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            PictureSelector.create(SettingActivity.this)
                    .openGallery(PictureMimeType.ofImage())
                    .theme(R.style.picture_default_style)
                    .imageSpanCount(4)
                    .selectionMode(PictureConfig.SINGLE)
                    .previewImage(true)
                    .isCamera(false)
                    .enableCrop(true)
                    .compress(true)
                    .circleDimmedLayer(true)
                    .minimumCompressSize(100)
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        }
    };
}
