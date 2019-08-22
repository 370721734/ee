package com.jarhero790.eub.message.message;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jarhero790.eub.R;
import com.jarhero790.eub.message.my.FullyGridLayoutManager;
import com.jarhero790.eub.message.my.GridImageAdapter;
import com.jarhero790.eub.message.my.SettingActivity;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.record.CustomProgressDialog;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.SharePreferenceUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.adapter.PictureImageGridAdapter;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivateJiuBaoActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tu)
    LinearLayout tu;
    //    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.text)
    TextView text;

    private String userid;
    private String type;
    private String content;
    private String img1 = "";
    private String img2 = "";
    private String img3 = "";
    private String img4 = "";

    private List<LocalMedia> selectList = new ArrayList<>();
    private GridImageAdapter adapter;
    private int maxSelectNum = 4;
    private int themeId = R.style.picture_default_style;
    private int chooseMode = PictureMimeType.ofImage();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_jiu_bao);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        recyclerView = findViewById(R.id.recycler);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        userid = intent.getStringExtra("userid");
        etTitle.setText(type);

        FullyGridLayoutManager manager = new FullyGridLayoutManager(PrivateJiuBaoActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(PrivateJiuBaoActivity.this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);

                            if ((selectList.size()-1)==position){
                                PictureSelector.create(PrivateJiuBaoActivity.this)
                                        .openGallery(PictureMimeType.ofImage())
                                        .theme(R.style.picture_default_style)
                                        .minSelectNum(1)
                                        .imageSpanCount(4)
                                        .maxSelectNum(4)
                                        .selectionMode(PictureConfig.MULTIPLE)
                                        .previewImage(true)
                                        .isCamera(false)
                                        .enableCrop(false)
                                        .compress(true)
                                        .circleDimmedLayer(true)
                                        .minimumCompressSize(100)
                                        .forResult(PictureConfig.CHOOSE_REQUEST);
                            }else {
                                PictureSelector.create(PrivateJiuBaoActivity.this).themeStyle(themeId).openExternalPreview(position, selectList);
                            }

                            break;
                    }



                }
            }
        });

    }


    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            PictureSelector.create(PrivateJiuBaoActivity.this)
                    .openGallery(PictureMimeType.ofImage())
                    .theme(R.style.picture_default_style)
                    .minSelectNum(1)
                    .imageSpanCount(4)
                    .maxSelectNum(4)
                    .selectionMode(PictureConfig.MULTIPLE)
                    .previewImage(true)
                    .isCamera(false)
                    .enableCrop(false)
                    .compress(true)
                    .circleDimmedLayer(true)
                    .minimumCompressSize(100)
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        }
    };

    List<String> tustring = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            tustring.clear();
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.e("-", "压缩---->" + media.getCompressPath());//ok
//                        压缩---->/storage/emulated/0/Android/data/com.jarhero790.eub/cache/luban_disk_cache/1566459206967421.jpg
//                        Log.e("-", "原图---->" + media.getPath());
//                        Log.e("-", "裁剪---->" + media.getCutPath());
                        Bitmap bitmap = BitmapFactory.decodeFile(media.getCompressPath());
                        tustring.add("data:image/png;base64," + CommonUtil.convertIconToString(bitmap));
                    }
                    Log.e("-------tustring:", tustring.size() + "");

                    if (tustring != null && tustring.size() == 4) {
                        img1 = tustring.get(0);
                        img2 = tustring.get(1);
                        img3 = tustring.get(2);
                        img4 = tustring.get(3);
                    } else if (tustring != null && tustring.size() == 3) {
                        img1 = tustring.get(0);
                        img2 = tustring.get(1);
                        img3 = tustring.get(2);
                    } else if (tustring != null && tustring.size() == 2) {
                        img1 = tustring.get(0);
                        img2 = tustring.get(1);
                    } else if (tustring != null && tustring.size() == 1) {
                        img1 = tustring.get(0);
                    }


                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();

                    break;

            }

        }

    }
    CustomProgressDialog dialog=new CustomProgressDialog();

    @OnClick({R.id.back, R.id.submit, R.id.tu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                type = etTitle.getText().toString();
                if (TextUtils.isEmpty(type)) {
                    Toast.makeText(this, "举报理由不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                content = etContent.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(this, "举报内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (tustring==null || tustring.size()==0){
                    Toast.makeText(this, "需要截图证据", Toast.LENGTH_SHORT).show();
                    return;
                }


                dialog.createLoadingDialog(this,"正在提交...");
                dialog.show();
                RetrofitManager.getInstance().getDataServer().report(SharePreferenceUtil.getToken(AppUtils.getContext()), userid, type, content, img1, img2, img3, img4)
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    dialog.dismiss();
                                    try {
                                        String json = response.body().string();
//                                        Log.e("------4444-----", json);
                                        JSONObject object = new JSONObject(json);
                                        int code = object.optInt("code");
                                        String msg = object.optString("msg");
                                        if (code == 200) {
                                            Toast.makeText(PrivateJiuBaoActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(PrivateJiuBaoActivity.this, msg, Toast.LENGTH_SHORT).show();
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
                                dialog.dismiss();
                            }
                        });


                break;
            case R.id.tu:
                break;
        }
    }
}
