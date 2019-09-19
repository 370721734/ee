package com.jarhero790.eub.message.attention;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dueeeke.videocontroller.BatteryReceiver;
import com.dueeeke.videocontroller.MarqueeTextView;
import com.dueeeke.videoplayer.controller.GestureVideoController;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.util.L;
import com.dueeeke.videoplayer.util.PlayerUtils;
import com.jarhero790.eub.R;

public class StandardVideo extends GestureVideoController implements View.OnClickListener {
    protected TextView mTotalTime, mCurrTime;
    protected ImageView mFullScreenButton;
    protected LinearLayout mBottomContainer, mTopContainer;
    protected ImageView mBackButton;
    protected ImageView mLockButton;
    protected MarqueeTextView mTitle;

    private ImageView mPlayButton;
    private ImageView mStartPlayButton;
    private ProgressBar mLoadingProgress;
    private ImageView mThumb;
    private FrameLayout mCompleteContainer;
    private ImageView mStopFullscreen;
    private TextView mSysTime;//系统当前时间
    private ImageView mBatteryLevel;//电量
//    private Animation mShowAnim = AnimationUtils.loadAnimation(getContext(), R.anim.dkplayer_anim_alpha_in);
//    private Animation mHideAnim = AnimationUtils.loadAnimation(getContext(), R.anim.dkplayer_anim_alpha_out);
    private BatteryReceiver mBatteryReceiver;
    protected ImageView mRefreshButton;
    public StandardVideo(@NonNull Context context) {
        super(context);
    }

