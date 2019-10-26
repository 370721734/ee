package com.mabeijianxi.jianxiffmpegcmd;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jarhero790.eub.R;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("avutil");
        System.loadLibrary("fdk-aac");
        System.loadLibrary("avcodec");
        System.loadLibrary("avformat");
        System.loadLibrary("swscale");
        System.loadLibrary("swresample");
        System.loadLibrary("avfilter");
        System.loadLibrary("jxffmpegrun");
    }


    private static MainActivity instance;
    private MainActivity(){}
    public static MainActivity getInstance(){
        if (instance==null){
            instance=new MainActivity();
        }
        return instance;
    }

    private final static String[] mPermissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private final static int CODE_STORAGE = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance=this;
        checkPermission();
    }

    public void onClick(View v){

    }

    /**
     * 命令运行
     * @param cmd
     * @return
     */
    public  int jxFFmpegCMDRun(String cmd){
        String regulation="[ \\t]+";
        final String[] split = cmd.split(regulation);

        return ffmpegRun(split);
    }
    public native int ffmpegRun(String[] cmd);

    /**
     * 获取ffmpeg编译信息
     * @return
     */
    public native String getFFmpegConfig();




    //动态申请权限
    private void checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(mPermissions[0]) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(mPermissions, CODE_STORAGE);
            }
        }
    }
}



