package com.jarhero790.eub.record;


import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.ScaleGestureDetector;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jarhero790.eub.BuildConfig;
import com.jarhero790.eub.record.activity.CustomRoundAngleImageView;
import com.jarhero790.eub.record.bean.BitmapBean;
import com.jarhero790.eub.utils.CommonUtil;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.ugc.TXRecordCommon;
import com.tencent.ugc.TXUGCRecord;
import com.jarhero790.eub.R;
import com.tencent.ugc.TXVideoEditConstants;
import com.tencent.ugc.TXVideoEditer;
import com.tencent.ugc.TXVideoInfoReader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.view.View.GONE;


/**
 * UGC小视频录制界面
 */
public class TCVideoRecordActivity extends Activity implements View.OnClickListener, BeautySettingPannel.IOnBeautyParamsChangeListener
        , TXRecordCommon.ITXVideoRecordListener, View.OnTouchListener, GestureDetector.OnGestureListener, ScaleGestureDetector.OnScaleGestureListener {

    private static final String TAG = "TCVideoRecordActivity";
    private static final String OUTPUT_DIR_NAME = "TXUGC";
    private static final int IMAGE_REQUEST_CODE = 33;
    private boolean mRecording = false;
    private boolean mStartPreview = false;
    private boolean mFront = true;
    private TXUGCRecord mTXCameraRecord;
    private TXRecordCommon.TXRecordResult mTXRecordResult = new TXRecordCommon.TXRecordResult();
    private long mDuration; // 视频总时长

    private BeautySettingPannel.BeautyParams mBeautyParams = new BeautySettingPannel.BeautyParams();
    private TXCloudVideoView mVideoView;
    private ImageView mIvConfirm;
    private TextView mProgressTime;
    private Button mBtnRecordRotation;
    //    private CustomProgressDialog mCustomProgressDialog;
    private ImageView mIvTorch;
    private ImageView mIvMusic;
    private ImageView mIvBeauty;
    private ImageView mIvScale, ivrecord;
    private ComposeRecordBtn mComposeRecordBtn;
    private RelativeLayout mRlAspect;
    private RelativeLayout mRlAspectSelect;
    private ImageView mIvAspectSelectFirst;
    private ImageView mIvAspectSelectSecond;
    private ImageView mIvScaleMask;
    private CustomRoundAngleImageView iv_local_tu;
    private boolean mAspectSelectShow = false;
    private TextView mTvFilter;

    private BeautySettingPannel mBeautyPannelView;
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusListener;
    private boolean mPause = false;
    private TCAudioControl mAudioCtrl;
    private int mCurrentAspectRatio;
    private int mFirstSelectScale;
    private int mSecondSelectScale;
    private RelativeLayout mRecordRelativeLayout = null;
    private FrameLayout mMaskLayout;
    private RecordProgressView mRecordProgressView;
    private ImageView mIvDeleteLastPart;
    private boolean isSelected = false; // 回删状态
    private long mLastClickTime;
    private boolean mIsTorchOpen = false; // 闪光灯的状态

    private GestureDetector mGestureDetector;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor;
    private float mLastScaleFactor;

    private int mRecommendQuality = TXRecordCommon.VIDEO_QUALITY_MEDIUM;
    private int mMinDuration;
    private int mMaxDuration;
    private int mAspectRatio; // 视频比例
    private int mRecordResolution; // 录制分辨率
    private int mHomeOrientation = TXLiveConstants.VIDEO_ANGLE_HOME_DOWN; // 录制方向 竖
    private int mRenderRotation = TXLiveConstants.RENDER_ROTATION_PORTRAIT; // 渲染方向
    private int mBiteRate; // 码率
    private int mFps; // 帧率
    private int mGop; // 关键帧间隔
    private String mBGMPath;
    private String mBGMPlayingPath;
    private int mBGMDuration;
    private ImageView mIvMusicMask;
    private RadioGroup mRadioGroup;
    private int mRecordSpeed = TXRecordCommon.RECORD_SPEED_NORMAL;
    private boolean mNeedEditer;
    private boolean mPortrait = true;
    private Button mSnapShot;
    private boolean mEnableStop = false;
    private int istransverse = TXLiveConstants.RENDER_ROTATION_0;//竖


    private int videotime = 0;
    /**
     * ------------------------ 滑动滤镜相关 ------------------------
     */
    private int mCurrentIndex = 0; // 当前滤镜Index
    private int mLeftIndex = 0, mRightIndex = 1;// 左右滤镜的Index
    private int mLastLeftIndex = -1, mLastRightIndex = -1; // 之前左右滤镜的Index
    private float mLeftBitmapRatio;      // 左侧滤镜的比例
    private float mMoveRatio;      // 滑动的比例大小
    private boolean mStartScroll;  // 已经开始滑动了标记
    private boolean mMoveRight;    // 滑动是否往右
    private boolean mIsNeedChange;    // 滤镜的是否需要发生改变
    private ValueAnimator mFilterAnimator;
    private boolean mIsDoingAnimator;

    private Bitmap mLeftBitmap;
    private Bitmap mRightBitmap;
    //--------------------------------------------------------------
    private ScreenOrientationListener mScreenOrientationListener;
    private int mMobileRotation = Surface.ROTATION_90;

    private boolean mTouchFocus = true; // 是否开启手动对焦
    private TextView tvlook;//预览


    private VideoMainHandler mVideoMainHandler;                 // 加载完信息后的回调主线程Hanlder
    private Thread mLoadBackgroundThread;                       // 后台加载视频信息的线程

    /**
     * 主线程的Handler 用于处理load 视频信息的完后的动作
     */
    private class VideoMainHandler extends Handler {
        static final int LOAD_VIDEO_SUCCESS = 0;
        static final int LOAD_VIDEO_ERROR = -1;
        private WeakReference<TCVideoRecordActivity> mWefActivity;


        VideoMainHandler(TCVideoRecordActivity activity) {
            mWefActivity = new WeakReference<TCVideoRecordActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TCVideoRecordActivity activity = mWefActivity.get();
            if (activity == null) return;
            switch (msg.what) {
                case LOAD_VIDEO_ERROR:
                    if (dialog != null)
                        dialog.dismiss();
                    mIvConfirm.setImageResource(R.drawable.selector_record_confirm);
                    mIvConfirm.setEnabled(true);
                    loadVideoFail();
                    break;
                case LOAD_VIDEO_SUCCESS:
                    if (dialog != null)
                        dialog.dismiss();
                    mIvConfirm.setImageResource(R.drawable.selector_record_confirm);
                    mIvConfirm.setEnabled(true);
                    loadVideoSuccess();
                    break;
            }
        }

        //明
        private void loadVideoSuccess() {
            // 更新一下VideoInfo的时间
            Intent intent = new Intent(TCVideoRecordActivity.this, TCVideoEditerActivity.class);
            // 如果是从录制过来的话，需要传递一个分辨率参数下去。

            intent.putExtra(TCConstants.VIDEO_RECORD_RESOLUTION, TXRecordCommon.VIDEO_RESOLUTION_540_960);//高
            intent.putExtra(TCConstants.VIDEO_RECORD_COVERPATH,mTXRecordResult.coverPath);
            intent.putExtra(TCConstants.VIDEO_EDITER_PATH, mTXRecordResult.videoPath);
            intent.putExtra(TCConstants.RECORD_CONFIG_BITE_RATE, mBiteRate);
            intent.putExtra(TCConstants.VIDEO_EDITER_IMPORT, false);
            //  mRenderRotation = TXLiveConstants.RENDER_ROTATION_0;
            intent.putExtra(TCConstants.TRANSVERSE, istransverse);
            if (istransverse==TXLiveConstants.RENDER_ROTATION_0){
                intent.putExtra(TCConstants.VIDEO_RECORD_TYPE, TCConstants.VIDEO_RECORD_TYPE_UGC_RECORD);//竖
            }else {
                intent.putExtra(TCConstants.VIDEO_RECORD_TYPE, TCConstants.VIDEO_RECORD_TYPE_EDIT);//横

            }
//            int times = (int) mProgressTime.getTag();
            intent.putExtra("time", videotime);///////////////////////////////////////////////////////////
//            Log.e("--------videotime2", "时" + videotime);
            startActivity(intent);
            releaseRecord();
            finish();
        }
    }

    private void loadVideoFail() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        CommonUtil.setStatusBarTransparent(this);

        setContentView(R.layout.activity_video_record);
        EventBus.getDefault().register(this);

        mTXCameraRecord = TXUGCRecord.getInstance(this.getApplicationContext());


        initViews();

        getData();

        initScreenOrientationListener();
    }

    private void initScreenOrientationListener() {
        mScreenOrientationListener = new ScreenOrientationListener(this);
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent == null) {
            TXCLog.e(TAG, "intent is null");
            return;
        }
//        mMinDuration = intent.getIntExtra(TCConstants.RECORD_CONFIG_MIN_DURATION, 5 * 1000);  //5秒
        mMinDuration=5*1000;
//        mMaxDuration = intent.getIntExtra(TCConstants.RECORD_CONFIG_MAX_DURATION, 60 * 1000);
        mMaxDuration=60 * 1000;
//        mAspectRatio = intent.getIntExtra(TCConstants.RECORD_CONFIG_ASPECT_RATIO, TXRecordCommon.VIDEO_RENDER_MODE_FULL_FILL_SCREEN);//VIDEO_ASPECT_RATIO_1_1
        mAspectRatio=TXRecordCommon.VIDEO_ASPECT_RATIO_9_16;
