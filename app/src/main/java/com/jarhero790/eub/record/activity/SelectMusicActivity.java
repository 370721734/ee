package com.jarhero790.eub.record.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jarhero790.eub.R;
import com.jarhero790.eub.bean.Video;
import com.jarhero790.eub.message.souye.AdViewPager;
import com.jarhero790.eub.record.TCConstants;
import com.jarhero790.eub.record.TCEditerUtil;
import com.jarhero790.eub.record.ffem.FileUtilss;
import com.jarhero790.eub.record.fragment.MusicSingFragment;
import com.jarhero790.eub.record.fragment.MusicZuangFragment;
import com.jarhero790.eub.record.view.NoScrollViewPager;
import com.jarhero790.eub.utils.CommonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tangyx.video.ffmpeg.FFmpegCommands;
import com.tangyx.video.ffmpeg.FFmpegRun;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.ugc.TXVideoEditConstants;
import com.tencent.ugc.TXVideoInfoReader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectMusicActivity extends AppCompatActivity {

    @BindView(R.id.m_ad_pager)
    AdViewPager mAdPager;
    @BindView(R.id.layout_ad_indicator)
    LinearLayout layoutAdIndicator;
    @BindView(R.id.iv_bot1)
    View ivBot1;
    @BindView(R.id.iv_bot2)
    View ivBot2;
    @BindView(R.id.vp)
    NoScrollViewPager vp;
    @BindView(R.id.m_swipe_layout)
    SmartRefreshLayout mSwipeLayout;

    //广告的上一个显示下标
    private int lastShowIndex;
    List<Video> lunBoTuList = new ArrayList<>();//轮播图

    int[] tu = new int[]{R.mipmap.music_1, R.mipmap.music_2, R.mipmap.music_3, R.mipmap.music_4, R.mipmap.music_5, R.mipmap.music_6};

    private String[] titles = {"专", "单"};
    private VpAdapter adapter;
    private MusicZuangFragment musicZuangFragment;
    private MusicSingFragment musicSingFragment;

    private FragmentManager manager;
    private FragmentTransaction transaction;


    private int videotime;
    private String videotype = "";
    private String mTargetPath;
    private FileUtilss mFileUtils;
    private String mVideoOutputPath;                        // 视频输出路径
    //    private TCVideoEditerWrapper mEditerWrapper;
    // 短视频SDK获取到的视频信息
//    private TXVideoEditer mTXVideoEditer;                   // SDK接口类
//    private long mVideoDuration;                            // 视频的总时长
    private int mVideoResolution = -1;                      // 分辨率类型（如果是从录制过来的话才会有，这参数）
    private int mCustomBitrate;
    private String music;
    private String middd;
    private int mVideoFrom;
    public boolean isPreviewFinish;

    //    private TXVideoEditConstants.TXGenerateResult mresult;
//    private String ms;
    private String mRecordProcessedPath;

    private String mCoverImagePath;

    private String mVideoPath;
    private int istransverse = TXLiveConstants.RENDER_ROTATION_0;

    private int fragposition=0;  //不见效

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_music);
        ButterKnife.bind(this);
        CommonUtil.setStatusBarTransparent(this);
//        mEditerWrapper = TCVideoEditerWrapper.getInstance();
//        mEditerWrapper.addTXVideoPreviewListenerWrapper(this);
        adapter = new VpAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        vp.setScroll(false);


        Intent intent = getIntent();
//        videotime = intent.getIntExtra("videotime", 0);
//        mVideoDuration=videotime;
//        istransverse=getIntent().getIntExtra(TCConstants.TRANSVERSE,0);
        videotype = intent.getStringExtra("typefabu");
        if (videotype.equals("fabu")) {
            //发布
            mFileUtils = new FileUtilss(SelectMusicActivity.this);
            mTargetPath = mFileUtils.getStorageDirectory();
            mVideoOutputPath = getCustomVideoOutputPath();
            mVideoResolution = intent.getIntExtra(TCConstants.VIDEO_RECORD_RESOLUTION, -1);
//        Log.e("----select_time=", videotime + "," + videotype);
            mCustomBitrate = getIntent().getIntExtra(TCConstants.RECORD_CONFIG_BITE_RATE, 0);
            videotime = getIntent().getIntExtra("videotime", 0) - 1;
            mVideoFrom = getIntent().getIntExtra(TCConstants.VIDEO_RECORD_TYPE, TCConstants.VIDEO_RECORD_TYPE_EDIT);
            // 录制经过预处理的视频路径，在编辑后需要删掉录制源文件
            mRecordProcessedPath = getIntent().getStringExtra(TCConstants.VIDEO_EDITER_PATH);
            mCoverImagePath = getIntent().getStringExtra(TCConstants.VIDEO_RECORD_COVERPATH);
            mVideoPath = getIntent().getStringExtra(TCConstants.VIDEO_RECORD_VIDEPATH);
            extractVideo();
        } else {
            //编辑

        }


