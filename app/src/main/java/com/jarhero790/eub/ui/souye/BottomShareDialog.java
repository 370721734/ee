package com.jarhero790.eub.ui.souye;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jarhero790.eub.R;
import com.jarhero790.eub.record.DialogUtil;
import com.jarhero790.eub.record.FileUtils;
import com.jarhero790.eub.record.PlayState;
import com.jarhero790.eub.record.TCConstants;
import com.jarhero790.eub.record.TCEditerUtil;
import com.jarhero790.eub.record.TCVideoEditerActivity;
import com.jarhero790.eub.record.TCVideoEditerWrapper;
import com.jarhero790.eub.record.TCVideoRecordActivity;
import com.jarhero790.eub.utils.AppUtils;
import com.jarhero790.eub.utils.CommonUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.ugc.TXRecordCommon;
import com.tencent.ugc.TXVideoEditConstants;
import com.tencent.ugc.TXVideoEditer;
import com.tencent.ugc.TXVideoInfoReader;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.functions.Consumer;


public class BottomShareDialog extends DialogFragment implements AdapterView.OnItemClickListener, TCVideoEditerWrapper.TXVideoPreviewListenerWrapper, TXVideoEditer.TXVideoGenerateListener {
    private View view;
    private Window window;

    private static BottomShareDialog instance = null;

    private DialogInterface.OnDismissListener mOnClickListener;

    private LinearLayout ll_down, ll_weixin;

    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        this.mOnClickListener = listener;
    }

    private String mVideoOutputPath;                        // 视频输出路径

    public static BottomShareDialog newInstance() {
        if (instance == null) {
            instance = new BottomShareDialog();
        }
        return instance;
    }

    private String url;
    private static String filePath = "/download/";


    private TCVideoEditerWrapper mEditerWrapper;
    // 短视频SDK获取到的视频信息
    private TXVideoEditer mTXVideoEditer;                   // SDK接口类


    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(getContext(),"BottomPingLunDialog onPause",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getContext(),"BottomPingLunDialog onResume",Toast.LENGTH_LONG).show();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEditerWrapper = TCVideoEditerWrapper.getInstance();
        mEditerWrapper.addTXVideoPreviewListenerWrapper(this);
//        mVideoOutputPath = getCustomVideoOutputPath();

//        mTXVideoEditer = new TXVideoEditer(getActivity());
//        mEditerWrapper.setEditer(mTXVideoEditer);


    }

