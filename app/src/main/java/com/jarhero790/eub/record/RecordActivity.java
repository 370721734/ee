package com.jarhero790.eub.record;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.ugc.TXRecordCommon;
import com.tencent.ugc.TXUGCRecord;
import com.jarhero790.eub.R;

public class RecordActivity extends Activity implements TXRecordCommon.ITXVideoRecordListener {


    @Override
    public void onRecordEvent(int i, Bundle bundle) {

    }

    @Override
    public void onRecordProgress(long l) {

    }

    @Override
    public void onRecordComplete(TXRecordCommon.TXRecordResult txRecordResult) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        String sdkver = TXLiveBase.getSDKVersionStr();
        Toast.makeText(this,sdkver,Toast.LENGTH_LONG).show();



        TXUGCRecord mTXCameraRecord = TXUGCRecord.getInstance(this.getApplicationContext());
        mTXCameraRecord.setVideoRecordListener(this);                    // 设置录制回调
        TXCloudVideoView mVideoView = findViewById(R.id.pusher_tx_cloud_view);    // 准备一个预览摄像头画面的

        TXRecordCommon.TXUGCSimpleConfig param = new TXRecordCommon.TXUGCSimpleConfig();
       //param.videoQuality = TXRecordCommon.VIDEO_QUALITY_LOW;        // 360p
       //param.videoQuality = TXRecordCommon.VIDEO_QUALITY_MEDIUM;        // 540p
        param.videoQuality = TXRecordCommon.VIDEO_QUALITY_HIGH;        // 720p
        param.isFront = true;           // 是否前置摄像头，使用
        param.minDuration=50000;
        param.maxDuration = 60000;    // 视频录制的最大时长ms
        param.touchFocus = false; // false为自动聚焦；true为手动聚焦
        mTXCameraRecord.startCameraSimplePreview(param,mVideoView);

// 结束画面预览
// mTXCameraRecord.stopCameraPreview();


    }
}