//        mTXVideoEditer = new TXVideoEditer(this);
//        mTXVideoEditer.setVideoPath(mRecordProcessedPath);

//        mEditerWrapper = TCVideoEditerWrapper.getInstance();
//        mEditerWrapper.setEditer(mTXVideoEditer);

//        mTXVideoEditer = mEditerWrapper.getEditer();
//        if (mTXVideoEditer == null || mEditerWrapper.getTXVideoInfo() == null) {
//            Toast.makeText(this, "状态异常，结束编辑", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }
//        mVideoDuration = mEditerWrapper.getTXVideoInfo().duration;
//
//
//        mEditerWrapper.setCutterStartTime(0, mVideoDuration);


        //轮播图
        initAd();

//        manager=getSupportFragmentManager();
//        transaction=manager.beginTransaction();
//
//
//        musicZuangFragment=new MusicZuangFragment();
//        musicZuangFragment.setMusicString(new MusicZuangFragment.MusicString() {
//            @Override
//            public void Clicklinener(int position, String url) {
//                Log.e("-------------","selectmusic"+position+"  "+url);
//            }
//        });


        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
//                Log.e("--------------","changebb="+i);
                fragposition=i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        mSwipeLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {

                FragmentPagerAdapter f = (FragmentPagerAdapter) vp.getAdapter();
//                    Log.e("------------","fragposition="+fragposition);

                if (fragposition==0){
                    MusicZuangFragment mz = (MusicZuangFragment) f.instantiateItem(vp, 0);
                    mz.initDate();
                }else {
                    MusicSingFragment ms= (MusicSingFragment) f.instantiateItem(vp,1);
                    ms.initDate();
                }
                mSwipeLayout.finishRefresh(100);

            }
        });

    }

    //    private void initPlayerLayout() {