    public StandardVideo(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StandardVideo(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dkplayer_layout_standard_controller;
    }


    @Override
    protected void initView() {
        super.initView();
        mFullScreenButton = mControllerView.findViewById(R.id.fullscreen);
        mFullScreenButton.setOnClickListener(this);
        mBottomContainer = mControllerView.findViewById(R.id.bottom_container);//隐藏
        mBottomContainer.setVisibility(View.GONE);
        mTopContainer = mControllerView.findViewById(R.id.top_container);
        mTotalTime = mControllerView.findViewById(R.id.total_time);
        mCurrTime = mControllerView.findViewById(R.id.curr_time);
        mBackButton = mControllerView.findViewById(R.id.back);
        mBackButton.setOnClickListener(this);
        mLockButton = mControllerView.findViewById(R.id.lock);
        mLockButton.setOnClickListener(this);
        mThumb = mControllerView.findViewById(R.id.thumb);
        mThumb.setOnClickListener(this);
        mPlayButton = mControllerView.findViewById(R.id.iv_play);
        mPlayButton.setOnClickListener(this);
        mStartPlayButton = mControllerView.findViewById(R.id.start_play);
        mStartPlayButton.setVisibility(View.GONE);
        mLoadingProgress = mControllerView.findViewById(R.id.loading);
        mLoadingProgress.setVisibility(View.GONE);
        ImageView rePlayButton = mControllerView.findViewById(R.id.iv_replay);
        rePlayButton.setOnClickListener(this);
        mCompleteContainer = mControllerView.findViewById(R.id.complete_container);
        mCompleteContainer.setOnClickListener(this);
        mStopFullscreen = mControllerView.findViewById(R.id.stop_fullscreen);
        mStopFullscreen.setOnClickListener(this);
        mTitle = mControllerView.findViewById(R.id.title);
        mSysTime = mControllerView.findViewById(R.id.sys_time);
        mBatteryLevel = mControllerView.findViewById(R.id.iv_battery);
        mBatteryReceiver = new BatteryReceiver(mBatteryLevel);
        mRefreshButton = mControllerView.findViewById(R.id.iv_refresh);
        mRefreshButton.setOnClickListener(this);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getContext().unregisterReceiver(mBatteryReceiver);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getContext().registerReceiver(mBatteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_play || i == R.id.thumb) {
            doPauseResume();
        }
    }




    @Override
    public void setPlayerState(int playerState) {
        switch (playerState) {
            case VideoView.PLAYER_NORMAL:
                L.e("PLAYER_NORMAL");
                if (mIsLocked) return;
                setLayoutParams(new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                mIsGestureEnabled = false;
                mFullScreenButton.setSelected(false);
                mBackButton.setVisibility(GONE);
                mLockButton.setVisibility(GONE);
                mTitle.setVisibility(INVISIBLE);
                mTitle.setNeedFocus(false);
                mSysTime.setVisibility(GONE);
                mBatteryLevel.setVisibility(GONE);
                mTopContainer.setVisibility(GONE);
                mStopFullscreen.setVisibility(GONE);
                break;
            case VideoView.PLAYER_FULL_SCREEN:
                L.e("PLAYER_FULL_SCREEN");
                if (mIsLocked) return;
                mIsGestureEnabled = true;
                mFullScreenButton.setSelected(true);
                mBackButton.setVisibility(VISIBLE);
                mTitle.setVisibility(VISIBLE);
                mTitle.setNeedFocus(true);
                mSysTime.setVisibility(VISIBLE);
                mBatteryLevel.setVisibility(VISIBLE);
                mStopFullscreen.setVisibility(VISIBLE);
                if (mShowing) {
                    mLockButton.setVisibility(VISIBLE);
                    mTopContainer.setVisibility(VISIBLE);
                } else {
                    mLockButton.setVisibility(GONE);
                }
                break;
        }
    }

    @Override
    public void setPlayState(int playState) {
        super.setPlayState(playState);
        switch (playState) {
            case VideoView.STATE_IDLE:
                L.e("STATE_IDLE");
                hide();
                mIsLocked = false;
                mLockButton.setSelected(false);
                mMediaPlayer.setLock(false);
                mCompleteContainer.setVisibility(GONE);
                mLoadingProgress.setVisibility(GONE);
                mStartPlayButton.setVisibility(GONE);
                mThumb.setVisibility(VISIBLE);
                break;
            case VideoView.STATE_PLAYING:
                L.e("STATE_PLAYING");
                post(mShowProgress);
                mPlayButton.setSelected(true);
                mLoadingProgress.setVisibility(GONE);
                mCompleteContainer.setVisibility(GONE);
                mThumb.setVisibility(GONE);
                mStartPlayButton.setVisibility(GONE);
                break;
            case VideoView.STATE_PAUSED:
                L.e("STATE_PAUSED");
                mPlayButton.setSelected(false);
                mStartPlayButton.setVisibility(GONE);
                break;
            case VideoView.STATE_PREPARING:
                L.e("STATE_PREPARING");
                mCompleteContainer.setVisibility(GONE);
                mStartPlayButton.setVisibility(GONE);
                mLoadingProgress.setVisibility(GONE);
                break;
            case VideoView.STATE_PREPARED:
                L.e("STATE_PREPARED");
                mStartPlayButton.setVisibility(GONE);
                break;
            case VideoView.STATE_ERROR:
                L.e("STATE_ERROR");
                mStartPlayButton.setVisibility(GONE);
                mLoadingProgress.setVisibility(GONE);
                mThumb.setVisibility(GONE);
                mTopContainer.setVisibility(GONE);
                break;
            case VideoView.STATE_BUFFERING:
                L.e("STATE_BUFFERING");
                mStartPlayButton.setVisibility(GONE);
                mLoadingProgress.setVisibility(GONE);
                mThumb.setVisibility(GONE);
                mPlayButton.setSelected(mMediaPlayer.isPlaying());
                break;
            case VideoView.STATE_BUFFERED:
                L.e("STATE_BUFFERED");
                mLoadingProgress.setVisibility(GONE);
                mStartPlayButton.setVisibility(GONE);
                mThumb.setVisibility(GONE);
                mPlayButton.setSelected(mMediaPlayer.isPlaying());
                break;
            case VideoView.STATE_PLAYBACK_COMPLETED:
                L.e("STATE_PLAYBACK_COMPLETED");
                hide();
                removeCallbacks(mShowProgress);
                mStartPlayButton.setVisibility(GONE);
                mThumb.setVisibility(VISIBLE);
                mCompleteContainer.setVisibility(VISIBLE);
                mStopFullscreen.setVisibility(mMediaPlayer.isFullScreen() ? VISIBLE : GONE);
                mIsLocked = false;
                mMediaPlayer.setLock(false);
                break;
        }
    }


//    protected void doLockUnlock() {
//        if (mIsLocked) {
//            mIsLocked = false;
//            mShowing = false;
//            mIsGestureEnabled = true;
//            show();
//            mLockButton.setSelected(false);
//            Toast.makeText(getContext(), R.string.dkplayer_unlocked, Toast.LENGTH_SHORT).show();
//        } else {
//            hide();
//            mIsLocked = true;
//            mIsGestureEnabled = false;
//            mLockButton.setSelected(true);
//            Toast.makeText(getContext(), R.string.dkplayer_locked, Toast.LENGTH_SHORT).show();
//        }
//        mMediaPlayer.setLock(mIsLocked);
//    }









    @Override
    public void hide() {
        if (mShowing) {
            if (mMediaPlayer.isFullScreen()) {
                mLockButton.setVisibility(GONE);
                if (!mIsLocked) {
                    hideAllViews();
                }
            } else {
                mBottomContainer.setVisibility(GONE);
//                mBottomContainer.startAnimation(mHideAnim);
            }

            mShowing = false;
        }
    }

    private void hideAllViews() {
        mTopContainer.setVisibility(GONE);
//        mTopContainer.startAnimation(mHideAnim);
        mBottomContainer.setVisibility(GONE);
//        mBottomContainer.startAnimation(mHideAnim);
    }

    private void show(int timeout) {
        if (mSysTime != null)
            mSysTime.setText(getCurrentSystemTime());
        if (!mShowing) {
            if (mMediaPlayer.isFullScreen()) {
                mLockButton.setVisibility(VISIBLE);
                if (!mIsLocked) {
                    showAllViews();
                }
            } else {
                mBottomContainer.setVisibility(GONE);
//                mBottomContainer.startAnimation(mShowAnim);
            }

            mShowing = true;
        }
        removeCallbacks(mFadeOut);
        if (timeout != 0) {
            postDelayed(mFadeOut, timeout);
        }
    }

    private void showAllViews() {
        mBottomContainer.setVisibility(GONE);
//        mBottomContainer.startAnimation(mShowAnim);
        mTopContainer.setVisibility(VISIBLE);
//        mTopContainer.startAnimation(mShowAnim);
    }

    @Override
    public void show() {
        show(mDefaultTimeout);
    }

    @Override
    protected int setProgress() {
        if (mMediaPlayer == null) {
            return 0;
        }



        int position = (int) mMediaPlayer.getCurrentPosition();
        int duration = (int) mMediaPlayer.getDuration();


        if (mTotalTime != null)
            mTotalTime.setText(stringForTime(duration));
        if (mCurrTime != null)
            mCurrTime.setText(stringForTime(position));

        return position;
    }


    @Override
    protected void slideToChangePosition(float deltaX) {
        super.slideToChangePosition(deltaX);

    }

    public ImageView getThumb() {
        return mThumb;
    }

    @Override
    public boolean onBackPressed() {
        if (mIsLocked) {
            show();
            Toast.makeText(getContext(), R.string.dkplayer_lock_tip, Toast.LENGTH_SHORT).show();
            return true;
        }

        Activity activity = PlayerUtils.scanForActivity(getContext());
        if (activity == null) return super.onBackPressed();

        if (mMediaPlayer.isFullScreen()) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mMediaPlayer.stopFullScreen();
            return true;
        }
        return super.onBackPressed();
    }
}
