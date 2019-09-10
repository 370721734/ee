package com.jarhero790.eub.record.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jarhero790.eub.MainActivity;
import com.jarhero790.eub.R;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.message.net.RetrofitManager;
import com.jarhero790.eub.message.permission.KbPermission;
import com.jarhero790.eub.message.permission.KbPermissionListener;
import com.jarhero790.eub.message.permission.KbPermissionUtils;
import com.jarhero790.eub.record.TCConstants;
import com.jarhero790.eub.record.bean.FaVBean;
import com.jarhero790.eub.record.view.MediaPlayUtil;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.jarhero790.eub.utils.SharePreferenceUtil;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.common.fwlog.LogEntity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FaBuActivity extends AppCompatActivity implements ITXVodPlayListener {

    private static final int REQUESTMUSIC = 112;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_fabu)
    TextView tvFabu;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.iv_music)
    ImageView ivMusic;
    @BindView(R.id.tv_text1)
    TextView tvText1;
    @BindView(R.id.text)
    View text;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.et_content)
    EditText etContent;
    //    @BindView(R.id.video_view)
    TXCloudVideoView mTXCloudVideoView;

    private TXVodPlayer mTXVodPlayer = null;
    private TXVodPlayConfig mTXPlayConfig = null;
    private int mVideoSource; // 视频来源
    private String mVideoPath;
    private String mCoverImagePath;
    //视频时长（ms）
    private long mVideoDuration;
    //录制界面传过来的视频分辨率
    private int mVideoResolution;
    ImageView mImageViewBg;
    boolean mVideoPlay = false;
    boolean mVideoPause = false;
    ImageView mStartPreview;
    private boolean mStartSeek = false;

    private String mid;
    private String img;
    private String address = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fa_bu);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        EventBus.getDefault().register(this);
        mTXCloudVideoView = (TXCloudVideoView) findViewById(R.id.video_view);
        mImageViewBg = (ImageView) findViewById(R.id.cover);
        mStartPreview = (ImageView) findViewById(R.id.record_preview);

        Intent intent = getIntent();
        mid = intent.getStringExtra("mid");


        if (KbPermissionUtils.needRequestPermission()) {
            KbPermission.with(this)
                    .requestCode(200)
                    .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .callBack(new KbPermissionListener() {
                        @Override
                        public void onPermit(int requestCode, String... permission) {

                        }

                        @Override
                        public void onCancel(int requestCode, String... permission) {
                            KbPermissionUtils.goSetting(FaBuActivity.this);
                        }
                    }).send();
        }


        mVideoSource = getIntent().getIntExtra(TCConstants.VIDEO_RECORD_TYPE, TCConstants.VIDEO_RECORD_TYPE_EDIT);
        mVideoPath = getIntent().getStringExtra(TCConstants.VIDEO_RECORD_VIDEPATH);
        mCoverImagePath = getIntent().getStringExtra(TCConstants.VIDEO_RECORD_COVERPATH);
        mVideoDuration = getIntent().getLongExtra(TCConstants.VIDEO_RECORD_DURATION, 0);
        mVideoResolution = getIntent().getIntExtra(TCConstants.VIDEO_RECORD_RESOLUTION, -1);