//        TXVideoEditConstants.TXPreviewParam param = new TXVideoEditConstants.TXPreviewParam();
////        param.videoView = mVideoPlayerLayout;
//        param.renderMode = TXVideoEditConstants.PREVIEW_RENDER_MODE_FILL_SCREEN;//PREVIEW_RENDER_MODE_FILL_EDGE
//        mTXVideoEditer.initWithPreview(param);
//    }
    @Override
    protected void onResume() {
        super.onResume();
//        mEditerWrapper.addTXVideoPreviewListenerWrapper(this);
    }

    private void initAd() {
        mAdPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position != lastShowIndex) {
                    if (layoutAdIndicator == null) {
                        return;
                    }
                    layoutAdIndicator.getChildAt(position).setSelected(true);
                    layoutAdIndicator.getChildAt(lastShowIndex).setSelected(false);
                    lastShowIndex = position;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        loadAd();
        loadTu();

    }

    private void loadTu() {
        layoutAdIndicator.removeAllViews();
        for (int i = 0; i < tu.length; i++) {
//            ImageView iv = new ImageView(this);
            TextView tv = new TextView(SelectMusicActivity.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.ad_indicator_drawable));
            lp.setMargins(5, 10, 5, 10);
            tv.setLayoutParams(lp);
            layoutAdIndicator.addView(tv);
        }
        mAdPager.setAdapter(new LunBoTuAdapter());
        mAdPager.startLoop();
    }

    @OnClick({R.id.back, R.id.ll1, R.id.ll2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.ll1:
                ivBot1.setVisibility(View.VISIBLE);
                ivBot2.setVisibility(View.INVISIBLE);
                vp.setCurrentItem(0);
                break;
            case R.id.ll2:
                ivBot1.setVisibility(View.INVISIBLE);
                ivBot2.setVisibility(View.VISIBLE);
                vp.setCurrentItem(1);
                break;
        }
    }

    private Dialog dialog;


    public class VpAdapter extends FragmentPagerAdapter {

        public VpAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    if (musicZuangFragment == null) {
                        musicZuangFragment = new MusicZuangFragment();


                        musicZuangFragment.setMusicString(new MusicZuangFragment.MusicString() {
                            @Override
                            public void Clicklinener(int position, String url, String mid) {
//                                Log.e("-------------1", "selectmusic" + position + "  " + url);
                                music = url;
                                middd = mid;


                                Intent intent = getIntent();
                                if (videotype.equals("fabu")) {
                                    dialog = new Dialog(SelectMusicActivity.this, R.style.progress_dialog);
                                    if (dialog.getWindow() != null)
                                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    dialog.setCancelable(true);
                                    dialog.setContentView(R.layout.dialog);
                                    dialog.show();

                                    final String path = Environment.getExternalStorageDirectory().getPath() + "/AAAAImg/";
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                downLoadFromUrl(url, "123.mp3", path);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();


                                } else {
                                    intent.putExtra("music", url);
                                    intent.putExtra("mid", mid);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }

//                                MediaPlayUtil.getInstance().start(url);
                            }
                        });

                        return musicZuangFragment;
                    }
                    break;
                case 1:
                    if (musicSingFragment == null) {
                        musicSingFragment = new MusicSingFragment();

                        musicSingFragment.setMusicString(new MusicSingFragment.MusicString() {
                            @Override
                            public void Clicklinener(int position, String url, String mid) {
//                                Log.e("-------------1","selectmusic"+position+"  "+url);
                                music = url;
                                middd = mid;
                                Intent intent = getIntent();
                                if (videotype.equals("fabu")) {
                                    dialog = new Dialog(SelectMusicActivity.this, R.style.progress_dialog);
                                    if (dialog.getWindow() != null)
                                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    dialog.setCancelable(false);
                                    dialog.setContentView(R.layout.dialog);
                                    dialog.show();

                                    final String path = Environment.getExternalStorageDirectory().getPath() + "/AAAAImg/";
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                downLoadFromUrl(url, "123.mp3", path);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();


                                } else {
                                    intent.putExtra("music", url);
                                    intent.putExtra("mid", mid);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }

//                                Intent intent = getIntent();
//                                intent.putExtra("music", url);
//                                intent.putExtra("mid", mid);
//                                setResult(RESULT_OK, intent);
//                                finish();
                            }
                        });
                        return musicSingFragment;
                    }
                    break;

                default:
                    return null;
            }

            return null;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    @Override
    protected void onStop() {
        if (dialog != null)
            dialog.dismiss();
        if (handler != null)
            handler.removeMessages(1);
        super.onStop();

    }


    //    private void loadAd() {
//        RetrofitManager.getInstance().getDataServer().getlist_lunbo2("1").enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//                    String json = null;
//                    try {
//                        json = response.body().string();
////                        Log.e("ad：", json);
//                        JSONObject object = new JSONObject(json);
//                        String success = object.optString("success");
//
//                        lunBoTuList.clear();
//                        if (success.equals("true")) {
//                            JSONArray jsonArray = object.optJSONArray("rows");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject itemobj = jsonArray.optJSONObject(i);
//                                String url = itemobj.optString("Url");
//                                String href = itemobj.optString("Href");
//                                Log.e("adfd", url + "," + href);
//                                lunBoTuList.add(new LunBoTu(url, href));
//                            }
////                            Log.e("adfs", "," + lunBoTuList.size());
//                            layoutAdIndicator.removeAllViews();
//                            if (lunBoTuList != null && lunBoTuList.size() > 0) {
//                                String[] urls = new String[lunBoTuList.size()];
//                                int count = urls.length;
//                                int itemWidth = (screenWidth - (count + 1) * 10) / count;
////                                Log.e("-----as", count + "");
//
//                                for (int i = 0; i < count; i++) {
//                                    urls[i] = lunBoTuList.get(i).getUrl();
//                                    ImageView iv = new ImageView(getActivity());
//                                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
//                                    //圆点
////                                    iv.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.ad_indicator_drawable));
////                                    lp.setMargins(5, 10, 5, 10);
////
//                                    iv.setLayoutParams(lp);
//                                    layoutAdIndicator.addView(iv);
//                                }
//                                mAdPager.setAdapter(new LunBoAdapter(urls, lunBoTuList));
//                                mAdPager.startLoop();
//
//                            }
//
//                        }
//
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//
//
//    }