//    private static final String OUTPUT_DIR_NAME = "TXUGC";
//    private String getCustomVideoOutputPath() {
//        long currentTime = System.currentTimeMillis();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
//        String time = sdf.format(new Date(currentTime));
//        String outputDir = Environment.getExternalStorageDirectory() + File.separator + OUTPUT_DIR_NAME;
//        File outputFolder = new File(outputDir);
//        if (!outputFolder.exists()) {
//            outputFolder.mkdir();
//        }
//        String tempOutputPath = outputDir + File.separator + "TXUGC_" + time + ".mp4";
//        return tempOutputPath;
//    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 去掉默认的标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.souye_share, null);
        ll_down = view.findViewById(R.id.ll_down);
        ll_weixin = view.findViewById(R.id.ll_weixin);

        ll_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog = new Dialog(getActivity(), R.style.progress_dialog);
                dialog.setContentView(R.layout.dialog);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();

                Toast.makeText(getActivity(), "开始下载", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (url != null)
                            downvideo(url);
                    }
                }).start();
                if (shareDialog != null) {
                    shareDialog.Clicklinear(v, "下载");
                }
                dismiss();
            }
        });
        ll_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shareDialog != null) {
                    shareDialog.Clicklinear(v, "分享");
                }
                dismiss();
            }
        });

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private Dialog dialog;
    private String videoid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
        requestPermissions(getActivity());
        Bundle bundle = getArguments();
        if (bundle != null) {
            url = bundle.getString("url");
            videoid = bundle.getString("videoid");
//            Log.e("------------url=>", url+"  "+videoid);
//            page = 1;
//            requestdate(vid);


        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onStart() {
        super.onStart();
        // 下面这些设置必须在此方法(onStart())中才有效
        window = getDialog().getWindow();
        // 如果不设置这句代码, 那么弹框就会与四边都有一定的距离
        window.setBackgroundDrawableResource(android.R.color.transparent);
        // 设置动画
        window.setWindowAnimations(R.style.bottomDialog);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        // 如果不设置宽度,那么即使你在布局中设置宽度为 match_parent 也不会起作用
        params.width = getResources().getDisplayMetrics().widthPixels;
        window.setAttributes(params);
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnClickListener != null) {
            mOnClickListener.onDismiss(dialog);
        }
    }

    @Override
    public void onPreviewProgressWrapper(int time) {
//        Log.e("------------","rrrrrrrrrrrrrrrrrrrrrrrrrrr1");
    }

    @Override
    public void onPreviewFinishedWrapper() {
//        Log.e("------------","rrrrrrrrrrrrrrrrrrrrrrrrrrr2");
    }

    @Override
    public void onGenerateProgress(float v) {
//        Log.e("------------","rrrrrrrrrrrrrrrrrrrrrrrrrrr3"+v);

//        Toast.makeText(AppUtils.getContext(),"下载中...",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGenerateComplete(TXVideoEditConstants.TXGenerateResult txGenerateResult) {
//          Log.e("------------","rrrrrrrrrrrrrrrrrrrrrrrrrrr4");
        if (dialog != null)
            dialog.dismiss();

        if (fileString != null)
            FileUtils.deleteFile(fileString);
        if (tufileString!=null)
            FileUtils.deleteFile(tufileString);
        Toast.makeText(AppUtils.getContext(), "下载完成", Toast.LENGTH_LONG).show();
    }


    public interface ShareDialog {
        void Clicklinear(View view, String type);
    }

    private ShareDialog shareDialog;

    public void setShareDialog(ShareDialog shareDialog) {
        this.shareDialog = shareDialog;
    }


    private int FileLength;

    private String fileString;

    private void downvideo(String url) {
        String savePAth = Environment.getExternalStorageDirectory() + filePath;
        File file1 = new File(savePAth);
        if (!file1.exists()) {
            file1.mkdir();
        }
        HttpURLConnection con;
        FileOutputStream fs = null;
        InputStream is;
        BufferedInputStream bs = null;
        File file = new File(savePAth + new Date().getTime() + ".mp4");

        Message message = new Message();
        try {
//            if (!file.exists())
//            file.createNewFile();
            fileString = file.getAbsolutePath();
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
            is = con.getInputStream();
            bs = new BufferedInputStream(is);
            fs = new FileOutputStream(file);

            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
            randomAccessFile.setLength(FileLength);
            byte[] bytes = new byte[1024 * 4];
            FileLength = con.getContentLength();
            message.what = 0;
            handler.sendMessage(message);
            int line = 0;
            while ((line = bs.read(bytes)) != -1) {
                fs.write(bytes, 0, line);
                fs.flush();
                Message message1 = new Message();
                message1.what = 1;
                handler.sendMessage(message1);
            }
            is.close();
            randomAccessFile.close();
            Message message2 = new Message();
            message2.what = 2;
            handler.sendMessage(message2);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fs != null) {
                try {
                    fs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (bs != null) {
                    try {
                        bs.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    case 0:
//                        progressBar.setMax(FileLength);
//                        Log.i("文件长度----------->", progressBar.getMax()+"");
                        break;
                    case 1:
//                            progressBar.setProgress(DownedFileLength);
//                            int x=DownedFileLength*100/FileLength;
//                            textView.setText(x+"%");
                        break;
                    case 2:
//                        if (dialog!=null)
//                            dialog.dismiss();

                        Toast.makeText(AppUtils.getContext(), "正在下载", Toast.LENGTH_SHORT).show();
                        startEditVideo();


                        break;
                    default:
                        break;
                }
            }
        }
    };


    public void requestPermissions(Activity activity) {
        RxPermissions rxPermission = new RxPermissions(activity);
        //请求权限全部结果
        rxPermission.request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        )
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (!granted) {
                            //ToastUtils.showToast("App未能获取全部需要的相关权限，部分功能可能不能正常使用.");
                        }
                        //不管是否获取全部权限，进入主页面

                    }
                });
        //分别请求权限
        //        rxPermission.requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
        //                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        //                Manifest.permission.READ_CALENDAR,
        //                Manifest.permission.READ_CALL_LOG,
        //                Manifest.permission.READ_CONTACTS,
        //                Manifest.permission.READ_PHONE_STATE,
        //                Manifest.permission.READ_SMS,
        //                Manifest.permission.RECORD_AUDIO,
        //                Manifest.permission.CAMERA,
        //                Manifest.permission.CALL_PHONE,
        //                Manifest.permission.SEND_SMS)
        //注：魅族pro6s-7.0-flyme6权限没有像类似6.0以上手机一样正常的提示dialog获取运行时权限，而是直接默认给了权限。魅族pro6s动态获取权限不会回调下面的方法
        //        rxPermission.requestEach(
        //                Manifest.permission.CAMERA,
        //                Manifest.permission.READ_PHONE_STATE,
        //                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        //                Manifest.permission.READ_EXTERNAL_STORAGE,
        //                Manifest.permission.ACCESS_COARSE_LOCATION)
        //                .subscribe(new Consumer<Permission>() {
        //                    @Override
        //                    public void accept(Permission permission) throws Exception {
        //                        if (permission.granted) {
        //                            // 用户已经同意该权限
        //                            Log.d(TAG, permission.name + " is granted.");
        //                        } else if (permission.shouldShowRequestPermissionRationale) {
        //                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
        //                            Log.d(TAG, permission.name + " is denied. More info should
        // be provided.");
        //                        } else {
        //                            // 用户拒绝了该权限，并且选中『不再询问』
        //                            Log.d(TAG, permission.name + " is denied.");
        //                        }
        //                    }
        //                });
    }


    private VideoMainHandler mVideoMainHandler;                 // 加载完信息后的回调主线程Hanlder
    private Thread mLoadBackgroundThread;                       // 后台加载视频信息的线程


    //下一个
    private void startEditVideo() {
        mTXVideoEditer = new TXVideoEditer(AppUtils.getContext());
        int ret = mTXVideoEditer.setVideoPath(fileString);//
        if (ret != 0) {
            if (ret == TXVideoEditConstants.ERR_SOURCE_NO_TRACK) {
//                DialogUtil.showDialog(this, "视频预处理失败", "不支持的视频格式", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        finish();
//                    }
//                });
//            } else if (ret == TXVideoEditConstants.ERR_UNSUPPORT_AUDIO_FORMAT) {
//                DialogUtil.showDialog(this, "视频预处理失败", "暂不支持非单双声道的视频格式", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        finish();
//                    }
//                });
            }
            return;
        }

        TCVideoEditerWrapper wrapper = TCVideoEditerWrapper.getInstance();
        wrapper.setEditer(mTXVideoEditer);

//        initPhoneListener();

        // 开始加载视频信息
        mVideoMainHandler = new VideoMainHandler();
        mLoadBackgroundThread = new Thread(new LoadVideoRunnable(this));
        mLoadBackgroundThread.start();


        /////
        startGenerate();

    }


    /**
     * 加在视频信息的runnable
     */
    private class LoadVideoRunnable implements Runnable {
        private WeakReference<BottomShareDialog> mWekActivity;

        LoadVideoRunnable(BottomShareDialog activity) {
            mWekActivity = new WeakReference<BottomShareDialog>(activity);
        }

        @Override
        public void run() {
            if (mWekActivity == null || mWekActivity.get() == null) {
                return;
            }
            BottomShareDialog activity = mWekActivity.get();
            if (activity == null) return;
            TXVideoEditConstants.TXVideoInfo info = TXVideoInfoReader.getInstance().getVideoFileInfo(fileString);

//            if (isCancel.get()) {
//                return;
//            }
            if (info == null) {// error 发生错误
                mVideoMainHandler.sendEmptyMessage(BottomShareDialog.VideoMainHandler.LOAD_VIDEO_ERROR);
            } else {
                TCVideoEditerWrapper.getInstance().setTXVideoInfo(info);
                mVideoMainHandler.sendEmptyMessage(BottomShareDialog.VideoMainHandler.LOAD_VIDEO_SUCCESS);
            }
        }
    }


    private void startGenerate() {
        mTXVideoEditer.cancel(); // 注意：生成时，停止输出缩略图

        startGenerateVideo();
    }


    private void startGenerateVideo() {
        // 处于生成状态
//        mCurrentState = PlayState.STATE_GENERATE;
//        // 防止
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
        addTailWaterMark();  //腾讯云水印

        mTXVideoEditer.setCutFromTime(getCutterStartTime(), getCutterEndTime());
        mTXVideoEditer.setVideoGenerateListener(this);
        mTXVideoEditer.setVideoBitrate(9600);
        mTXVideoEditer.generateVideo(TXVideoEditConstants.VIDEO_COMPRESSED_720P, mVideoOutputPath);

    }


    private long getCutterStartTime() {
        return mEditerWrapper.getCutterStartTime();
    }

    private long getCutterEndTime() {
        return mEditerWrapper.getCutterEndTime();
    }


    private String tufileString;
    /**
     * 添加水印
     */
    private void addTailWaterMark() {

//        TXVideoEditConstants.TXVideoInfo info = TCVideoEditerWrapper.getInstance().getTXVideoInfo();

//        Bitmap tailWaterMarkBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.tcloud_logo);
//        float widthHeightRatio = tailWaterMarkBitmap.getWidth() / (float) tailWaterMarkBitmap.getHeight();

        TXVideoEditConstants.TXRect txRect = new TXVideoEditConstants.TXRect();
//        txRect.width = 0.25f; // 归一化的片尾水印，这里设置了一个固定值，水印占屏幕宽度的0.25。
        // 后面根据实际图片的宽高比，计算出对应缩放后的图片的宽度：txRect.width * videoInfo.width 和高度：txRect.width * videoInfo.width / widthHeightRatio，然后计算出水印放中间时的左上角位置
//        txRect.x = (info.width - txRect.width * info.width) / (2f * info.width);
//        txRect.y = (info.height - txRect.width * info.width / widthHeightRatio) / (2f * info.height);


        txRect.x = 0f;
        txRect.y = 0f;
        txRect.width = 0.5f;
        String tustring = Environment.getExternalStorageDirectory() + "/images/";
        File filer = new File(tustring);
        if (!filer.exists()) {
            filer.mkdirs();
        }

        File bb = new File(tustring, "image1.jpg");
        try {
            bb.createNewFile();
            tufileString=bb.getAbsolutePath();
//            Bitmap bitmap = CommonUtil.textToPicture(bb.getAbsolutePath(),"钻视TV \r\n 112233",TCVideoEditerActivity.this);

//            Bitmap bitmap=CommonUtil.textToBitmap("钻视TV \r\n 112233",TCVideoEditerActivity.this);


            Bitmap bitmap = CommonUtil.textAsBitmap(" 钻视TV \r\n ID:" + videoid, 48);


            mTXVideoEditer.setWaterMark(bitmap, txRect);//全局
        } catch (IOException e) {
            e.printStackTrace();
        }


//        mTXVideoEditer.setTailWaterMark(tailWaterMarkBitmap, txRect, 3);//片尾

    }


    private class VideoMainHandler extends Handler {
        static final int LOAD_VIDEO_SUCCESS = 0;
        static final int LOAD_VIDEO_ERROR = -1;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOAD_VIDEO_ERROR:
                    if (dialog != null)
                        dialog.dismiss();

                    break;
                case LOAD_VIDEO_SUCCESS:
                    if (dialog != null)
                        dialog.dismiss();

                    break;
            }
        }

    }
}
