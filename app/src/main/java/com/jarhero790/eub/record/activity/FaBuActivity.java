package com.jarhero790.eub.record.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jarhero790.eub.R;
import com.jarhero790.eub.record.TCConstants;
import com.jarhero790.eub.record.TCVideoView;
import com.jarhero790.eub.record.view.MediaPlayUtil;
import com.jarhero790.eub.utils.CommonUtil;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.io.File;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fa_bu);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
        mTXCloudVideoView = (TXCloudVideoView) findViewById(R.id.video_view);
        mImageViewBg = (ImageView) findViewById(R.id.cover);
        mStartPreview = (ImageView) findViewById(R.id.record_preview);


        mVideoSource = getIntent().getIntExtra(TCConstants.VIDEO_RECORD_TYPE, TCConstants.VIDEO_RECORD_TYPE_EDIT);
        mVideoPath = getIntent().getStringExtra(TCConstants.VIDEO_RECORD_VIDEPATH);
        mCoverImagePath = getIntent().getStringExtra(TCConstants.VIDEO_RECORD_COVERPATH);
        mVideoDuration = getIntent().getLongExtra(TCConstants.VIDEO_RECORD_DURATION, 0);
        mVideoResolution = getIntent().getIntExtra(TCConstants.VIDEO_RECORD_RESOLUTION, -1);
        Log.e("-------", "onCreate: mVideoPath = " + mVideoPath + ",mVideoDuration = " + mVideoDuration);

        if (mCoverImagePath != null && !mCoverImagePath.isEmpty()) {
            Glide.with(this).load(Uri.fromFile(new File(mCoverImagePath)))
                    .into(mImageViewBg);
        }

        mTXVodPlayer = new TXVodPlayer(this);
        mTXPlayConfig = new TXVodPlayConfig();
        mTXCloudVideoView.disableLog(true);

    }

    @OnClick({R.id.back, R.id.tv_fabu, R.id.iv_music, R.id.submit,R.id.record_preview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_fabu:
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
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaPlayUtil.getInstance().release();
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