//    class LunBoAdapter extends ImagePagerAdapter {
//        private List<LunBoTu> mlist;
//
//        public LunBoAdapter(String[] images, List<LunBoTu> mlist) {
//            super(images, getActivity());
//            this.mlist = mlist;
//        }
//
//
//        @Override
//        protected void onItemShow(int position, View itemView) {
//            ((ImageView) itemView).setScaleType(ImageView.ScaleType.FIT_XY);
//            itemView.setTag(R.id.m_ad_pager, mlist.get(position).getHref());//放入的是网页地址
////            Log.e("aaaaaaaa", mlist.get(position).getUrl());//图片地址
//            itemView.setOnClickListener(onAdClick);
//        }
//    }


    class LunBoTuAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return tu.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(SelectMusicActivity.this);
            imageView.setImageResource(tu[position]);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((ImageView) object);
        }
    }


    File filemusic;

    public void downLoadFromUrl(String urlStr, String fileName, String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);
        //文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
//        File file = new File(saveDir + File.separator + fileName);
        filemusic = new File(saveDir + fileName);
//

//        Log.e("----------ffff=",mRecordProcessedPath+"        "+filemusic.getAbsolutePath());
//        mMediaPath.add(filemusic.getAbsolutePath());
        cutSelectMusic(filemusic.getAbsolutePath());