//        mRecommendQuality = intent.getIntExtra(TCConstants.RECORD_CONFIG_RECOMMEND_QUALITY, TXRecordCommon.VIDEO_QUALITY_MEDIUM);//VIDEO_QUALITY_HIGH
        mRecommendQuality=TXRecordCommon.VIDEO_QUALITY_MEDIUM;
//        mNeedEditer = intent.getBooleanExtra(TCConstants.RECORD_CONFIG_NEED_EDITER, true);
        mNeedEditer=true;
//        mTouchFocus = intent.getBooleanExtra(TCConstants.RECORD_CONFIG_TOUCH_FOCUS, false);
        mTouchFocus=false;
        mCurrentAspectRatio = mAspectRatio;
        setSelectAspect();

        mRecordProgressView.setMaxDuration(mMaxDuration);
        mRecordProgressView.setMinDuration(mMinDuration);

//        if (mRecommendQuality != -1) {
//            // 使用了推荐的视频质量设置，用TXUGCSimpleConfig
//            TXCLog.i(TAG, "mRecommendQuality = " + mRecommendQuality);
//            return;
//        }
        // 自定义视频质量设置，用TXUGCCustomConfig
//        mRecordResolution = intent.getIntExtra(TCConstants.RECORD_CONFIG_RESOLUTION, TXRecordCommon.VIDEO_RESOLUTION_540_960);
        mRecordResolution=TXRecordCommon.VIDEO_RESOLUTION_540_960;
//        mBiteRate = intent.getIntExtra(TCConstants.RECORD_CONFIG_BITE_RATE, 6500);//
        mBiteRate=6500;
//        mFps = intent.getIntExtra(TCConstants.RECORD_CONFIG_FPS, 20);
        mFps=20;
