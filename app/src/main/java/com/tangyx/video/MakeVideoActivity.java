package com.tangyx.video;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.jarhero790.eub.R;
import com.tangyx.video.ffmpeg.FFmpegCommands;
import com.tangyx.video.ffmpeg.FFmpegRun;
import com.tangyx.video.utils.FileUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tangyx
 * Date 2017/8/2
 * email tangyx@live.com
 */
public class MakeVideoActivity extends AppCompatActivity implements View.OnClickListener,SeekBar.OnSeekBarChangeListener{
    private final static String TAG="SLog";
    private VideoView mVideoView;
    private TextView mNext;
    private AppCompatSeekBar mAudioSeekBar;
    private AppCompatSeekBar mMusicSeekBar;
    private MediaPlayer mAudioPlayer;
    private MediaPlayer mMusicPlayer;
    private List<String> mMediaPath;
    private String mTargetPath;
    private FileUtils mFileUtils;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_make_video);
//        mVideoView = (VideoView) findViewById(R.id.video);
//        mAudioSeekBar = (AppCompatSeekBar) findViewById(R.id.video_seek_bar);
//        mMusicSeekBar = (AppCompatSeekBar) findViewById(R.id.music_seek_bar);
//        mNext = (TextView) findViewById(R.id.next);
//        mAudioSeekBar.setOnSeekBarChangeListener(this);
//        mMusicSeekBar.setOnSeekBarChangeListener(this);
//        mNext.setOnClickListener(this);
//        findViewById(R.id.back).setOnClickListener(this);
//        findViewById(R.id.local_music).setOnClickListener(this);
//        boolean isPlayer = getIntent().getBooleanExtra("isPlayer", false);
//        Log.e(TAG,"isPlayer:"+isPlayer);
//        if (isPlayer) {
//            findViewById(R.id.title_layout).setVisibility(View.GONE);
//            findViewById(R.id.editor_layout).setVisibility(View.GONE);
//            mVideoView.setVideoPath(getIntent().getStringExtra("path"));
//            mVideoView.start();
//        }else{
//            mFileUtils = new FileUtils(this);
//            mTargetPath = mFileUtils.getStorageDirectory();
//            extractVideo();
//        }
    }


    /**
     * 提取视频
     */
    private void extractVideo() {
        final String outVideo = mTargetPath + "/video.mp4";
        String[] commands = FFmpegCommands.extractVideo(getIntent().getStringExtra("path"), outVideo);
        FFmpegRun.execute(commands, new FFmpegRun.FFmpegRunListener() {
            @Override
            public void onStart() {
                mMediaPath = new ArrayList<>();
                Log.e(TAG,"extractVideo ffmpeg start...");
            }

            @Override
            public void onEnd(int result) {
                Log.e(TAG,"extractVideo ffmpeg end...");
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
        String[] commands = FFmpegCommands.extractAudio(getIntent().getStringExtra("path"), outVideo);
        FFmpegRun.execute(commands, new FFmpegRun.FFmpegRunListener() {
            @Override
            public void onStart() {
                mAudioPlayer = new MediaPlayer();
            }

            @Override
            public void onEnd(int result) {
                Log.e(TAG,"extractAudio ffmpeg end...");
                mMediaPath.add(outVideo);
                String path = mMediaPath.get(0);
                mVideoView.setVideoPath(path);
                mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mVideoView.start();
                    }
                });
                mVideoView.start();
                try {
                    mAudioPlayer.setDataSource(mMediaPath.get(1));
                    mAudioPlayer.setLooping(true);
                    mAudioPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mAudioPlayer.setVolume(0.5f, 0.5f);
                            mAudioPlayer.start();
                        }
                    });
                    mAudioPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void cutSelectMusic(String musicUrl) {
        final String musicPath = mTargetPath + "/bgMusic.aac";
        long time = getIntent().getIntExtra("time",0);
        String[] commands = FFmpegCommands.cutIntoMusic(musicUrl, time, musicPath);
        Log.e("------------",musicUrl+"   "+time);
        FFmpegRun.execute(commands, new FFmpegRun.FFmpegRunListener() {
            @Override
            public void onStart() {
                    Log.e(TAG,"cutSelectMusic ffmpeg start...");
            }

            @Override
            public void onEnd(int result) {
                Log.e(TAG,"cutSelectMusic ffmpeg end...");
                if(mMusicPlayer!=null){//移除上一个选择的音乐背景
                    mMediaPath.remove(mMediaPath.size()-1);
                }
                mMediaPath.add(musicPath);
                Log.e("-----------music_end=",musicPath);
                stopMediaPlayer();
                mMusicPlayer = new MediaPlayer();
                try {
                    mMusicPlayer.setDataSource(musicPath);
                    mMusicPlayer.setLooping(true);
                    mMusicPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.setVolume(0.5f, 0.5f);
                            mediaPlayer.start();
                            mMusicSeekBar.setProgress(50);
                        }
                    });
                    mMusicPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                mFileUtils.deleteFile(mTargetPath,null);
                break;
//            case R.id.local_music:
//                Intent intent = new Intent(this, MusicActivity.class);
//                startActivityForResult(intent,0);
//                break;
//            case R.id.next:
//                composeVideoAudio();
//                mNext.setTextColor(Color.parseColor("#999999"));
//                mNext.setEnabled(false);
//                break;
        }
    }

    /**
     * 处理视频原声
     */
    private void composeVideoAudio() {
        int mAudioVol = mAudioSeekBar.getProgress();
        String audioUrl = mMediaPath.get(1);
        Log.e("---------audio=",audioUrl);
        final String audioOutUrl = mTargetPath + "/tempAudio.aac";
        String[] common = FFmpegCommands.changeAudioOrMusicVol(audioUrl, mAudioVol * 10, audioOutUrl);
        FFmpegRun.execute(common, new FFmpegRun.FFmpegRunListener() {
            @Override
            public void onStart() {
                Log.e(TAG,"changeAudioVol ffmpeg start...");
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onEnd(int result) {
                Log.e(TAG,"changeAudioVol ffmpeg end...");
                if (mMediaPath.size() == 3) {
                    composeVideoMusic(audioOutUrl);
                } else {
                    composeMusicAndAudio(audioOutUrl);
                }
            }
        });
    }

    /**
     * 处理背景音乐
     */
    private void composeVideoMusic(final String audioUrl) {
        final int mMusicVol = mMusicSeekBar.getProgress();
        String musicUrl;
        if (audioUrl == null) {
            musicUrl = mMediaPath.get(1);
        } else {
            musicUrl = mMediaPath.get(2);
        }
        final String musicOutUrl = mTargetPath + "/tempMusic.aac";
        final String[] common = FFmpegCommands.changeAudioOrMusicVol(musicUrl, mMusicVol * 10, musicOutUrl);
        FFmpegRun.execute(common, new FFmpegRun.FFmpegRunListener() {
            @Override
            public void onStart() {
                Log.e(TAG,"changeMusicVol ffmpeg start...");
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onEnd(int result) {
                Log.e(TAG,"changeMusicVol ffmpeg end...");
                composeAudioAndMusic(audioUrl, musicOutUrl);
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
                    Log.e(TAG,"composeAudioAndMusic ffmpeg start...");
                    handler.sendEmptyMessage(0);
                }

                @Override
                public void onEnd(int result) {
                    Log.e(TAG,"composeAudioAndMusic ffmpeg end...");
                    composeMusicAndAudio(musicAudioPath);
                }
            });
        }
    }

    /**
     * 视频和背景音乐合成
     *
     * @param bgMusicAndAudio
     */
    private void composeMusicAndAudio(String bgMusicAndAudio) {
        final String videoAudioPath = mTargetPath + "/videoMusicAudio.mp4";
        final String videoUrl = mMediaPath.get(0);
        final int time = getIntent().getIntExtra("time",0) - 1;
        String[] common = FFmpegCommands.composeVideo(videoUrl, bgMusicAndAudio, videoAudioPath, time);
        FFmpegRun.execute(common, new FFmpegRun.FFmpegRunListener() {
            @Override
            public void onStart() {
                Log.e(TAG,"videoAndAudio ffmpeg start...");
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onEnd(int result) {
                Log.e(TAG,"videoAndAudio ffmpeg end...");
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
                    showProgressLoading();
                    break;
                case 1:
                    dismissProgress();
                    String videoPath = (String) msg.obj;
                    Intent intent = new Intent(MakeVideoActivity.this, MakeVideoActivity.class);
                    intent.putExtra("path",videoPath);
                    intent.putExtra("isPlayer",true);
                    startActivity(intent);
                    finish();
                    break;
                case 2:
                    dismissProgress();
                    break;
            }
        }
    };

    private void showProgressLoading(){

    }
    private void dismissProgress(){

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10000) {
            String music = data.getStringExtra("music");
            Log.e("---------music=",music);
            //   /storage/emulated/0/ringtones/杜歌、何鹏 - 兄弟难当 (DJ版).mp3
            //   http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/a37776dd8e508d1e5444d8b6bc992b6d.mp3
            final String url="http://aoyouhudongkeji-1259346675.cos.ap-guangzhou.myqcloud.com/a37776dd8e508d1e5444d8b6bc992b6d.mp3";

//            path=getCustomVideoOutputPath();
//            File file=new File(path);




//            try {
//                URI uri=new URI(url);
//                Uri uri=new Uri("http://www.csdn.net");
//                Uri uri=Uri.parse(url);
//                Log.e("---------uri=",uri.toString());
//                httpDown(url);
//                String filePathByUri = UriTofilePath.getFilePathByUri(MakeVideoActivity.this, uri);
//                Log.e("-----------pa-",filePathByUri);
//                cutSelectMusic(filePathByUri);









                final String path = Environment.getExternalStorageDirectory().getPath() + "/AAAAImg/";

                new Thread(new Runnable() {

                    @Override

                    public void run() {

                        try {

                            downLoadFromUrl(url, "123.mp3", path);

//                            cutSelectMusic(getCustomVideoOutputPath2());
                        } catch (Exception e) {

                            e.printStackTrace();

                        }

                    }

                }).start();










//            } catch (Exception e) {
//                e.printStackTrace();
//            }


        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        float volume = i / 100f;
        if (mAudioSeekBar == seekBar) {
            mAudioPlayer.setVolume(volume, volume);
        } else if(mMusicPlayer!=null){
            mMusicPlayer.setVolume(volume, volume);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    private void stopMediaPlayer(){
        try {
            if (mMusicPlayer != null) {
                mMusicPlayer.stop();
                mMusicPlayer.release();
                mMusicPlayer=null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
        if (mAudioPlayer != null) {
            mAudioPlayer.stop();
            mAudioPlayer.release();
        }
        stopMediaPlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        mVideoView.pause();
        if (mAudioPlayer != null) {
            mAudioPlayer.pause();
        }
        if (mMusicPlayer != null) {
            mMusicPlayer.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mVideoView.start();
        if (mAudioPlayer != null) {
            mAudioPlayer.start();
        }
        if (mMusicPlayer != null) {
            mMusicPlayer.start();
        }
    }


    private static final String OUTPUT_DIR_NAME = "TXUGC";
    String path;
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
    private String getCustomVideoOutputPath2() {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
        String time = sdf.format(new Date(currentTime));
        String outputDir = Environment.getExternalStorageDirectory() + File.separator + OUTPUT_DIR_NAME;
        File outputFolder = new File(outputDir);
        if (!outputFolder.exists()) {
            outputFolder.mkdir();
        }
        String tempOutputPath = outputDir + File.separator + "TXUGC_" + time + ".mp3";
        return tempOutputPath;
    }

    private void httpDown(final String path) {
        new Thread() {
            @Override
            public void run() {
                URL url;
                HttpURLConnection connection;
                try {
                    //统一资源
                    url = new URL(path);
                    //打开链接
                    connection = (HttpURLConnection) url.openConnection();
                    //设置链接超时
                    connection.setConnectTimeout(4000);
                    //设置允许得到服务器的输入流,默认为true可以不用设置
                    connection.setDoInput(true);
                    //设置允许向服务器写入数据，一般get方法不会设置，大多用在post方法，默认为false
                    connection.setDoOutput(true);//此处只是为了方法说明
                    //设置请求方法
                    connection.setRequestMethod("GET");
                    //设置请求的字符编码
                    connection.setRequestProperty("Charset", "utf-8");
                    //设置connection打开链接资源
                    connection.connect();
                    //得到链接地址中的file路径
                    String urlFilePath = connection.getURL().getFile();
                    //得到url地址总文件名 file的separatorChar参数表示文件分离符
                    String fileName = urlFilePath.substring(urlFilePath.lastIndexOf(File.separatorChar) + 1);
                    //创建一个文件对象用于存储下载的文件 此次的getFilesDir()方法只有在继承至Context类的类中
                    // 可以直接调用其他类中必须通过Context对象才能调用，得到的是内部存储中此应用包名下的文件路径
                    //如果使用外部存储的话需要添加文件读写权限，5.0以上的系统需要动态获取权限 此处不在不做过多说明。
                    File file = new File(getCustomVideoOutputPath2(), fileName);
                    //创建一个文件输出流
                    FileOutputStream outputStream = new FileOutputStream(file);

//                    cutSelectMusic(getCustomVideoOutputPath2());
                    Log.e("--------newfile=",file.getAbsolutePath());
                    //得到链接的响应码 200为成功
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        //得到服务器响应的输入流
                        InputStream inputStream = connection.getInputStream();
                        //获取请求的内容总长度
                        int contentLength = connection.getContentLength();
                        //设置progressBar的Max
//                        mPb.setMax(contentLength);
                        //创建缓冲输入流对象，相对于inputStream效率要高一些
                        BufferedInputStream bfi = new BufferedInputStream(inputStream);
                        //此处的len表示每次循环读取的内容长度
                        int len;
                        //已经读取的总长度
                        int totle = 0;
                        //bytes是用于存储每次读取出来的内容
                        byte[] bytes = new byte[1024];
                        while ((len = bfi.read(bytes)) != -1) {
                            //每次读取完了都将len累加在totle里
                            totle += len;
                            //每次读取的都更新一次progressBar
//                            mPb.setProgress(totle);
                            //通过文件输出流写入从服务器中读取的数据
                            outputStream.write(bytes, 0, len);
                        }
                        //关闭打开的流对象
                        outputStream.close();
                        inputStream.close();
                        bfi.close();
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(this, "下载完成!", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }













    /**

     * 从网络Url中下载文件

     *

     * @param urlStr

     * @param fileName

     * @param savePath

     * @throws IOException

     */

    public  void downLoadFromUrl(String urlStr, String fileName, String savePath) throws IOException {

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
        File file = new File(saveDir + fileName);

        cutSelectMusic(file.getAbsolutePath());
        Log.e("----------ffff",file.getAbsolutePath());

        FileOutputStream fos = new FileOutputStream(file);

        fos.write(getData);

        if (fos != null) {

            fos.close();

        }

        if (inputStream != null) {

            inputStream.close();

        }

        System.out.println("info:" + url + " download success");

    }



    /**

     * 从输入流中获取字节数组

     *

     * @param inputStream

     * @return

     * @throws IOException

     */

    public static byte[] readInputStream(InputStream inputStream) throws IOException {

        byte[] buffer = new byte[1024];

        int len = 0;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        while ((len = inputStream.read(buffer)) != -1) {

            bos.write(buffer, 0, len);

        }

        bos.close();

        return bos.toByteArray();

    }
}