//        composeAudioAndMusic(mRecordProcessedPath,filemusic.getAbsolutePath());
//        composeVideoAudio();
        FileOutputStream fos = new FileOutputStream(filemusic);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        System.out.println("info:" + url + " download success");
    }

    public byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    private List<String> mMediaPath = new ArrayList<>();

    private void cutSelectMusic(String musicUrl) {
        final String musicPath = mTargetPath + "/bgMusic.aac";
        long time = getIntent().getIntExtra("videotime", 0);//时间没有
        String[] commands = FFmpegCommands.cutIntoMusic(musicUrl, time, musicPath);
//        Log.e("-------cut", musicUrl + "   " + time + "");
//        cut: ffmpeg  /storage/emulated/0/AAAAImg123.mp3   6
        FFmpegRun.execute(commands, new FFmpegRun.FFmpegRunListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onEnd(int result) {
//                if(mMusicPlayer!=null){//移除上一个选择的音乐背景
//                    mMediaPath.remove(mMediaPath.size()-1);
//                }
                mMediaPath.add(musicPath);
                composeVideoAudio();//1
//                if (musicUrl != null)
//                    MediaPlayUtil.getInstance().start(musicPath);
//                Log.e("-------cut", musicUrl + "   " + time + "");
//               handler.sendEmptyMessage(2);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        composeVideoAudio();//1
//                    }
//                }).start();
//                startGenerate();
            }
        });
    }

    private static final String OUTPUT_DIR_NAME = "TXUGC";

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


    /**
     * 提取视频
     */
    private void extractVideo() {
        final String outVideo = mTargetPath + "/video.mp4";
//        Log.e("----------", "是不是.mp4" + outVideo);
        String[] commands = FFmpegCommands.extractVideo(mVideoPath, outVideo);
        FFmpegRun.execute(commands, new FFmpegRun.FFmpegRunListener() {
            @Override
            public void onStart() {
//                mMediaPath = new ArrayList<>();
            }

            @Override
            public void onEnd(int result) {
//                Log.e("------------houvideo=", outVideo);
                mMediaPath.add(outVideo);
                extractAudio();
            }
        });
    }


    /**
     * 提取音频
     */
    private void extractAudio() {
        final String outVideo = mTargetPath + "/audio.aac";
//        Log.e("-----------音频路径对不对", outVideo);
        String[] commands = FFmpegCommands.extractAudio(mVideoPath, outVideo);
        FFmpegRun.execute(commands, new FFmpegRun.FFmpegRunListener() {
            @Override
            public void onStart() {
//                mAudioPlayer = new MediaPlayer();
            }

            @Override
            public void onEnd(int result) {
                mMediaPath.add(outVideo);
//                String path = mMediaPath.get(0);

//                Log.e("--------------第一次音频",outVideo);

//                mVideoView.setVideoPath(path);  //播放
//                mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mediaPlayer) {
//                        mVideoView.start();
//                    }
//                });
//                mVideoView.start();
//                try {
//                    mAudioPlayer.setDataSource(mMediaPath.get(1));
//                    mAudioPlayer.setLooping(true);
//                    mAudioPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                        @Override
//                        public void onPrepared(MediaPlayer mediaPlayer) {
//                            mAudioPlayer.setVolume(0.5f, 0.5f);
//                            mAudioPlayer.start();
//                        }
//                    });
//                    mAudioPlayer.prepare();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }


    private void startGenerate() {
//        stopPlay(); // 停止播放
//        mTXVideoEditer.cancel(); // 注意：生成时，停止输出缩略图

//        mIbPlay.setImageResource(R.mipmap.ic_play);
//        if (mIsPicCombine) {
//            startGenerateVideo();//生成视频
//            return;
//        }
        startGenerateVideo();
//        AlertDialog.Builder normalDialog = new AlertDialog.Builder(TCVideoEditerActivity.this, R.style.ConfirmDialogStyle);
//        normalDialog.setMessage("生成模式");
//        normalDialog.setCancelable(true);
//        normalDialog.setNegativeButton("生成视频", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//        normalDialog.setPositiveButton("原视频转换为gif", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                startGenerateGif();
//            }
//        });
//        normalDialog.show();
    }


    private void startGenerateVideo() {
//        if (mGifStart) {
//            Toast.makeText(TCVideoEditerActivity.this, "正在生成gif，请稍后", Toast.LENGTH_SHORT).show();
//            return;
//        }
        // 处于生成状态
//        mCurrentState = PlayState.STATE_GENERATE;
        // 防止
//        mTvDone.setEnabled(false);
//        mTvDone.setClickable(false);
        // 生成视频输出路径
        mVideoOutputPath = TCEditerUtil.generateVideoPath();
//        if (mWorkLoadingProgress == null) {
//            initWorkLoadingProgress();
//        }
//        mWorkLoadingProgress.setProgress(0);
//        mWorkLoadingProgress.setCancelable(false);
//        mWorkLoadingProgress.show(getSupportFragmentManager(), "progress_dialog");

        // 添加片尾水印
//        addTailWaterMark();  //腾讯云水印

//        mTXVideoEditer.setCutFromTime(getCutterStartTime(), getCutterEndTime());
//        mTXVideoEditer.setVideoGenerateListener(this);

//        if (mCustomBitrate != 0) { // 是否自定义码率
//
//        }
//        mTXVideoEditer.setVideoBitrate(9600);
//        if (mVideoResolution == -1) {// 默认情况下都将输出720的视频
//            mTXVideoEditer.generateVideo(TXVideoEditConstants.VIDEO_COMPRESSED_720P, mVideoOutputPath);
//        } else if (mVideoResolution == TXRecordCommon.VIDEO_RESOLUTION_360_640) {
//            mTXVideoEditer.generateVideo(TXVideoEditConstants.VIDEO_COMPRESSED_360P, mVideoOutputPath);
//        } else if (mVideoResolution == TXRecordCommon.VIDEO_RESOLUTION_540_960) {
//            mTXVideoEditer.generateVideo(TXVideoEditConstants.VIDEO_COMPRESSED_540P, mVideoOutputPath);
//        } else if (mVideoResolution == TXRecordCommon.VIDEO_RESOLUTION_720_1280) {
//            mTXVideoEditer.generateVideo(TXVideoEditConstants.VIDEO_COMPRESSED_720P, mVideoOutputPath);
//        }
    }
//    private long getCutterStartTime() {
//        return mEditerWrapper.getCutterStartTime();
//    }
//
//    private long getCutterEndTime() {
//        return mEditerWrapper.getCutterEndTime();
//    }


    /**
     * 创建缩略图，并跳转至视频预览的Activity
     */
    private void createThumbFile(final TXVideoEditConstants.TXGenerateResult result) {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, String, String> task = new AsyncTask<Void, String, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                File outputVideo = new File(mVideoOutputPath);
                if (!outputVideo.exists())
                    return null;
//                Log.e("--mVideoOutputPath=4",mVideoOutputPath+"  "+outputVideo.getAbsolutePath());
                Bitmap bitmap = TXVideoInfoReader.getInstance().getSampleImage(0, mVideoOutputPath);
                if (bitmap == null)
                    return null;
                String mediaFileName = outputVideo.getAbsolutePath();
                if (mediaFileName.lastIndexOf(".") != -1) {
                    mediaFileName = mediaFileName.substring(0, mediaFileName.lastIndexOf("."));
                }
                String folder = Environment.getExternalStorageDirectory() + File.separator + TCConstants.DEFAULT_MEDIA_PACK_FOLDER + File.separator + mediaFileName;
                File appDir = new File(folder);
                if (!appDir.exists()) {
                    appDir.mkdirs();
                }

                String fileName = "thumbnail" + ".jpg";
                File file = new File(appDir, fileName);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return file.getAbsolutePath();
            }

            @Override
            protected void onPostExecute(String s) {
//                if (mVideoFrom == TCConstants.VIDEO_RECORD_TYPE_UGC_RECORD) {
//                    FileUtils.deleteFile(mRecordProcessedPath);
//                }
                //录制完成，跳到预览界面， ？？？？？？？？？？？？？？？？？？？？？
//                startPreviewActivity(result, s);
                //现在不是预览界面，是发布界面

//                mresult = result;
//                ms = s;


                dialog.dismiss();
                if (music != null && music.length() > 0) {
                    composeVideoAudio();//1
//                    Log.e("---------------","有音乐");
                } else {
                    //直接发布
//                    Log.e("---------------","无音乐");
                    Intent intent = new Intent(SelectMusicActivity.this, FaBuActivity.class);
                    intent.putExtra(TCConstants.VIDEO_RECORD_RESULT, result.retCode);
                    intent.putExtra(TCConstants.VIDEO_RECORD_DESCMSG, result.descMsg);
                    intent.putExtra(TCConstants.VIDEO_RECORD_TYPE, TCConstants.VIDEO_RECORD_TYPE_EDIT);
                    intent.putExtra(TCConstants.VIDEO_RECORD_RESULT, result.retCode);
                    intent.putExtra(TCConstants.VIDEO_RECORD_DESCMSG, result.descMsg);
                    intent.putExtra(TCConstants.VIDEO_RECORD_VIDEPATH, mVideoOutputPath);
                    intent.putExtra(TCConstants.TRANSVERSE, istransverse);
                    intent.putExtra("videotime", videotime);
//                    Log.e("---------mVideoOutputPath11=","  "+mVideoOutputPath);
                    intent.putExtra("mid", middd);
                    if (s != null)
                        intent.putExtra(TCConstants.VIDEO_RECORD_COVERPATH, s);
//                    intent.putExtra(TCConstants.VIDEO_RECORD_DURATION, getCutterEndTime() - getCutterStartTime());
                    startActivity(intent);
                    finish();
                }


                //预览界面
//                private void startPreview() {
//                    if (mTXRecordResult != null && (mTXRecordResult.retCode == TXRecordCommon.RECORD_RESULT_OK
//                            || mTXRecordResult.retCode == TXRecordCommon.RECORD_RESULT_OK_REACHED_MAXDURATION
//                            || mTXRecordResult.retCode == TXRecordCommon.RECORD_RESULT_OK_LESS_THAN_MINDURATION)) {
////            Intent intent = new Intent(getApplicationContext(), TCVideoPreviewActivity.class);
//                        Intent intent = new Intent();
//                        intent.setAction("com.tencent.liteav.demo.videopreview");
//                        intent.putExtra(TCConstants.VIDEO_RECORD_TYPE, TCConstants.VIDEO_RECORD_TYPE_UGC_RECORD);
//                        intent.putExtra(TCConstants.VIDEO_RECORD_RESULT, mTXRecordResult.retCode);
//                        intent.putExtra(TCConstants.VIDEO_RECORD_DESCMSG, mTXRecordResult.descMsg);
//                        intent.putExtra(TCConstants.VIDEO_RECORD_VIDEPATH, mTXRecordResult.videoPath);
//                        intent.putExtra(TCConstants.VIDEO_RECORD_COVERPATH, mTXRecordResult.coverPath);
//                        intent.putExtra(TCConstants.VIDEO_RECORD_DURATION, mDuration);
//                        if (mRecommendQuality == TXRecordCommon.VIDEO_QUALITY_LOW) {
//                            intent.putExtra(TCConstants.VIDEO_RECORD_RESOLUTION, TXRecordCommon.VIDEO_RESOLUTION_360_640);
//                        } else if (mRecommendQuality == TXRecordCommon.VIDEO_QUALITY_MEDIUM) {
//                            intent.putExtra(TCConstants.VIDEO_RECORD_RESOLUTION, TXRecordCommon.VIDEO_RESOLUTION_540_960);
//                        } else if (mRecommendQuality == TXRecordCommon.VIDEO_QUALITY_HIGH) {
//                            intent.putExtra(TCConstants.VIDEO_RECORD_RESOLUTION, TXRecordCommon.VIDEO_RESOLUTION_720_1280);
//                        } else {
//                            intent.putExtra(TCConstants.VIDEO_RECORD_RESOLUTION, mRecordResolution);
//                        }
//                        startActivity(intent);
//
//                        releaseRecord();
//                        finish();
//                    }
//                }
            }

        };
        task.execute();
    }


    /**
     * 处理视频原声
     */
    private void composeVideoAudio() {//2

        String audioUrl = mMediaPath.get(1);
//        Log.e("---------audio=", audioUrl);
        final String audioOutUrl = mTargetPath + "/tempAudio.aac";
        String[] common = FFmpegCommands.changeAudioOrMusicVol(audioUrl, 100, audioOutUrl);
//        Log.e("---------vol1=", common[3]);
        FFmpegRun.execute(common, new FFmpegRun.FFmpegRunListener() {
            @Override
            public void onStart() {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onEnd(int result) {
//                composeMusicAndAudio(audioOutUrl);
                composeVideoMusic(audioOutUrl);
//                Log.e("---------", "3" + audioOutUrl);
//                if (mMediaPath.size() == 3) {
//                    composeVideoMusic(audioOutUrl);
//                    Log.e("---------","3"+audioOutUrl);
//                } else {
//                    composeMusicAndAudio(audioOutUrl);
//                    Log.e("---------","1");
//                }
            }
        });
    }


    /**
     * 处理背景音乐
     */
    private void composeVideoMusic(final String audioUrl) {
//        final int mMusicVol = mMusicSeekBar.getProgress();
        String musicUrl;
        if (audioUrl == null) {
            musicUrl = mMediaPath.get(1);
        } else {
            musicUrl = mMediaPath.get(2);
        }
//        Log.e("-------------",musicUrl+"音乐地址对不对");
        final String musicOutUrl = mTargetPath + "/tempMusic.aac";
        final String[] common = FFmpegCommands.changeAudioOrMusicVol(musicUrl, 99, musicOutUrl);
//        Log.e("---------vol2=",common[3]);
        FFmpegRun.execute(common, new FFmpegRun.FFmpegRunListener() {
            @Override
            public void onStart() {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onEnd(int result) {
//                Log.e("---------vol3=", common[3]);
//                composeAudioAndMusic(audioUrl, musicOutUrl);

                composeMusicAndAudio(musicOutUrl);
            }
        });
    }


    /**
     * 合成原声和背景音乐
     */
    public void composeAudioAndMusic(String audioUrl, String musicUrl) {
        if (audioUrl == null) {
            composeMusicAndAudio(musicUrl);
        } else {
            final String musicAudioPath = mTargetPath + "/audioMusic.aac";
            String[] common = FFmpegCommands.composeAudio(audioUrl, musicUrl, musicAudioPath);
            FFmpegRun.execute(common, new FFmpegRun.FFmpegRunListener() {
                @Override
                public void onStart() {
                    handler.sendEmptyMessage(0);
                }

                @Override
                public void onEnd(int result) {
                    composeMusicAndAudio(musicAudioPath);
                }
            });
        }
    }

    private void composeMusicAndAudio(String bgMusicAndAudio) {
        final String videoAudioPath = mTargetPath + "/videoMusicAudio.mp4";
        final String videoUrl = mMediaPath.get(0);   //是视频地址
//        final int time = getIntent().getIntExtra("time", 0) - 1;
        String[] common = FFmpegCommands.composeVideo(videoUrl, bgMusicAndAudio, videoAudioPath, videotime);
//        Log.e("--------------", videoAudioPath + "    " + videotime);
        FFmpegRun.execute(common, new FFmpegRun.FFmpegRunListener() {
            @Override
            public void onStart() {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onEnd(int result) {
                handleVideoNext(videoAudioPath);
            }
        });
    }


    /**
     * 适配处理完成，进入下一步
     */
    private void handleVideoNext(String videoUrl) {
        Message message = new Message();
        message.what = 1;
        message.obj = videoUrl;
        handler.sendMessage(message);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
//                    showProgressLoading();
                    break;
                case 1:
//                    dismissProgress();
                    String videoPath = (String) msg.obj;
//                    Log.e("-------------video=3333", videoPath);
//                    Intent intent = new Intent(TCVideoEditerActivity.this,MakeVideoActivity.class);
//                    intent.putExtra("path",videoPath);
//                    intent.putExtra("isPlayer",true);
//                    startActivity(intent);
//                    finish();


                    Intent intent = new Intent(SelectMusicActivity.this, FaBuActivity.class);
                    intent.putExtra(TCConstants.VIDEO_RECORD_TYPE, TCConstants.VIDEO_RECORD_TYPE_UGC_RECORD);
//                        intent.putExtra(TCConstants.VIDEO_RECORD_RESULT, mresult.retCode);
//                        intent.putExtra(TCConstants.VIDEO_RECORD_DESCMSG, mresult.descMsg);
                    intent.putExtra(TCConstants.VIDEO_RECORD_TYPE, TCConstants.VIDEO_RECORD_TYPE_EDIT);
//                        intent.putExtra(TCConstants.VIDEO_RECORD_RESULT, mresult.retCode);
//                        intent.putExtra(TCConstants.VIDEO_RECORD_DESCMSG, mresult.descMsg);
                    intent.putExtra(TCConstants.VIDEO_RECORD_VIDEPATH, videoPath);
                    intent.putExtra(TCConstants.VIDEO_RECORD_RESOLUTION, mVideoResolution);
                    intent.putExtra("videotime", videotime);
//                        Log.e("--mVideoOutputPath11=",videoPath+"  "+mVideoOutputPath);
                    intent.putExtra("mid", middd);
                    if (mCoverImagePath != null)
                        intent.putExtra(TCConstants.VIDEO_RECORD_COVERPATH, mCoverImagePath);
//                        intent.putExtra(TCConstants.VIDEO_RECORD_DURATION, getCutterEndTime() - getCutterStartTime());
                    startActivity(intent);
                    finish();


                    break;
                case 2:
//                    dismissProgress();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            composeVideoAudio();//1
                        }
                    }).start();

                    break;
            }
        }
    };