//        mGop = intent.getIntExtra(TCConstants.RECORD_CONFIG_GOP, 3);
        mGop=3;

        TXCLog.d(TAG, "mMinDuration = " + mMinDuration + ", mMaxDuration = " + mMaxDuration + ", mAspectRatio = " + mAspectRatio +
                ", mRecommendQuality = " + mRecommendQuality + ", mRecordResolution = " + mRecordResolution + ", mBiteRate = " + mBiteRate + ", mFps = " + mFps + ", mGop = " + mGop);
    }

    private void startCameraPreview() {
        if (mStartPreview) return;
        mStartPreview = true;

        mTXCameraRecord.setVideoRecordListener(this);
        //方式1：
        // activity竖屏模式，竖屏录制 :
        //                           setHomeOrientation(TXLiveConstants.VIDEO_ANGLE_HOME_DOWN);
        //                           setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
        // activity横屏模式，home在右横屏录制(activity随着重力感应旋转)：
        //                           setHomeOrientation(TXLiveConstants.VIDEO_ANGLE_HOME_RIGHT);
        //                           setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
        // activity横屏模式，home在左横屏录制(activity随着重力感应旋转)：
        //                           setHomeOrientation(TXLiveConstants.VIDEO_ANGLE_HOME_LEFT);
        //                           setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
        //
        //方式2：
        // 如果想保持activity为竖屏，并且要home在 [右] 横屏录制，那么可以用下面的方式：
        // activity竖屏模式，home在右横屏录制(锁定Activity不旋转，比如在manefest设置activity的 android:screenOrientation="portrait")：
        //                           setHomeOrientation(TXLiveConstants.VIDEO_ANGLE_HOME_RIGHT);
        //                           setRenderRotation(TXLiveConstants.RENDER_ROTATION_90);
        // 如果想保持activity为竖屏，并且要home在 [左] 横屏录制，那么可以用下面的方式：
        //                           setHomeOrientation(TXLiveConstants.VIDEO_ANGLE_HOME_LEFT);
        //                           setRenderRotation(TXLiveConstants.RENDER_ROTATION_270);
        //
        mTXCameraRecord.setHomeOrientation(mHomeOrientation);
        mTXCameraRecord.setRenderRotation(mRenderRotation);
        // 推荐配置
        if (mRecommendQuality >= 0) {
            TXRecordCommon.TXUGCSimpleConfig simpleConfig = new TXRecordCommon.TXUGCSimpleConfig();
            simpleConfig.videoQuality = mRecommendQuality;
            simpleConfig.minDuration = mMinDuration;
            simpleConfig.maxDuration = mMaxDuration;
            simpleConfig.isFront = mFront;
            simpleConfig.touchFocus = mTouchFocus; // 手动对焦和自动对焦切换需要重新开启预览
            simpleConfig.needEdit = mNeedEditer;

            mTXCameraRecord.setRecordSpeed(mRecordSpeed);
            mTXCameraRecord.startCameraSimplePreview(simpleConfig, mVideoView);
            mTXCameraRecord.setAspectRatio(mCurrentAspectRatio);
        } else {
            // 自定义配置
            TXRecordCommon.TXUGCCustomConfig customConfig = new TXRecordCommon.TXUGCCustomConfig();
            customConfig.videoResolution = mRecordResolution;
            customConfig.minDuration = mMinDuration;
            customConfig.maxDuration = mMaxDuration;
            customConfig.videoBitrate = mBiteRate;
            customConfig.videoGop = mGop;
            customConfig.videoFps = mFps;
            customConfig.isFront = mFront;
            customConfig.touchFocus = mTouchFocus;
            customConfig.needEdit = mNeedEditer;

            mTXCameraRecord.setRecordSpeed(mRecordSpeed);
            mTXCameraRecord.startCameraCustomPreview(customConfig, mVideoView);
            mTXCameraRecord.setAspectRatio(mCurrentAspectRatio);
        }

        mTXCameraRecord.setBeautyDepth(mBeautyParams.mBeautyStyle, mBeautyParams.mBeautyLevel, mBeautyParams.mWhiteLevel, mBeautyParams.mRuddyLevel);
        mTXCameraRecord.setFaceScaleLevel(mBeautyParams.mFaceSlimLevel);
        mTXCameraRecord.setEyeScaleLevel(mBeautyParams.mBigEyeLevel);
        mTXCameraRecord.setSpecialRatio(mBeautyParams.mFilterMixLevel / 10.0f);
        mTXCameraRecord.setFilter(mBeautyParams.mFilterBmp);
        mTXCameraRecord.setGreenScreenFile(mBeautyParams.mGreenFile, true);
        mTXCameraRecord.setMotionTmpl(mBeautyParams.mMotionTmplPath);
        mTXCameraRecord.setFaceShortLevel(mBeautyParams.mFaceShortLevel);
        mTXCameraRecord.setFaceVLevel(mBeautyParams.mFaceVLevel);
        mTXCameraRecord.setChinLevel(mBeautyParams.mChinSlimLevel);
        mTXCameraRecord.setNoseSlimLevel(mBeautyParams.mNoseScaleLevel);
    }

    private void setRecordRotatioinListener() {
        // 如果想保持activity为竖屏，并且要home在右横屏录制，首先把mBtnRecordRotation的点击监听代码打开，把btn_orientation控件可见，然后在manifest中把该activity设置为竖屏android:screenOrientation="portrait"
        mBtnRecordRotation.setVisibility(View.VISIBLE);
        mBtnRecordRotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 5.0版本开始不需要 先停止预览再开启预览了
//                mTXCameraRecord.stopCameraPreview();
//                mStartPreview = false;
                mPortrait = !mPortrait;
                if (mPortrait) {
                    Toast.makeText(TCVideoRecordActivity.this, "竖屏录制", Toast.LENGTH_SHORT).show();
                    mHomeOrientation = TXLiveConstants.VIDEO_ANGLE_HOME_DOWN;
                    mRenderRotation = TXLiveConstants.RENDER_ROTATION_0;
                } else {
                    Toast.makeText(TCVideoRecordActivity.this, "横屏录制", Toast.LENGTH_SHORT).show();
                    // home键在右边的设置
                    mHomeOrientation = TXLiveConstants.VIDEO_ANGLE_HOME_RIGHT;
                    mRenderRotation = TXLiveConstants.RENDER_ROTATION_90;

                    // home键在左边的设置
//                     mHomeOrientation = TXLiveConstants.VIDEO_ANGLE_HOME_LEFT;
//                     mRenderRotation = TXLiveConstants.RENDER_ROTATION_270;
                }
                mTXCameraRecord.setHomeOrientation(mHomeOrientation);
                mTXCameraRecord.setRenderRotation(mRenderRotation);
//                startCameraPreview();
            }
        });
    }

    private void initViews() {
        LinearLayout backLL = (LinearLayout) findViewById(R.id.back_ll);
        backLL.setOnClickListener(this);

        RelativeLayout btn_switch_camera2 = findViewById(R.id.btn_switch_camera2);
        btn_switch_camera2.setOnClickListener(this);

        tvlook = findViewById(R.id.tv_look);
        tvlook.setOnClickListener(this);

        mMaskLayout = (FrameLayout) findViewById(R.id.mask);
        mMaskLayout.setOnTouchListener(this);

        mIvConfirm = (ImageView) findViewById(R.id.btn_confirm);
        mIvConfirm.setOnClickListener(this);
        mIvConfirm.setImageResource(R.mipmap.ugc_confirm_disable);
        mIvConfirm.setEnabled(false);

        mTvFilter = (TextView) findViewById(R.id.record_tv_filter);

        mBeautyPannelView = (BeautySettingPannel) findViewById(R.id.beauty_pannel);
        mBeautyPannelView.setBeautyParamsChangeListener(this);
        mBeautyPannelView.disableExposure();

        mAudioCtrl = (TCAudioControl) findViewById(R.id.layoutAudioControl);
        initAudioListener();

        mVideoView = (TXCloudVideoView) findViewById(R.id.video_view);

        mProgressTime = (TextView) findViewById(R.id.progress_time);
        mIvDeleteLastPart = (ImageView) findViewById(R.id.btn_delete_last_part);
        mIvDeleteLastPart.setOnClickListener(this);

        mIvScale = (ImageView) findViewById(R.id.iv_scale);
        mIvScaleMask = (ImageView) findViewById(R.id.iv_scale_mask);
        mIvAspectSelectFirst = (ImageView) findViewById(R.id.iv_scale_first);
        mIvAspectSelectSecond = (ImageView) findViewById(R.id.iv_scale_second);
        mRlAspect = (RelativeLayout) findViewById(R.id.layout_aspect);
        mRlAspectSelect = (RelativeLayout) findViewById(R.id.layout_aspect_select);

        mIvMusic = (ImageView) findViewById(R.id.btn_music_pannel);
        mIvMusicMask = (ImageView) findViewById(R.id.iv_music_mask);

        mIvBeauty = (ImageView) findViewById(R.id.btn_beauty);

        mRecordRelativeLayout = (RelativeLayout) findViewById(R.id.record_layout);
        mRecordProgressView = (RecordProgressView) findViewById(R.id.record_progress_view);

        mGestureDetector = new GestureDetector(this, this);
        mScaleGestureDetector = new ScaleGestureDetector(this, this);

        iv_local_tu = findViewById(R.id.iv_local_tu);
        iv_local_tu.setOnClickListener(this);

        //自定义进度条的使用
//        mCustomProgressDialog = new CustomProgressDialog();
//        mCustomProgressDialog.createLoadingDialog(this, "");
//        mCustomProgressDialog.setCancelable(false); // 设置是否可以通过点击Back键取消
//        mCustomProgressDialog.setCanceledOnTouchOutside(false); // 设置在点击Dialog外是否取消Dialog进度条

        mIvTorch = (ImageView) findViewById(R.id.btn_torch);
        mIvTorch.setOnClickListener(this);

        if (mFront) {
//            mIvTorch.setImageResource(R.mipmap.ugc_torch_disable);
            mIvTorch.setImageResource(R.mipmap.record_shan);
            mIvTorch.setEnabled(false);
        } else {
            mIvTorch.setImageResource(R.drawable.selector_torch_close);
            mIvTorch.setEnabled(true);
        }

        mComposeRecordBtn = (ComposeRecordBtn) findViewById(R.id.compose_record_btn);
        ivrecord = findViewById(R.id.iv_record_selete);
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_record_speed);
        ((RadioButton) findViewById(R.id.rb_normal)).setChecked(true);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_fast) {
                    mRecordSpeed = TXRecordCommon.RECORD_SPEED_FAST;

                } else if (checkedId == R.id.rb_fastest) {
                    mRecordSpeed = TXRecordCommon.RECORD_SPEED_FASTEST;

                } else if (checkedId == R.id.rb_normal) {
                    mRecordSpeed = TXRecordCommon.RECORD_SPEED_NORMAL;

                } else if (checkedId == R.id.rb_slow) {
                    mRecordSpeed = TXRecordCommon.RECORD_SPEED_SLOW;

                } else if (checkedId == R.id.rb_slowest) {
                    mRecordSpeed = TXRecordCommon.RECORD_SPEED_SLOWEST;

                }
                mTXCameraRecord.setRecordSpeed(mRecordSpeed);
            }
        });

        mBtnRecordRotation = (Button) findViewById(R.id.btn_orientation);

        mSnapShot = (Button) findViewById(R.id.snapshot);
        mSnapShot.setOnClickListener(this);
    }

    private void initAudioListener() {
        mAudioCtrl.setOnItemClickListener(new RecordDef.OnItemClickListener() {

            @Override
            public void onBGMSelect(String path) {
                mBGMPath = path;
                mBGMDuration = mTXCameraRecord.setBGM(path);
                // 在选择音乐的时候试听一下
                if (!TextUtils.isEmpty(mBGMPath)) {
                    // 保证在试听的时候音乐是正常播放的
                    mTXCameraRecord.setRecordSpeed(TXRecordCommon.RECORD_SPEED_NORMAL);
                    mTXCameraRecord.playBGMFromTime(0, mBGMDuration);
                }
            }
        });

        mAudioCtrl.setReturnListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 选择完音乐返回时试听结束
                if (!TextUtils.isEmpty(mBGMPath)) {
                    mTXCameraRecord.stopBGM();
                    // 在试听结束时，再设置回原来的速度
                    mTXCameraRecord.setRecordSpeed(mRecordSpeed);
                }

                mAudioCtrl.mMusicSelectView.setVisibility(View.GONE);
                mAudioCtrl.setVisibility(View.GONE);
                mIvMusic.setImageResource(R.mipmap.ugc_record_music);
                mRecordRelativeLayout.setVisibility(View.VISIBLE);
            }
        });
        mAudioCtrl.setAudioListener(new TCAudioControl.AudioListener() {
            @Override
            public void onSetReverb(int reverbType) {
                mTXCameraRecord.setReverb(reverbType);
            }

            @Override
            public void onSetVoiceChangerType(int voiceChangeType) {
                mTXCameraRecord.setVoiceChangerType(voiceChangeType);
            }

            @Override
            public void onClickStopBgm() {
                mTXCameraRecord.stopBGM();
            }

            @Override
            public void onSetBGMVolume(float volume) {
                // 范围0-2，默认为1
                mTXCameraRecord.setBGMVolume(volume);
            }

            @Override
            public void onSetMicVolume(float volume) {
                // 范围0-2，默认为1
                mTXCameraRecord.setMicVolume(volume);
            }

            @Override
            public int onGetMusicDuration(String musicPath) {
                return mTXCameraRecord.getMusicDuration(musicPath);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        TXCLog.i(TAG, "onPause");
        mScreenOrientationListener.disable();
        if (mTXCameraRecord != null) {
            mTXCameraRecord.setVideoProcessListener(null); // 这里要取消监听，否则在上面的回调中又会重新开启预览
            mTXCameraRecord.stopCameraPreview();
            mStartPreview = false;
            // 设置闪光灯的状态为关闭
            if (mIsTorchOpen) {
                mIsTorchOpen = false;
                if (mFront) {
                    mIvTorch.setImageResource(R.mipmap.record_shan);
                    mIvTorch.setEnabled(false);
                } else {
                    mIvTorch.setImageResource(R.drawable.selector_torch_close);
                    mIvTorch.setEnabled(true);
                }
            }
        }
        if (mRecording && !mPause) {
            pauseRecord();
        }
        if (mTXCameraRecord != null) {
            mTXCameraRecord.pauseBGM();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        TXCLog.i(TAG, "onResume");
        mScreenOrientationListener.enable();
        setSelectAspect();

        onActivityRotation();

        if (hasPermission()) {
            startCameraPreview();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        TXCLog.i(TAG, "onStop");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void bitee(BitmapBean bitmapBean){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TXCLog.i(TAG, "onDestroy");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        onActivityRotation();
        super.onConfigurationChanged(newConfig);
        if (mRecording && !mPause) {
            pauseRecord();
        }
        if (mTXCameraRecord != null) {
            mTXCameraRecord.pauseBGM();
        }
    }

    /**
     * 用来在activity随着重力感应切换方向时，切换横竖屏录制
     * 注意：使用时，录制过程中或暂停后不允许切换横竖屏，如果开始录制时使用的是横屏录制，那么整段录制都要用横屏，否则录制失败。
     */
    protected void onActivityRotation() {
        // 自动旋转打开，Activity随手机方向旋转之后，需要改变录制方向
        mMobileRotation = this.getWindowManager().getDefaultDisplay().getRotation();
        mRenderRotation = TXLiveConstants.RENDER_ROTATION_PORTRAIT; // 渲染方向，因为activity也旋转了，本地渲染相对正方向的角度为0。
        mHomeOrientation = TXLiveConstants.VIDEO_ANGLE_HOME_DOWN;
        switch (mMobileRotation) {
            case Surface.ROTATION_0:
                mHomeOrientation = TXLiveConstants.VIDEO_ANGLE_HOME_DOWN;
                break;
            case Surface.ROTATION_180:
                mHomeOrientation = TXLiveConstants.VIDEO_ANGLE_HOME_UP;
                break;
            case Surface.ROTATION_90:
                mHomeOrientation = TXLiveConstants.VIDEO_ANGLE_HOME_RIGHT;
                break;
            case Surface.ROTATION_270:
                mHomeOrientation = TXLiveConstants.VIDEO_ANGLE_HOME_LEFT;
                break;
            default:
                break;
        }
        if (mTXCameraRecord != null) {
            mTXCameraRecord.setHomeOrientation(mHomeOrientation);
            mTXCameraRecord.setRenderRotation(mRenderRotation);
        }
    }

    /**
     * 手机从home在右（左）边直接180度切换到home键在左（右）边，onConfigurationChanged是不回调的，可以用该方案解决此问题。
     */
    class ScreenOrientationListener extends OrientationEventListener {

        public ScreenOrientationListener(Context context) {
            super(context);
        }

        @Override
        public void onOrientationChanged(int orientation) {
            if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                return; // 手机平放时，检测不到有效的角度
            }

            int currentRotation = TCVideoRecordActivity.this.getWindowManager().getDefaultDisplay().getRotation();
            if (mMobileRotation == Surface.ROTATION_90 && currentRotation == Surface.ROTATION_270 && mTXCameraRecord != null) {
                // 手机从home在右边直接180度切换到home键在左边
                mHomeOrientation = TXLiveConstants.VIDEO_ANGLE_HOME_LEFT;
                mTXCameraRecord.setHomeOrientation(mHomeOrientation);
                mTXCameraRecord.setRenderRotation(mRenderRotation);
                mMobileRotation = currentRotation;
                return;
            } else if (mMobileRotation == Surface.ROTATION_270 && currentRotation == Surface.ROTATION_90 && mTXCameraRecord != null) {
                // 手机从home在左边直接180度切换到home键在边
                mHomeOrientation = TXLiveConstants.VIDEO_ANGLE_HOME_RIGHT;
                mTXCameraRecord.setHomeOrientation(mHomeOrientation);
                mTXCameraRecord.setRenderRotation(mRenderRotation);
                mMobileRotation = currentRotation;
                return;
            }

            // 只检测是否有四个角度的改变
            if (orientation > 350 || orientation < 10) {
                // 0度：手机默认竖屏状态（home键在正下方）
            } else if (orientation > 80 && orientation < 100) {
                // 90度：手机顺时针旋转90度横屏（home建在左侧）
                mHomeOrientation = TXLiveConstants.VIDEO_ANGLE_HOME_LEFT;
                // 手机从home在右边直接180度切换到home键在左边
                if (mMobileRotation == Surface.ROTATION_270 && mTXCameraRecord != null) {
                    mTXCameraRecord.setHomeOrientation(mHomeOrientation);
                    mTXCameraRecord.setRenderRotation(mRenderRotation);
                }
                mMobileRotation = Surface.ROTATION_90;

            } else if (orientation > 170 && orientation < 190) {
                // 手机顺时针旋转180度竖屏（home键在上方）
            } else if (orientation > 250 && orientation < 280) {
                // 手机顺时针旋转270度横屏，（home键在右侧）
                mHomeOrientation = TXLiveConstants.VIDEO_ANGLE_HOME_RIGHT;
                // 手机从home在左边直接180度切换到home键在右边
                if (mMobileRotation == Surface.ROTATION_90 && mTXCameraRecord != null) {
                    mTXCameraRecord.setHomeOrientation(mHomeOrientation);
                    mTXCameraRecord.setRenderRotation(mRenderRotation);
                }
                mMobileRotation = Surface.ROTATION_270;
            }
        }
    }

    private Dialog dialog;

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.back_ll) {
            back();

        } else if (i == R.id.btn_beauty) {
            mBeautyPannelView.setVisibility(mBeautyPannelView.getVisibility() == View.VISIBLE ? GONE : View.VISIBLE);
            mIvBeauty.setImageResource(mBeautyPannelView.getVisibility() == View.VISIBLE ? R.mipmap.ugc_record_beautiful_girl_hover : R.mipmap.ugc_record_beautiful_girl);
            mRecordRelativeLayout.setVisibility(mBeautyPannelView.getVisibility() == View.VISIBLE ? GONE : View.VISIBLE);

            if (mAudioCtrl.getVisibility() == View.VISIBLE) {
                mAudioCtrl.setVisibility(GONE);
                mIvMusic.setImageResource(R.mipmap.ugc_record_music);
            }

        } else if (i == R.id.btn_switch_camera2) {
//            Log.e("---------------","旋转");
            mFront = !mFront;
            mIsTorchOpen = false;
            if (mFront) {
                mIvTorch.setImageResource(R.mipmap.record_shan);
                mIvTorch.setEnabled(false);
            } else {
                mIvTorch.setImageResource(R.drawable.selector_torch_close);
                mIvTorch.setEnabled(true);
            }
            if (mTXCameraRecord != null) {
                TXCLog.e(TAG, "switchCamera = " + mFront);
                mTXCameraRecord.switchCamera(mFront);
            }

        } else if (i == R.id.compose_record_btn || i == R.id.iv_record_selete) {
            if (mAspectSelectShow) {

//                Log.e("----------","否则2");
                hideAspectSelectAnim();
                mAspectSelectShow = !mAspectSelectShow;
            } else {
//                Log.e("----------","否则");
                if (ivrecord.isSelected()) {
                    ivrecord.setSelected(false);
                } else {
                    ivrecord.setSelected(true);
                }

            }

            switchRecord();

        } else if (i == R.id.btn_music_pannel) {
            mAudioCtrl.setVisibility(mAudioCtrl.getVisibility() == View.VISIBLE ? GONE : View.VISIBLE);
            mIvMusic.setImageResource(mAudioCtrl.getVisibility() == View.VISIBLE ? R.mipmap.ugc_record_music_hover : R.mipmap.ugc_record_music);
            mRecordRelativeLayout.setVisibility(mAudioCtrl.getVisibility() == View.VISIBLE ? GONE : View.VISIBLE);

            if (mBeautyPannelView.getVisibility() == View.VISIBLE) {
                mBeautyPannelView.setVisibility(GONE);
                mIvBeauty.setImageResource(R.mipmap.ugc_record_beautiful_girl);
            }

        } else if (i == R.id.btn_confirm) {//                mCompleteProgressDialog.show();
//            mCustomProgressDialog.show();
            mIvConfirm.setImageResource(R.mipmap.ugc_confirm_disable);
            mIvConfirm.setEnabled(false);
            dialog = new Dialog(this, R.style.progress_dialog);
            dialog.setContentView(R.layout.dialog);
            dialog.setCancelable(false);
            if (dialog.getWindow() != null)
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();

            //完成   预览
            stopRecord();

        } else if (i == R.id.iv_scale) {
            scaleDisplay();

        } else if (i == R.id.iv_scale_first) {
            selectAnotherAspect(mFirstSelectScale);

        } else if (i == R.id.iv_scale_second) {
            selectAnotherAspect(mSecondSelectScale);

        } else if (i == R.id.btn_delete_last_part) {
            deleteLastPart();

        } else if (i == R.id.btn_torch) {
            toggleTorch();

        } else if (i == R.id.snapshot) {
            snapshot();

        } else if (i == R.id.tv_look) {
            //跳到预览界面的条件

            //完成   预览
            stopRecord();
            mNeedEditer = false;

        } else if (i == R.id.iv_local_tu) {
//            //本地图片
            Intent intent_gallery = new Intent(Intent.ACTION_PICK);
            intent_gallery.setType("video/*");
            startActivityForResult(intent_gallery, IMAGE_REQUEST_CODE);

            //本地视频
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            String type = "video/*";
//            Uri name = Uri.fromFile(new File(path));
//            intent.setDataAndType(name, type);
//            startActivity(intent);

//            String vo=Environment.getExternalStorageDirectory().getPath();
//            Uri uriForFile = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".FileProvider", new File(vo));
//
//            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
//            Uri data = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "");
//            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.setDataAndType(uriForFile, "video/*");
//            startActivity(intent);

//            File videoDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "USBCameraTest");


//
////            Intent intent = new Intent(Intent.ACTION_VIEW);
////            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();//该路径可以自定义
//            File videoDir = new File(Environment.getExternalStorageDirectory() /*+ "/DCIM"*/ + "/DCIM/Camera");
//
//
////
////            Uri uriForFile = FileProvider.getUriForFile(this, "com.jarhero790.eub.FileProvider",file);
//////            Uri uri = Uri.fromFile(file);
////            intent.setDataAndType(uriForFile, "video/*");
////            startActivity(intent);
//
//            Intent intent = new Intent();
//            Uri uri=null;
//            intent.setAction(Intent.ACTION_VIEW);
//            if (Build.VERSION.SDK_INT>=24){
//                uri = FileProvider.getUriForFile(getApplicationContext(), "com.jarhero790.eub.fileprovider",videoDir);
//            }else {
//                uri = Uri.fromFile(videoDir);
//
//            }
////            openAndroidFile(path);
//
////            File file = new File(filepath);
////        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//设置标记
//
//            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
////            intent.setAction(Intent.ACTION_VIEW);//动作，查看
//            intent.setDataAndType(uri, "video/mp4");
////            intent.setDataAndType(Uri.fromFile(file), CommonUtil.getMIMEType(file));//设置类型
//            startActivity(intent);


        }
    }


    public void openAndroidFile(String filepath) {
        Intent intent = new Intent();
        File file = new File(filepath);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//设置标记
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(Intent.ACTION_VIEW);//动作，查看
        intent.setDataAndType(Uri.fromFile(file), CommonUtil.getMIMEType(file));//设置类型
        startActivity(intent);
    }


    private void snapshot() {
        if (mTXCameraRecord != null) {
            mTXCameraRecord.snapshot(new TXRecordCommon.ITXSnapshotListener() {
                @Override
                public void onSnapshot(Bitmap bmp) {
                    saveBitmap(bmp);
                }
            });
        }
    }

    public static void saveBitmap(Bitmap bitmap) {
        File dir = new File("/sdcard/TXUGC/");
        if (!dir.exists())
            dir.mkdirs();
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
        String time = sdf.format(new Date(currentTime));
        File f = new File(dir, String.valueOf(time) + ".jpg");
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setSelectAspect() {
        if (mCurrentAspectRatio == TXRecordCommon.VIDEO_ASPECT_RATIO_9_16) {
            mIvScale.setImageResource(R.drawable.selector_aspect169);
            mFirstSelectScale = TXRecordCommon.VIDEO_ASPECT_RATIO_1_1;
            mIvAspectSelectFirst.setImageResource(R.drawable.selector_aspect11);

            mSecondSelectScale = TXRecordCommon.VIDEO_ASPECT_RATIO_3_4;
            mIvAspectSelectSecond.setImageResource(R.drawable.selector_aspect43);
        } else if (mCurrentAspectRatio == TXRecordCommon.VIDEO_ASPECT_RATIO_1_1) {
            mIvScale.setImageResource(R.drawable.selector_aspect11);
            mFirstSelectScale = TXRecordCommon.VIDEO_ASPECT_RATIO_3_4;
            mIvAspectSelectFirst.setImageResource(R.drawable.selector_aspect43);

            mSecondSelectScale = TXRecordCommon.VIDEO_ASPECT_RATIO_9_16;
            mIvAspectSelectSecond.setImageResource(R.drawable.selector_aspect169);
        } else {
            mIvScale.setImageResource(R.drawable.selector_aspect43);
            mFirstSelectScale = TXRecordCommon.VIDEO_ASPECT_RATIO_1_1;
            mIvAspectSelectFirst.setImageResource(R.drawable.selector_aspect11);

            mSecondSelectScale = TXRecordCommon.VIDEO_ASPECT_RATIO_9_16;
            mIvAspectSelectSecond.setImageResource(R.drawable.selector_aspect169);
        }
    }

    private void toggleTorch() {
        if (mIsTorchOpen) {
            mTXCameraRecord.toggleTorch(false);
            mIvTorch.setImageResource(R.drawable.selector_torch_close);
        } else {
            mTXCameraRecord.toggleTorch(true);
            mIvTorch.setImageResource(R.drawable.selector_torch_open);
        }
        mIsTorchOpen = !mIsTorchOpen;
    }

    private void deleteLastPart() {
        if (mRecording && !mPause) {
            return;
        }
        if (!isSelected) {
            isSelected = true;
            mRecordProgressView.selectLast();
        } else {
            isSelected = false;
            mRecordProgressView.deleteLast();
            mTXCameraRecord.getPartsManager().deleteLastPart();
            int timeSecond = mTXCameraRecord.getPartsManager().getDuration() / 1000;
            mProgressTime.setText(String.format(Locale.CHINA, "00:%02d", timeSecond));
            mProgressTime.setTag(timeSecond);
            if (timeSecond < mMinDuration / 1000) {
                mIvConfirm.setImageResource(R.mipmap.ugc_confirm_disable);
                mIvConfirm.setEnabled(false);
            } else {
                mIvConfirm.setImageResource(R.drawable.selector_record_confirm);
                mIvConfirm.setEnabled(true);
            }

            if (mTXCameraRecord.getPartsManager().getPartsPathList().size() == 0) {
                mIvScaleMask.setVisibility(GONE);
                mIvMusicMask.setVisibility(GONE);
            }
        }
    }

    private void scaleDisplay() {
        if (!mAspectSelectShow) {
            showAspectSelectAnim();
        } else {
            hideAspectSelectAnim();
        }

        mAspectSelectShow = !mAspectSelectShow;
    }

    private void selectAnotherAspect(int targetScale) {
        if (mTXCameraRecord != null) {
            scaleDisplay();

            mCurrentAspectRatio = targetScale;

            if (mCurrentAspectRatio == TXRecordCommon.VIDEO_ASPECT_RATIO_9_16) {
                mTXCameraRecord.setAspectRatio(TXRecordCommon.VIDEO_ASPECT_RATIO_9_16);

            } else if (mCurrentAspectRatio == TXRecordCommon.VIDEO_ASPECT_RATIO_3_4) {
                mTXCameraRecord.setAspectRatio(TXRecordCommon.VIDEO_ASPECT_RATIO_3_4);

            } else if (mCurrentAspectRatio == TXRecordCommon.VIDEO_ASPECT_RATIO_1_1) {
                mTXCameraRecord.setAspectRatio(TXRecordCommon.VIDEO_ASPECT_RATIO_1_1);
            }

            setSelectAspect();
        }
    }

    private void hideAspectSelectAnim() {
        ObjectAnimator showAnimator = ObjectAnimator.ofFloat(mRlAspectSelect, "translationX", 0f,
                2 * (getResources().getDimension(R.dimen.ugc_aspect_divider) + getResources().getDimension(R.dimen.ugc_aspect_width)));
        showAnimator.setDuration(80);
        showAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mRlAspectSelect.setVisibility(GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        showAnimator.start();
    }

    private void showAspectSelectAnim() {
        ObjectAnimator showAnimator = ObjectAnimator.ofFloat(mRlAspectSelect, "translationX",
                2 * (getResources().getDimension(R.dimen.ugc_aspect_divider) + getResources().getDimension(R.dimen.ugc_aspect_width)), 0f);
        showAnimator.setDuration(80);
        showAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mRlAspectSelect.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        showAnimator.start();
    }

    private void switchRecord() {
        long currentClickTime = System.currentTimeMillis();
        if (currentClickTime - mLastClickTime < 200) {
            return;
        }
        if (mRecording) {
            if (mPause) {
                if (mTXCameraRecord == null) {
                    return;
                }
                if (mTXCameraRecord.getPartsManager().getPartsPathList().size() == 0) {
                    startRecord();
                    ivrecord.setSelected(true);
                } else {
                    resumeRecord();
                }
            } else {
                if (!mEnableStop && currentClickTime - mLastClickTime < 3000) {
                    Toast.makeText(TCVideoRecordActivity.this.getApplicationContext(), "别着急，还没有录制数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                pauseRecord();
                ivrecord.setSelected(false);
            }
        } else {
            startRecord();
            ivrecord.setSelected(true);
        }
        mLastClickTime = currentClickTime;
    }

    private void resumeRecord() {
        if (mTXCameraRecord == null) {
            return;
        }
        int startResult = mTXCameraRecord.resumeRecord();
        if (startResult != TXRecordCommon.START_RECORD_OK) {
            TXCLog.i(TAG, "resumeRecord, startResult = " + startResult);
            if (startResult == TXRecordCommon.START_RECORD_ERR_NOT_INIT) {
                Toast.makeText(TCVideoRecordActivity.this.getApplicationContext(), "别着急，画面还没出来", Toast.LENGTH_SHORT).show();
            } else if (startResult == TXRecordCommon.START_RECORD_ERR_IS_IN_RECORDING) {
                Toast.makeText(TCVideoRecordActivity.this.getApplicationContext(), "还有录制的任务没有结束", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        if (!TextUtils.isEmpty(mBGMPath)) {
            if (mBGMPlayingPath == null || !mBGMPath.equals(mBGMPlayingPath)) {
                mTXCameraRecord.setBGM(mBGMPath);
                mTXCameraRecord.playBGMFromTime(0, mBGMDuration);
                mBGMPlayingPath = mBGMPath;
            } else {
                mTXCameraRecord.resumeBGM();
            }
        }

        mComposeRecordBtn.startRecord();
        ivrecord.setSelected(true);
        mIvDeleteLastPart.setImageResource(R.mipmap.ugc_delete_last_part_disable);
        mIvDeleteLastPart.setEnabled(false);
        mIvScaleMask.setVisibility(View.VISIBLE);

        mPause = false;
        isSelected = false;
        requestAudioFocus();

        mRadioGroup.setVisibility(GONE);
        mEnableStop = false;
    }

    private void pauseRecord() {
        mComposeRecordBtn.pauseRecord();
        ivrecord.setSelected(false);
        mPause = true;
        mIvDeleteLastPart.setImageResource(R.drawable.selector_delete_last_part);
        mIvDeleteLastPart.setEnabled(true);

        if (mTXCameraRecord != null) {
            if (!TextUtils.isEmpty(mBGMPlayingPath)) {
                mTXCameraRecord.pauseBGM();
            }
            mTXCameraRecord.pauseRecord();
        }
        abandonAudioFocus();

        mRadioGroup.setVisibility(View.VISIBLE);
    }

    private void stopRecord() {
        if (mTXCameraRecord != null) {
            mTXCameraRecord.stopBGM();
            mTXCameraRecord.stopRecord();
        }
        mRecording = false;
        mPause = false;

        abandonAudioFocus();

        mRadioGroup.setVisibility(View.VISIBLE);
    }

    private void startRecord() {
        // 在开始录制的时候，就不能再让activity旋转了，否则生成视频出错
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            int mobileRotation = this.getWindowManager().getDefaultDisplay().getRotation();
            switch (mobileRotation) {
                case Surface.ROTATION_90:
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    break;
                case Surface.ROTATION_270:
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                    break;
                default:
                    break;
            }
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        if (mTXCameraRecord == null) {
            mTXCameraRecord = TXUGCRecord.getInstance(this.getApplicationContext());
        }

        String customVideoPath = getCustomVideoOutputPath();
        String customCoverPath = customVideoPath.replace(".mp4", ".jpg");
        String customPartFolder = getCustomVideoPartFolder();

        int result = mTXCameraRecord.startRecord(customVideoPath, customPartFolder, customCoverPath);
        if (result != TXRecordCommon.START_RECORD_OK) {
            if (result == TXRecordCommon.START_RECORD_ERR_NOT_INIT) {
                Toast.makeText(TCVideoRecordActivity.this.getApplicationContext(), "别着急，画面还没出来", Toast.LENGTH_SHORT).show();
            } else if (result == TXRecordCommon.START_RECORD_ERR_IS_IN_RECORDING) {
                Toast.makeText(TCVideoRecordActivity.this.getApplicationContext(), "还有录制的任务没有结束", Toast.LENGTH_SHORT).show();
            } else if (result == TXRecordCommon.START_RECORD_ERR_VIDEO_PATH_IS_EMPTY) {
                Toast.makeText(TCVideoRecordActivity.this.getApplicationContext(), "传入的视频路径为空", Toast.LENGTH_SHORT).show();
            } else if (result == TXRecordCommon.START_RECORD_ERR_API_IS_LOWER_THAN_18) {
                Toast.makeText(TCVideoRecordActivity.this.getApplicationContext(), "版本太低", Toast.LENGTH_SHORT).show();
            }
            // 增加了TXUgcSDK.licence校验的返回错误码
            else if (result == TXRecordCommon.START_RECORD_ERR_LICENCE_VERIFICATION_FAILED) {
                Toast.makeText(TCVideoRecordActivity.this.getApplicationContext(), "licence校验失败", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        mComposeRecordBtn.startRecord();
        ivrecord.setSelected(true);
        mIvScaleMask.setVisibility(View.VISIBLE);
        mIvDeleteLastPart.setImageResource(R.mipmap.ugc_delete_last_part_disable);
        mIvDeleteLastPart.setEnabled(false);

        if (!TextUtils.isEmpty(mBGMPath)) {
            mBGMDuration = mTXCameraRecord.setBGM(mBGMPath);
            mTXCameraRecord.playBGMFromTime(0, mBGMDuration);
            mBGMPlayingPath = mBGMPath;
            TXCLog.i(TAG, "music duration = " + mTXCameraRecord.getMusicDuration(mBGMPath));
        }

        mRecording = true;
        mPause = false;
        requestAudioFocus();

        mIvMusicMask.setVisibility(View.VISIBLE);
        mRadioGroup.setVisibility(GONE);
        mEnableStop = false;
    }

    //自定义分段视频存储目录
    private String getCustomVideoPartFolder() {
        String outputDir = Environment.getExternalStorageDirectory() + File.separator + "txrtmp" + File.separator + "UGCParts";
        return outputDir;
    }

    private String getCustomVideoOutputPath() {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
        String time = sdf.format(new Date(currentTime));
        String outputDir = Environment.getExternalStorageDirectory() + File.separator + OUTPUT_DIR_NAME;
        File outputFolder = new File(outputDir);
        if (!outputFolder.exists()) {
            outputFolder.mkdir();
        }
        String tempOutputPath = outputDir + File.separator + "TXUGC_" + time + ".mp4";
        return tempOutputPath;
    }

    //预览界面
    private void startPreview() {
        if (mTXRecordResult != null && (mTXRecordResult.retCode == TXRecordCommon.RECORD_RESULT_OK
                || mTXRecordResult.retCode == TXRecordCommon.RECORD_RESULT_OK_REACHED_MAXDURATION
                || mTXRecordResult.retCode == TXRecordCommon.RECORD_RESULT_OK_LESS_THAN_MINDURATION)) {
//            Intent intent = new Intent(getApplicationContext(), TCVideoPreviewActivity.class);
            Intent intent = new Intent();
            intent.setAction("com.tencent.liteav.demo.videopreview");
            intent.putExtra(TCConstants.VIDEO_RECORD_TYPE, TCConstants.VIDEO_RECORD_TYPE_UGC_RECORD);
            intent.putExtra(TCConstants.VIDEO_RECORD_RESULT, mTXRecordResult.retCode);
            intent.putExtra(TCConstants.VIDEO_RECORD_DESCMSG, mTXRecordResult.descMsg);
            intent.putExtra(TCConstants.VIDEO_RECORD_VIDEPATH, mTXRecordResult.videoPath);
            intent.putExtra(TCConstants.VIDEO_RECORD_COVERPATH, mTXRecordResult.coverPath);
            intent.putExtra(TCConstants.VIDEO_RECORD_DURATION, mDuration);
            if (mRecommendQuality == TXRecordCommon.VIDEO_QUALITY_LOW) {
                intent.putExtra(TCConstants.VIDEO_RECORD_RESOLUTION, TXRecordCommon.VIDEO_RESOLUTION_360_640);
            } else if (mRecommendQuality == TXRecordCommon.VIDEO_QUALITY_MEDIUM) {
                intent.putExtra(TCConstants.VIDEO_RECORD_RESOLUTION, TXRecordCommon.VIDEO_RESOLUTION_540_960);
            } else if (mRecommendQuality == TXRecordCommon.VIDEO_QUALITY_HIGH) {
                intent.putExtra(TCConstants.VIDEO_RECORD_RESOLUTION, TXRecordCommon.VIDEO_RESOLUTION_720_1280);
            } else {
                intent.putExtra(TCConstants.VIDEO_RECORD_RESOLUTION, mRecordResolution);
            }
            startActivity(intent);

            releaseRecord();
            finish();
        }
    }

    private TXVideoEditer mTXVideoEditer;                       // SDK接口类


    //下一个
    private void startEditVideo() {
        mTXVideoEditer = new TXVideoEditer(this);
        int ret = mTXVideoEditer.setVideoPath(mTXRecordResult.videoPath);//
        if (ret != 0) {
            if (ret == TXVideoEditConstants.ERR_SOURCE_NO_TRACK) {
                DialogUtil.showDialog(this, "视频预处理失败", "不支持的视频格式", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            } else if (ret == TXVideoEditConstants.ERR_UNSUPPORT_AUDIO_FORMAT) {
                DialogUtil.showDialog(this, "视频预处理失败", "暂不支持非单双声道的视频格式", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
            return;
        }

        TCVideoEditerWrapper wrapper = TCVideoEditerWrapper.getInstance();
        wrapper.setEditer(mTXVideoEditer);

//        initPhoneListener();

        // 开始加载视频信息
        mVideoMainHandler = new VideoMainHandler(TCVideoRecordActivity.this);
        mLoadBackgroundThread = new Thread(new LoadVideoRunnable(this));
        mLoadBackgroundThread.start();
    }

    /**
     * private void startEditVideo() {
     * Intent intent = new Intent();
     * intent.setAction("com.tencent.liteav.demo.videopreprocess");
     * //        Intent intent = new Intent(this, TCVideoPreprocessActivity.class);
     * //        fileInfo.setThumbPath(mTXRecordResult.coverPath);
     * //
     * //        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
     * //        retriever.setDataSource(mTXRecordResult.videoPath);
     * //        String duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
     * //        fileInfo.setDuration(Integer.valueOf(duration) );
     * int times = (int) mProgressTime.getTag();
     * Log.e("-------", "时间" + times);
     * if (mRecommendQuality == TXRecordCommon.VIDEO_QUALITY_LOW) {
     * intent.putExtra(TCConstants.VIDEO_RECORD_RESOLUTION, TXRecordCommon.VIDEO_RESOLUTION_360_640);
     * mBiteRate = 2400;
     * } else if (mRecommendQuality == TXRecordCommon.VIDEO_QUALITY_MEDIUM) {
     * intent.putExtra(TCConstants.VIDEO_RECORD_RESOLUTION, TXRecordCommon.VIDEO_RESOLUTION_540_960);
     * mBiteRate = 6500;
     * } else if (mRecommendQuality == TXRecordCommon.VIDEO_QUALITY_HIGH) {
     * intent.putExtra(TCConstants.VIDEO_RECORD_RESOLUTION, TXRecordCommon.VIDEO_RESOLUTION_720_1280);
     * mBiteRate = 9600;
     * } else {
     * intent.putExtra(TCConstants.VIDEO_RECORD_RESOLUTION, mRecordResolution);
     * }
     * FileUtils.deleteFile(mTXRecordResult.coverPath);
     * intent.putExtra(TCConstants.RECORD_CONFIG_BITE_RATE, mBiteRate);
     * intent.putExtra(TCConstants.VIDEO_RECORD_TYPE, TCConstants.VIDEO_RECORD_TYPE_UGC_RECORD);
     * intent.putExtra(TCConstants.VIDEO_EDITER_PATH, mTXRecordResult.videoPath);
     * intent.putExtra(TCConstants.VIDEO_RECORD_COVERPATH, mTXRecordResult.coverPath);
     * intent.putExtra("time", times);
     * startActivity(intent);
     * <p>
     * releaseRecord();
     * finish();
     * }
     */

    @Override
    public void onRecordEvent(int event, Bundle param) {
        TXCLog.d(TAG, "onRecordEvent event id = " + event);
        if (event == TXRecordCommon.EVT_ID_PAUSE) {
            mRecordProgressView.clipComplete();
        } else if (event == TXRecordCommon.EVT_CAMERA_CANNOT_USE) {
            Toast.makeText(this, "摄像头打开失败，请检查权限", Toast.LENGTH_SHORT).show();
        } else if (event == TXRecordCommon.EVT_MIC_CANNOT_USE) {
            Toast.makeText(this, "麦克风打开失败，请检查权限", Toast.LENGTH_SHORT).show();
        } else if (event == TXRecordCommon.EVT_ID_RESUME) {

        }
    }

    @Override
    public void onRecordProgress(long milliSecond) {
        TXCLog.i(TAG, "onRecordProgress, mRecordProgressView = " + mRecordProgressView);
        if (mRecordProgressView == null) {
            return;
        }
        mRecordProgressView.setProgress((int) milliSecond);
        float timeSecondFloat = milliSecond / 1000f;
        int timeSecond = Math.round(timeSecondFloat);
        mProgressTime.setText(String.format(Locale.CHINA, "00:%02d", timeSecond));
        mProgressTime.setTag(timeSecond);
        if (timeSecondFloat < mMinDuration / 1000f) {
            mIvConfirm.setImageResource(R.mipmap.ugc_confirm_disable);
            mIvConfirm.setEnabled(false);
        } else {
            mIvConfirm.setImageResource(R.drawable.selector_record_confirm);
            mIvConfirm.setEnabled(true);
        }
        mEnableStop = true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /** attention to this below ,must add this**/
//        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {//是否选择，没选择就不会继续
            if (requestCode == mAudioCtrl.REQUESTCODE) {
                if (data == null) {
                    TXCLog.e(TAG, "null data");
                } else {
                    Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
                    if (mAudioCtrl != null) {
                        mAudioCtrl.processActivityResult(uri);
                    } else {
                        TXCLog.e(TAG, "NULL Pointer! Get Music Failed");
                    }
                }
            } else if (requestCode == IMAGE_REQUEST_CODE) {

                Uri imageUri = data.getData();
//                Log.e("----------------", "视频地址"+imageUri);
                File files = CommonUtil.uriToFile(imageUri, TCVideoRecordActivity.this);
//                Bitmap bitmaps=CommonUtil.getVideoThumbnail(files.getAbsolutePath());

                if (imageUri != null && files!=null) {

                     if (imageUri.toString().endsWith(".jpg") || imageUri.toString().endsWith(".gif")){
                         Toast.makeText(TCVideoRecordActivity.this, "请选择视频格式文件", Toast.LENGTH_SHORT).show();
                     }else {
                         //到编辑页面
//                        Intent intent = new Intent(this, TCVideoEditerActivity.class);
//                        // 如果是从录制过来的话，需要传递一个分辨率参数下去。
//                        intent.putExtra(TCConstants.VIDEO_RECORD_RESOLUTION, mRecommendQuality);
//                        intent.putExtra(TCConstants.VIDEO_RECORD_TYPE, TCConstants.VIDEO_RECORD_TYPE_UGC_RECORD);
//                        intent.putExtra(TCConstants.VIDEO_EDITER_PATH, files.getAbsolutePath());
//                        intent.putExtra(TCConstants.RECORD_CONFIG_BITE_RATE, 9600);
//                        intent.putExtra(TCConstants.VIDEO_EDITER_IMPORT, false);
////                        intent.putExtra(TCConstants.VIDEO_RECORD_COVERPATH, mTXRecordResult.coverPath);
//
////                        int times = (int) mProgressTime.getTag();//0
////                        intent.putExtra("time",times);
////                        Log.e("--------time","时"+times);
//                        startActivity(intent);


                         videotime = CommonUtil.getLocalVideoDuration(files.getAbsolutePath());
//                        Log.e("---videotime=", videotime + "");
                         mTXRecordResult.videoPath = files.getAbsolutePath();

//                        String width=CommonUtil.getPlayWidth(files.getAbsolutePath());
//                        String height=CommonUtil.getPlayHeight(files.getAbsolutePath());
//
//                        Log.e("-----------","宽度"+width+"    高度"+height);


//                        MediaMetadataRetriever media = new MediaMetadataRetriever();

//                        media.setDataSource(files.getAbsolutePath());// videoPath 本地视频的路径

//                        Bitmap bitmap = media.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);//第一种获取缩略图方法



                         Bitmap bitmap = TXVideoInfoReader.getInstance().getSampleImage(0, files.getAbsolutePath());//第二种获取缩略图
                         int width1 = bitmap.getWidth();
                         int height1 = bitmap.getHeight();
                         Log.e("-----------", "宽度" + width1 + "    高度" + height1);
//                        mIvBigShow.setImageBitmap(bitmap);//对应的ImageView赋值图片
                         EventBus.getDefault().post(new BitmapBean(bitmap));

                         if (width1 > height1) {
                             //模
                             istransverse = TXLiveConstants.RENDER_ROTATION_90;
                         } else {
                             istransverse = TXLiveConstants.RENDER_ROTATION_0;
                         }


                         startEditVideo();

                     }



//                    if (imageUri.toString().endsWith(".mp4") || imageUri.toString().endsWith(".avi")
//                            || imageUri.toString().endsWith(".mov") || imageUri.toString().endsWith(".rmvb")
//                            || imageUri.toString().endsWith(".rm") || imageUri.toString().endsWith(".flv")
//                            || imageUri.toString().endsWith(".3gp") || imageUri.toString().endsWith(".asf")
//                            || imageUri.toString().endsWith(".asx")) {
//
//
//                    } else {
//                        Toast.makeText(TCVideoRecordActivity.this, "请选择视频格式文件", Toast.LENGTH_SHORT).show();
//                    }
                }else {
                    Toast.makeText(TCVideoRecordActivity.this, "文件不存在", Toast.LENGTH_SHORT).show();
                }


                //获取照片路径
//                String[] filePathColumn = {MediaStore.Audio.Media.DATA};
//                Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
//                cursor.moveToFirst();
//                String photoPath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
//                setImageBitmap(photoPath);
//                cursor.close();
//                crop(imageUri);

            }
        }
    }


    /**
     * 压缩图片
     */
    private void setImageBitmap(String photoPath) {
        //获取imageview的宽和高
        int targetWidth = iv_local_tu.getWidth();
        int targetHeight = iv_local_tu.getHeight();

        //根据图片路径，获取bitmap的宽和高
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, options);
        int photoWidth = options.outWidth;
        int photoHeight = options.outHeight;

        //获取缩放比例
        int inSampleSize = 1;
        if (photoWidth > targetWidth || photoHeight > targetHeight) {
            int widthRatio = Math.round((float) photoWidth / targetWidth);
            int heightRatio = Math.round((float) photoHeight / targetHeight);
            inSampleSize = Math.min(widthRatio, heightRatio);
        }

        //使用现在的options获取Bitmap
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
        iv_local_tu.setImageBitmap(bitmap);
    }

//    TXRecordCommon.TXRecordResult mresult = null;

    @Override
    public void onRecordComplete(TXRecordCommon.TXRecordResult result) {
//        mCustomProgressDialog.dismiss();

        mTXRecordResult = result;

//        TXCLog.i(TAG, "onRecordComplete, result retCode = " + result.retCode + ", descMsg = " + result.descMsg + ", videoPath = " + result.videoPath + ", coverPath = " + result.coverPath);
        if (mTXRecordResult.retCode < 0) {
            mRecording = false;
            if (mTXCameraRecord != null) {
                int timeSecond = mTXCameraRecord.getPartsManager().getDuration() / 1000;
                mProgressTime.setText(String.format(Locale.CHINA, "00:%02d", timeSecond));
                mProgressTime.setTag(timeSecond);
            }
            Toast.makeText(TCVideoRecordActivity.this.getApplicationContext(), "录制失败，原因：" + mTXRecordResult.descMsg, Toast.LENGTH_SHORT).show();
        } else {
            mDuration = mTXCameraRecord.getPartsManager().getDuration();
            if (mTXCameraRecord != null) {
                mTXCameraRecord.getPartsManager().deleteAllParts();
            }

            if (mNeedEditer) {

                istransverse = TXLiveConstants.RENDER_ROTATION_0;
                videotime = (int) mProgressTime.getTag();
                startEditVideo();
                ///为true 时，显示预览

//                Log.e("-------------------",mNeedEditer+"这里是编辑 ");

            } else {
                startPreview();

//                Log.e("-------------------",mNeedEditer+"这里是预览 ");

            }
        }
    }

    private void requestAudioFocus() {
        if (null == mAudioManager) {
            mAudioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        }

        if (null == mOnAudioFocusListener) {
            mOnAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() {

                @Override
                public void onAudioFocusChange(int focusChange) {
                    try {
                        TXCLog.i(TAG, "requestAudioFocus, onAudioFocusChange focusChange = " + focusChange);

                        if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                            pauseRecord();
                        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                            pauseRecord();
                        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {

                        } else {
                            pauseRecord();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
        }
        try {
            mAudioManager.requestAudioFocus(mOnAudioFocusListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abandonAudioFocus() {
        try {
            if (null != mAudioManager && null != mOnAudioFocusListener) {
                mAudioManager.abandonAudioFocus(mOnAudioFocusListener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBeautyParamsChange(BeautySettingPannel.BeautyParams params, int key) {
        switch (key) {
            case BeautySettingPannel.BEAUTYPARAM_BEAUTY:
                mBeautyParams.mBeautyLevel = params.mBeautyLevel;
                mBeautyParams.mBeautyStyle = params.mBeautyStyle;
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.setBeautyDepth(mBeautyParams.mBeautyStyle, mBeautyParams.mBeautyLevel, mBeautyParams.mWhiteLevel, mBeautyParams.mRuddyLevel);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_WHITE:
                mBeautyParams.mWhiteLevel = params.mWhiteLevel;
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.setBeautyDepth(mBeautyParams.mBeautyStyle, mBeautyParams.mBeautyLevel, mBeautyParams.mWhiteLevel, mBeautyParams.mRuddyLevel);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_FACE_LIFT:
                mBeautyParams.mFaceSlimLevel = params.mFaceSlimLevel;
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.setFaceScaleLevel(params.mFaceSlimLevel);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_BIG_EYE:
                mBeautyParams.mBigEyeLevel = params.mBigEyeLevel;
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.setEyeScaleLevel(params.mBigEyeLevel);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_FILTER:
                mBeautyParams.mFilterBmp = params.mFilterBmp;
                mCurrentIndex = params.filterIndex;
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.setSpecialRatio(mBeautyPannelView.getFilterProgress(mCurrentIndex) / 10.f);
                    mTXCameraRecord.setFilter(params.mFilterBmp);
                }
                doTextAnimator();
                break;
            case BeautySettingPannel.BEAUTYPARAM_MOTION_TMPL:
                mBeautyParams.mMotionTmplPath = params.mMotionTmplPath;
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.setMotionTmpl(params.mMotionTmplPath);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_GREEN:
                mBeautyParams.mGreenFile = params.mGreenFile;
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.setGreenScreenFile(params.mGreenFile, true);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_RUDDY:
                mBeautyParams.mRuddyLevel = params.mRuddyLevel;
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.setBeautyDepth(mBeautyParams.mBeautyStyle, mBeautyParams.mBeautyLevel, mBeautyParams.mWhiteLevel, mBeautyParams.mRuddyLevel);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_FACEV:
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.setFaceVLevel(params.mFaceVLevel);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_FACESHORT:
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.setFaceShortLevel(params.mFaceShortLevel);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_CHINSLIME:
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.setChinLevel(params.mChinSlimLevel);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_NOSESCALE:
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.setNoseSlimLevel(params.mNoseScaleLevel);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_FILTER_MIX_LEVEL:
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.setSpecialRatio(params.mFilterMixLevel / 10.f);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                for (int ret : grantResults) {
                    if (ret != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                startCameraPreview();
                break;
            default:
                break;
        }
    }

    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(this,
                        permissions.toArray(new String[0]),
                        100);
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view == mMaskLayout) {
            if (motionEvent.getPointerCount() >= 2) {
                mScaleGestureDetector.onTouchEvent(motionEvent);
            } else if (motionEvent.getPointerCount() == 1) {
                mGestureDetector.onTouchEvent(motionEvent);
                // 说明是滤镜滑动后结束
                if (mStartScroll && motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    doFilterAnimator();
                }
            }
        }
        return true;
    }

    private void doFilterAnimator() {
        if (mMoveRatio >= 0.2f) { //当滑动距离达到0.2比例的时候，则说明要切换
            mIsNeedChange = true;
            if (mMoveRight) { //说明是右滑动
                mCurrentIndex--;
                mFilterAnimator = generateValueAnimator(mLeftBitmapRatio, 1);
            } else { //左滑动
                mCurrentIndex++;
                mFilterAnimator = generateValueAnimator(mLeftBitmapRatio, 0);
            }
        } else {
            if (mCurrentIndex == mLeftIndex) {//说明用户向左侧滑动
                mFilterAnimator = generateValueAnimator(mLeftBitmapRatio, 1);
            } else {
                mFilterAnimator = generateValueAnimator(mLeftBitmapRatio, 0);
            }
        }
        mFilterAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mIsDoingAnimator = true;
                if (mTXCameraRecord == null) return;
                float leftRatio = (float) valueAnimator.getAnimatedValue();
                // 动画结束
                if (leftRatio == 0 || leftRatio == 1) {
                    mLeftBitmapRatio = leftRatio;
                    if (mIsNeedChange) {
                        mIsNeedChange = false;
                        doTextAnimator();
                    } else {
                        mIsDoingAnimator = false;
                    }
                    mBeautyPannelView.setCurrentFilterIndex(mCurrentIndex);

                    // 保存到params 以便程序切换后恢复滤镜
                    if (mCurrentIndex == mLeftIndex) {
                        mBeautyParams.mFilterBmp = mLeftBitmap;
                    } else {
                        mBeautyParams.mFilterBmp = mRightBitmap;
                    }
                    mBeautyParams.mFilterMixLevel = mBeautyPannelView.getFilterProgress(mCurrentIndex);
                }
                float leftIntensity = mBeautyPannelView.getFilterProgress(mLeftIndex) / 10.f;
                float rightIntensity = mBeautyPannelView.getFilterProgress(mRightIndex) / 10.f;
                mTXCameraRecord.setFilter(
                        mLeftBitmap,
                        leftIntensity,
                        mRightBitmap,
                        rightIntensity, leftRatio
                );
            }


        });
        mFilterAnimator.start();
    }

    private ValueAnimator generateValueAnimator(float start, float end) {
        ValueAnimator animator = ValueAnimator.ofFloat(start, end);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(400);
        return animator;
    }

    private void doTextAnimator() {
        // 设置当前滤镜的名字
        mTvFilter.setText(mBeautyPannelView.getBeautyFilterArr()[mCurrentIndex]);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(400);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mTvFilter.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mTvFilter.setVisibility(View.GONE);
                mIsDoingAnimator = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mTvFilter.startAnimation(alphaAnimation);
    }

    // OnGestureListener回调start
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        mStartScroll = false;
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        if (mBeautyPannelView.isShown()) {
            mBeautyPannelView.setVisibility(GONE);
            mIvBeauty.setImageResource(R.mipmap.ugc_record_beautiful_girl);
            mRecordRelativeLayout.setVisibility(View.VISIBLE);
        }
        if (mAudioCtrl.isShown()) {
            mAudioCtrl.setVisibility(GONE);
            mIvMusic.setImageResource(R.mipmap.ugc_record_music);
            mRecordRelativeLayout.setVisibility(View.VISIBLE);
        }
        if (mTXCameraRecord != null && mTouchFocus) {
            mTXCameraRecord.setFocusPosition(motionEvent.getX(), motionEvent.getY());
        }
        return false;
    }


    @Override
    public boolean onScroll(MotionEvent downEvent, MotionEvent moveEvent, float distanceX, float distanceY) {
        if (mIsDoingAnimator) {
            return true;
        }
        boolean moveRight = moveEvent.getX() > downEvent.getX();
        if (moveRight && mCurrentIndex == 0) {
            //  Toast.makeText(TCVideoRecordActivity.this, "已经是第一个啦~", Toast.LENGTH_SHORT).show();
            return true;
        } else if (!moveRight && mCurrentIndex == mBeautyPannelView.getBeautyFilterArr().length - 1) {
            //Toast.makeText(TCVideoRecordActivity.this, "已经是最后一个啦~", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            mStartScroll = true;
            if (moveRight) {//往右滑动
                mLeftIndex = mCurrentIndex - 1;
                mRightIndex = mCurrentIndex;
            } else {// 往左滑动
                mLeftIndex = mCurrentIndex;
                mRightIndex = mCurrentIndex + 1;
            }

            if (mLastLeftIndex != mLeftIndex) { //如果不一样，才加载bitmap出来；避免滑动过程中重复加载
                mLeftBitmap = mBeautyPannelView.getFilterBitmapByIndex(mLeftIndex);
                mLastLeftIndex = mLeftIndex;
            }

            if (mLastRightIndex != mRightIndex) {//如果不一样，才加载bitmap出来；避免滑动过程中重复加载
                mRightBitmap = mBeautyPannelView.getFilterBitmapByIndex(mRightIndex);
                mLastRightIndex = mRightIndex;
            }

            int width = mVideoView.getWidth();
            float dis = moveEvent.getX() - downEvent.getX();
            float leftRatio = Math.abs(dis) / (width * 1.0f);

            float leftIntensity = mBeautyPannelView.getFilterProgress(mLeftIndex) / 10.0f;
            float rightIntensity = mBeautyPannelView.getFilterProgress(mRightIndex) / 10.0f;
            mMoveRatio = leftRatio;
            if (moveRight) {
                leftRatio = leftRatio;
            } else {
                leftRatio = 1 - leftRatio;
            }
            this.mMoveRight = moveRight;
            mLeftBitmapRatio = leftRatio;
            if (mTXCameraRecord != null)
                mTXCameraRecord.setFilter(mLeftBitmap, leftIntensity, mRightBitmap, rightIntensity, leftRatio);
            return true;
        }
    }

    @Override
    public boolean onFling(MotionEvent downEvent, MotionEvent motionEvent1, float v, float v1) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }


    // OnGestureListener回调end

    // OnScaleGestureListener回调start
    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        int maxZoom = mTXCameraRecord.getMaxZoom();
        if (maxZoom == 0) {
            TXCLog.i(TAG, "camera not support zoom");
            return false;
        }

        float factorOffset = scaleGestureDetector.getScaleFactor() - mLastScaleFactor;

        mScaleFactor += factorOffset;
        mLastScaleFactor = scaleGestureDetector.getScaleFactor();
        if (mScaleFactor < 0) {
            mScaleFactor = 0;
        }
        if (mScaleFactor > 1) {
            mScaleFactor = 1;
        }

        int zoomValue = Math.round(mScaleFactor * maxZoom);
        mTXCameraRecord.setZoom(zoomValue);
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        mLastScaleFactor = scaleGestureDetector.getScaleFactor();
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

    }
    // OnScaleGestureListener回调end

    private void releaseRecord() {
        if (mRecordProgressView != null) {
            mRecordProgressView.release();
        }

        if (mTXCameraRecord != null) {
            mTXCameraRecord.stopBGM();
            mTXCameraRecord.stopCameraPreview();
            mTXCameraRecord.setVideoRecordListener(null);
            mTXCameraRecord.getPartsManager().deleteAllParts();
            mTXCameraRecord.release();
            mTXCameraRecord = null;
            mStartPreview = false;
        }
        abandonAudioFocus();
    }

    private void back() {
        if (!mRecording) {
            releaseRecord();
            finish();
        }
        if (mPause) {
            if (mTXCameraRecord != null) {
                mTXCameraRecord.getPartsManager().deleteAllParts();
            }
            releaseRecord();
            finish();
        } else {
            pauseRecord();
        }
    }

    @Override
    public void onBackPressed() {
        back();
    }


    /**
     * 加在视频信息的runnable
     */
    private class LoadVideoRunnable implements Runnable {
        private WeakReference<TCVideoRecordActivity> mWekActivity;

        LoadVideoRunnable(TCVideoRecordActivity activity) {
            mWekActivity = new WeakReference<TCVideoRecordActivity>(activity);
        }

        @Override
        public void run() {
            if (mWekActivity == null || mWekActivity.get() == null) {
                return;
            }
            TCVideoRecordActivity activity = mWekActivity.get();
            if (activity == null) return;
            TXVideoEditConstants.TXVideoInfo info = TXVideoInfoReader.getInstance().getVideoFileInfo(mTXRecordResult.videoPath);

//            if (isCancel.get()) {
//                return;
//            }
            if (info == null) {// error 发生错误
                mVideoMainHandler.sendEmptyMessage(VideoMainHandler.LOAD_VIDEO_ERROR);
            } else {
                TCVideoEditerWrapper.getInstance().setTXVideoInfo(info);
                mVideoMainHandler.sendEmptyMessage(VideoMainHandler.LOAD_VIDEO_SUCCESS);
            }
        }
    }









}

