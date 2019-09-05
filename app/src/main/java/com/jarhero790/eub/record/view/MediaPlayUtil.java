package com.jarhero790.eub.record.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import android.webkit.URLUtil;
import android.widget.SeekBar;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class MediaPlayUtil {
    //一个约定
    private static final int MSG_POS=1;
    //记录当前正在下载的任务
    private Map<String,DownloadTask> currentTask;

    // 缓存路径
    private String cachePath="/mnt/sdcard";
    //进度条
    private SeekBar mSeek;
    private Surface surface;

    public void initCachePath(Context context){
        cachePath=context.getExternalCacheDir().getAbsolutePath();
        // /mnt/sdcard/Android/data/应用包名/cache
        //是保存在这个目录 之下
    }


    //私有静态本类变量
    private static MediaPlayUtil instance;
    //私有构造方法
    private MediaPlayUtil() {
        currentTask=new HashMap<String,DownloadTask>();
    }
    //返回公开静态本类方法
    public static MediaPlayUtil getInstance(){
        if (instance==null){
            instance=new MediaPlayUtil();
        }
        return instance;
    }

   //记录当前是否是停止状态
    private boolean isStop;

    /**
     * 使用地址来播放
     *
     * @param url
     */
    public void start(String url){// file:/// /mnt/sdcard http:// https://
        isStop=true;  //开始播放为true
        //初始化播放器
        initMediaPlayer();
        // 如果源是在网络上则下载，
        if (URLUtil.isNetworkUrl(url)){
            // http://ip:8080/WeiboServer/music/download?filename=aaa.mp3
            // 判断当前任务是否有在下载
            if (!currentTask.containsKey(url)){
                // 检测本地是否已经有该文件
                String localPath=getLocalPathFromUrl(url);
                //如果本地已经有该文件，则直接本地播放
                if (new File(localPath).exists()){
                    // 如果是不完成的记录最好用数据库记录当前大小 总大小
                    play(localPath);
                }else {

                    // 处理下载并播放
                    //"/mnt/sdcard/"+System.currentTimeMillis()+".mp3"
                    new DownloadTask(localPath).execute(url);
                }
            }
        }else {//是本地则直接从本地加载
            // 本地播放
            play(url);
        }
    }


    /**
     * 从网址中获取对应的本地缓存文件路径
     *
     * @param url
     * @return
     */
    private String getLocalPathFromUrl(String url){
        //获取最后一个/的位置
        int lastIndex=url.lastIndexOf("/");
        // 从网址中最后一个/开始截取到末尾
        String name=url.substring(lastIndex);
        // 替换所有的特殊字符
        //// 替换所有的特殊字符 如:download?filename=aaa.mp3转为downloadfilenameaaamp3
        name=name.replaceAll("[^\\w]","");
        // download?filename=aaa.mp3
        // -->downloadfilenameaaamp3
        return cachePath+"/"+name;
    }





    /**
     * 恢复播放状态
     */
    public void start(){
        if (mPlayer!=null && !mPlayer.isPlaying()){
            mPlayer.start();
            if (mSeek!=null){
                mHandler.sendEmptyMessageDelayed(MSG_POS,200);
            }
        }
    }


    /**
     * 暂停
     */
    public void pause(){
        if (mPlayer!=null && mPlayer.isPlaying()){
            mHandler.removeMessages(MSG_POS);
            mPlayer.pause();
        }
    }



    /**
     * 停止方法
     */
    public void stop(){
        if (mPlayer!=null){
            mHandler.removeMessages(MSG_POS);
            mPlayer.stop();
            mPlayer.release();
            // 如果有正在下载的（未完整的文件），删除缓存
            if (!currentTask.isEmpty()){
                //使用迭代器遍历当前正在下载的任务
                Iterator<Map.Entry<String,DownloadTask>> it=currentTask.entrySet().iterator();
                while (it.hasNext()){
                    Map.Entry<String,DownloadTask> entry=it.next();
                    //获取当前下载的任务对象
                    DownloadTask t=entry.getValue();
                    // 取消任务
                    t.cancel(true);
                    // 删除该任务的缓存
                    new File(t.getSavePath()).delete();
                }
            }
            mPlayer=null;
            if (mSeek!=null){
                // 恢复进度值到0
                mSeek.setProgress(0);
                mSeek.setSecondaryProgress(0);
            }
        }
    }

    //如果没有初始化
    public boolean isMediaPlayerisNull(){
        return mPlayer==null;
    }




    //异步任务，从网络下载
    class DownloadTask extends AsyncTask<String,Integer,Boolean>{
        //保存路径
        private String savePath;

        DownloadTask(String savePath) {
            this.savePath = savePath;
        }


        /**
         * 子线程处理网络下载
         * @param strings
         * @return
         */
        @Override
        protected Boolean doInBackground(String... strings) {
            boolean result=true;
            // 约定第一个参数是网址
            String url=strings[0];
            // 记录当前任务为正在下载中的任务
            currentTask.put(url,this);
            HttpURLConnection conn=null;
            try {
                URL link=new URL(url);
                 conn= (HttpURLConnection) link.openConnection();
                //// 如果需要断点续传的话，可以配置断点的信息

                // 获取返回码
                int code=conn.getResponseCode();
                if (code==200){
                    // 获取文件大小
                    int total=conn.getContentLength();
                    //
                    RandomAccessFile raf=new RandomAccessFile(savePath,"rw");
                    raf.setLength(total);
                    //保存文件
                    InputStream is=conn.getInputStream();
                    int len=0;// 每次读到的量
                    int currentSize=0;// 记录当前下载的总大小
                    byte[] buf=new byte[1024];
                    while ((len=is.read(buf))!=-1){
                        raf.write(buf,0,len);
                        // 统计当前下载的总大小(计算进度)
                        currentSize+=len;
                        // 发布更新进度到主线程（百分比，当前下载的总大小）
                        publishProgress(currentSize*100/total,currentSize);
                    }
                    raf.close();
                    is.close();
                }
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                //有异常
               if (conn!=null){
                   conn.disconnect();
               }
                // 移除不完整的本地缓存文件
                new File(savePath).delete();
               result=false;
            }
            currentTask.remove(url);
            return result; //顺利进行
        }

        /**
         * 主线程中接收到进度的方法
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            //文件进度
            int fileProgress=values[0];
            //当前下载的大小
            int currentSize=values[1];
            // 判断当前下载是否超过1k，如果超过1k并且还没播放则开始播放
            if (currentSize>=1024 && isStop){
                //播放保存路径的音频
                play(savePath);
            }

            Log.e("m_tag", fileProgress + "%");
            //处理文件的缓冲进度
            if (mSeek!=null){
                mSeek.setSecondaryProgress(fileProgress);
            }
        }
        public String getSavePath(){
            return savePath;
        }
    }


    private MediaPlayer mPlayer;

    /**
     * 初始化MediaPlayer对象
     */
    private void initMediaPlayer(){
        // 检测是否有MediaPlayer对象，没有则创建对象
        if (mPlayer==null){
            mPlayer=new MediaPlayer();
            // 基本配置
            // 准备监听
         mPlayer.setOnPreparedListener(onPreparedListener);
            // 错误监听
         mPlayer.setOnErrorListener(onErrorListener);
            // 播放结束监听
        mPlayer.setOnCompletionListener(onCompletionListener);
            //如果是视频播放(有显示的Surface)，则设置Surface以及视频相关的大小监听
            if (surface!=null){
                mPlayer.setSurface(surface);
                //监听视频大小变化
                mPlayer.setOnVideoSizeChangedListener(onVideoSizeChangedListener);
                ////播放时不休眠（防止关屏）
                mPlayer.setScreenOnWhilePlaying(true);
            }

        }else {
            // 有则重置状态
          mPlayer.reset();
        }
        // 处于idle状态
    }



    /**
     * 设置播放路径，开始解码
     *
     * @param path
     */
    private void play(String path){
        //
        isStop=false;
        // 设置播放的文件资源
        try {
            mPlayer.setDataSource(path);
            // 进入initialized状态
            // 准备
            mPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
            //
            isStop=true;
        }
    }

    // 准备结果监听
    private MediaPlayer.OnPreparedListener onPreparedListener=new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            // 进入prepared状态
            // 进入该状态之后可以start pause seekTo stop getDuration getCurrentPosition
            // 初始化播放进度的总进度
            // int total = mp.getDuration();
            // mSeek.setMax(total);
            // 开始播放
            mp.start();
            if (mSeek!=null){
            // 开始循环获取当前进度
             mHandler.sendEmptyMessageDelayed(MSG_POS, 200);
        }
            Log.e("m_tag", "开始播放");
        }
    };


    // 错误监听
    private MediaPlayer.OnErrorListener onErrorListener=new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            mp.stop();
            mp.reset();
            isStop=true;
            return true;
        }
    };

    // 播放结束
    private MediaPlayer.OnCompletionListener onCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {

        }
    };



    @SuppressLint("HandlerLeak")
    private Handler mHandler=new Handler(){
        public void handleMessage(android.os.Message msg){
            // 处理进度更新
            if (msg.what==MSG_POS){
                // 获取当前的播放位置
                int current=mPlayer.getCurrentPosition();
                // 设置到进度的当前进度上
                mSeek.setProgress(100*current/mPlayer.getDuration());
                // 延迟200ms之后发送MSG_POS
                mHandler.sendEmptyMessageDelayed(MSG_POS,200);
            }
        }
    };

    public SeekBar getSeekBar() {
        return mSeek;
    }

    public void setSeekBar(SeekBar mSeek) {
        this.mSeek = mSeek;
        if (mSeek!=null){
            this.mSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // 如果有程序在更新进度，应该停止程序的更新
                    if (mPlayer!=null && mPlayer.isPlaying()){
                        // 停止更新
                        mHandler.removeMessages(MSG_POS);
                    }

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                         // 确定播放位置
                    if (mPlayer!=null){
                        int pos=seekBar.getProgress();
                        // 30% 3000 -->
                        // 0.3*3000
                        // = 900
                        int current=pos*mPlayer.getDuration()/100;
                        // 设置播放位置
                        mPlayer.seekTo(current);
                        // 如果是播放状态则恢复程序对进度条的更新
                        if (mPlayer.isPlaying()){
                            mHandler.sendEmptyMessageDelayed(MSG_POS,200);
                        }
                    }
                }
            });
        }else {
            this.mSeek.setOnSeekBarChangeListener(null);
        }
    }

    //监听视频大小变化
    private MediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener;

    public Surface getSurface() {
        return surface;
    }

    public void setSurface(Surface surface) {
        this.surface = surface;
    }

    public MediaPlayer.OnVideoSizeChangedListener getOnVideoSizeChangedListener() {
        return onVideoSizeChangedListener;
    }

    public void setOnVideoSizeChangedListener(MediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener) {
        this.onVideoSizeChangedListener = onVideoSizeChangedListener;
    }


    //释放资源(退出应用时使用)
    public void release(){
        stop();
        currentTask=null;
        instance=null;
    }
}