//        Log.e("-------", "onCreate: mVideoPath = " + mVideoPath + ",mVideoDuration = " + mVideoDuration);


        if (mCoverImagePath != null && !mCoverImagePath.isEmpty()) {
            Glide.with(this).load(Uri.fromFile(new File(mCoverImagePath)))
                    .into(mImageViewBg);

            try {
                Bitmap bitmap = GifUtil.getBitmapFormUri(FaBuActivity.this, Uri.fromFile(new File(mCoverImagePath)));
                img = "data:image/png;base64," + CommonUtil.convertIconToString(bitmap);
//                mImageViewBg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


//        Bitmap bitmap = BitmapFactory.decodeFile(mCoverImagePath);
//        Bitmap newbitmap = GifUtil.compressImage(bitmap);


        mTXVodPlayer = new TXVodPlayer(this);
        mTXPlayConfig = new TXVodPlayConfig();
        mTXCloudVideoView.disableLog(true);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void fav(FaVBean faVBean) {

    }

    private Dialog dialog;


    @OnClick({R.id.back, R.id.tv_fabu, R.id.iv_music, R.id.submit, R.id.record_preview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_fabu:

                String title = etTitle.getText().toString();
                if (TextUtils.isEmpty(title)) {
                    Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show();
                    return;
                }
                address = etContent.getText().toString();

                HttpParameterBuilder builder = HttpParameterBuilder.newBuilder();
                Map<String, RequestBody> bodyMap = builder.bulider();

                File file = new File(mVideoPath);


//                URI uri = file.toURI();

//                if (!file.exists()) {
//                    file.mkdirs();
//                }
//                Log.e("----------file=", mVideoPath);
                if (mid == null || img == null) {

                    Toast.makeText(this, "图片或音乐为空", Toast.LENGTH_SHORT).show();
                    return;
                }
//                Log.e("--------mid=", mid + "  img=" + img);
//                String img = "data:image/png;base64,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";
                String descriptionString = "This is a description";
                RequestBody ytoken = RequestBody.create(MediaType.parse("text/plain"), SharePreferenceUtil.getToken(AppUtils.getContext()));
                RequestBody ymid = RequestBody.create(MediaType.parse("text/plain"), mid);//音乐ID
                RequestBody yimg = RequestBody.create(MediaType.parse("text/plain"), img);//base64
                RequestBody ytitle = RequestBody.create(MediaType.parse("text/plain"), title);//标题
                RequestBody yaddress = RequestBody.create(MediaType.parse("text/plain"), address);//地址
//                RequestBody traceBody = RequestBody.create(MediaType.parse("video/mp4"), file);
                RequestBody description = RequestBody.create(MediaType.parse("text/plain"), descriptionString);
                bodyMap.put("token", ytoken);
                bodyMap.put("mid", ymid);
                bodyMap.put("img", yimg);
                bodyMap.put("title", ytitle);
                bodyMap.put("address", yaddress);
                bodyMap.put("description", description);


                RequestBody bo = new MultipartBody.Builder()
                        .addFormDataPart("token", "d2bb986529ba01f3d42d676dfb63ac4f")
                        .addFormDataPart("mid", "229")
                        .addFormDataPart("img", img)
                        .addFormDataPart("title", "title")

//                        .addFormDataPart("file", file.getName(), traceBody)
                        .build();
                MultipartBody.Part myBody = MultipartBody.Part.createFormData("file", file.getName(), bo);

//                bodyMap.put("file",yfile);


//                List<MultipartBody.Part> partList = new ArrayList<>();
//                RequestBody requestBody = null;
//                MultipartBody.Part part = null;
//                List<File> files=new ArrayList<>();
//                files.add(new File(mVideoPath));
//                for (File filer : files) {
//                      requestBody=RequestBody.create(MediaType.parse("video/mp4"), filer);
//                    part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
//                    partList.add(part);
//                }
//                byte[] bytes = new byte[]{};
                try {
//                    bytes = GifUtil.readStream(mVideoPath);

//                    String newbytes = new String(bytes, "UTF-8");
                    RequestBody yfile = RequestBody.create(MediaType.parse("video/mp4"), file);//
                    MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), yfile);
////                    if (bytes.length>0){
//
//                    RetrofitManager.getInstance().getDataServer().uploadmusic(SharePreferenceUtil.getToken(AppUtils.getContext())
//                            , mid, img, title, newbytes, address).enqueue(new Callback<ResponseBody>() {
//                        @Override
//                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                            if (response.isSuccessful()) {
//                                //Response{protocol=http/1.1, code=500, message=Internal Server Error, url=http://www.51ayhd.com/zstv/public/index.php/web/index/uploadLocal}
//                                try {
//                                    String json = response.body().string();
//                                    Log.e("--------------333", json);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            } else {
//                                Log.e("--------------333", "fail");
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<ResponseBody> call, Throwable t) {
//                            Log.e("--------------333", "fail3");
//                        }
//                    });
//
//
////                    }
//
//


//                    RequestBody requestBody = new MultipartBody.Builder()
//                            .setType(MultipartBody.FORM)
//                            .addFormDataPart("token", SharePreferenceUtil.getToken(AppUtils.getContext()))
//                            .addFormDataPart("mid", "229")
//                            .addFormDataPart("img", img)
//                            .addFormDataPart("title", "title")
//                            .addPart(part)
//                            .addFormDataPart("file", file.getName(), traceBody)
//                            .build();
//                    OkHttpClient okHttpClient = new OkHttpClient();
//                    Request request = new Request.Builder().url(Api.HOST + "web/index/uploadLocal").post(requestBody).build();
//                    okhttp3.Call call = okHttpClient.newCall(request);
//                    call.enqueue(new okhttp3.Callback() {
//                        @Override
//                        public void onFailure(okhttp3.Call call, IOException e) {
//                            Log.e("--------------222", "fail" + e.getMessage());
//                        }
//
//                        @Override
//                        public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
//                            if (response.isSuccessful()) {
//                                String json = response.body().string();
//                                Log.e("-----------", json);
//                            } else {
//                                Log.e("--------------333", "fail" + response.message());
//                            }
//                        }
//                    });


                    dialog = new Dialog(this, R.style.progress_dialog);
//                    View viewp = View.inflate(this, R.layout.dialog, null);
//                    ImageView imageView = viewp.findViewById(R.id.iv_icon);
//                    Glide.with(this).load(R.mipmap.wangluoicon).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
//                            .into(imageView);
                    dialog.setContentView(R.layout.dialog);
                    dialog.setCancelable(true);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.show();

//
                    RetrofitManager.getInstance().getDataServer().uploadLocal(bodyMap, part).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                dialog.dismiss();
                                try {
                                    String json = response.body().string();
                                    Log.e("--------------333", json);
                                    JSONObject object = new JSONObject(json);
                                    int code = object.optInt("code");
                                    String msg = object.optString("msg");
                                    if (code == 200) {
                                        EventBus.getDefault().post(new FaVBean("video"));
                                        startActivity(new Intent(FaBuActivity.this, MainActivity.class));

                                    } else {
                                        Toast.makeText(FaBuActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                dialog.dismiss();
//                            Log.e("--------------333", "fail" + response.message());
//                            try {
//                                Log.e("--------------3232","fail"+response.body().string());
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("--------------333", "fail2");
                            dialog.dismiss();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;
            case R.id.iv_music:
                Intent intent = new Intent(this, SelectMusicActivity.class);
                startActivityForResult(intent, REQUESTMUSIC);
                break;
            case R.id.submit:
                break;
            case R.id.record_preview:
                if (mVideoPlay) {
                    if (mVideoPause) {
                        mTXVodPlayer.resume();
                        mStartPreview.setBackgroundResource(R.drawable.icon_record_pause);
                        mVideoPause = false;
                    } else {
                        mTXVodPlayer.pause();
                        mStartPreview.setBackgroundResource(R.drawable.icon_record_start);
                        mVideoPause = true;
                    }
                } else {
                    startPlay();
                }
                break;
        }
    }


    private boolean startPlay() {
        mStartPreview.setBackgroundResource(R.drawable.icon_record_pause);
        mTXVodPlayer.setPlayerView(mTXCloudVideoView);
        mTXVodPlayer.setVodListener(this);

        mTXVodPlayer.enableHardwareDecode(false);
        mTXVodPlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
        mTXVodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);

        mTXVodPlayer.setConfig(mTXPlayConfig);

        int result = mTXVodPlayer.startPlay(mVideoPath); // result返回值：0 success;  -1 empty url; -2 invalid url; -3 invalid playType;
        if (result != 0) {
            mStartPreview.setBackgroundResource(R.drawable.icon_record_start);
            return false;
        }

        mVideoPlay = true;
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTMUSIC && resultCode == RESULT_OK) {
            music = data.getStringExtra("music");
            mid = data.getStringExtra("mid");
            Log.e("-----------music=", music);
            MediaPlayUtil.getInstance().stop();
            MediaPlayUtil.getInstance().start(music);
        }
    }

    private String music = null;

    @Override
    protected void onResume() {
        super.onResume();
        if (music != null) {
            MediaPlayUtil.getInstance().start(music);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        MediaPlayUtil.getInstance().pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        MediaPlayUtil.getInstance().stop();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaPlayUtil.getInstance().release();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPlayEvent(TXVodPlayer txVodPlayer, int event, Bundle param) {
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.setLogText(null, param, event);
        }
        if (event == TXLiveConstants.PLAY_EVT_PLAY_PROGRESS) {
            if (mStartSeek) {
                return;
            }
            if (mImageViewBg.isShown()) {
                mImageViewBg.setVisibility(View.GONE);
            }
            int progress = param.getInt(TXLiveConstants.EVT_PLAY_PROGRESS);
            int duration = param.getInt(TXLiveConstants.EVT_PLAY_DURATION);//单位为s
            long curTS = System.currentTimeMillis();
            // 避免滑动进度条松开的瞬间可能出现滑动条瞬间跳到上一个位置
//            if (Math.abs(curTS - mTrackingTouchTS) < 500) {
//                return;
//            }
//            mTrackingTouchTS = curTS;
//
//            if (mSeekBar != null) {
//                mSeekBar.setProgress(progress);
//            }
//            if (mProgressTime != null) {
//                mProgressTime.setText(String.format(Locale.CHINA, "%02d:%02d/%02d:%02d", (progress) / 60, progress % 60, (duration) / 60, duration % 60));
//            }
//
//            if (mSeekBar != null) {
//                mSeekBar.setMax(duration);
//            }
        } else if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {

//            showErrorAndQuit(TCConstants.ERROR_MSG_NET_DISCONNECTED);

        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {
            mTXVodPlayer.resume(); // 播放结束后，可以直接resume()，如果调用stop和start，会导致重新播放会黑一下
        }
    }

    @Override
    public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

    }

}