//    public interface OnHideKeyboardListener{
//        public boolean hideKeyboard();
//    }
//    private OnHideKeyboardListener onHideKeyboardListener;
//    public void setOnHideKeyboardListener(OnHideKeyboardListener onHideKeyboardListener){
//        this.onHideKeyboardListener = onHideKeyboardListener;
//    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            //当isShouldHideInput(v, ev)为true时，表示的是点击输入框区域，则需要显示键盘，同时显示光标，反之，需要隐藏键盘、光标
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    //处理Editext的光标隐藏、显示逻辑
//                    mEdtFind.clearFocus();
                    FragmentPagerAdapter f = (FragmentPagerAdapter) vp.getAdapter();
//                    Log.e("------------","fragposition="+fragposition);
                    MusicZuangFragment mz = (MusicZuangFragment) f.instantiateItem(vp, 0);
                    mz.setZuang(new MusicZuangFragment.Zuang() {
                        @Override
                        public void Clickenear(EditText etSearch) {
                            etSearch.clearFocus();
//                            imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
                        }
                    });

//                    if (fragposition==0){
//                        MusicZuangFragment mz= (MusicZuangFragment) f.instantiateItem(vp,fragposition);
//                        mz.setZuang(new MusicZuangFragment.Zuang() {
//                            @Override
//                            public void Clickenear(EditText etSearch) {
//                                etSearch.clearFocus();
//                            }
//                        });
//
//                    }else {
//                         MusicSingFragment ms= (MusicSingFragment) f.instantiateItem(vp,fragposition);
//                         ms.setZuang(new MusicSingFragment.Zuang() {
//                             @Override
//                             public void Clickenear(EditText etSearch) {
//                                 etSearch.clearFocus();
//                             }
//                         });
//                    }


                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


}
