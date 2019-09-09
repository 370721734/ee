package com.jarhero790.eub.message.souye;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jarhero790.eub.R;

import java.util.Random;

public class Love extends RelativeLayout {
    private Context mContext;
    float[] num = {-30, -20, 0, 20, 30};//随机心形图片角度

//    private long firstClick=0;
//    private  long lastClick=0;
//    private int midMillis=500;
    private final int MSG_ONE_CLICK=0x000012;

//    private int mClickcount;// 点击次数
//   private int mDownX;
//   private int mDownY;
//   private int mMoveX;
//   private int mMoveY;
//   private int mUpX;
//   private int mUpY;
//   private long mLastDownTime;
//   private long mLastUpTime;
//   private long mFirstClick;
//   private long mSecondClick;
//   private long mThreeClick;
//   private boolean isDoubleClick=false;
//   private boolean isThreeClic=false;
//   private int MAX_LONG_PRESS_TIME=350;//长按/双击最长等待时间
//    private int MAX_SINGLE_CLICK_TIME=50;//单击最长等待时间
//    private int MAX_MOVE_FOR_CLICK=50;//最长改变距离,超过则算移动

    public interface ClickCallBack{
        void onClick();
        void onDoubleClick();
    }
    private ClickCallBack callback;

    public void setCallback(ClickCallBack callback) {
        this.callback = callback;
    }

//    private MoveCallBack moveCallBack;
//    public interface MoveCallBack {
//        void onMove(float x,float y);
//    }
//
//    public void setMoveCallBack(MoveCallBack moveCallBack) {
//        this.moveCallBack = moveCallBack;
//    }

//    private float startX;
//    private float startY;
//    private float rawX;
//    private float rawY;
//    private boolean isMove;
//
//    private Runnable mLongPressTask=new Runnable() {
//    @Override
//    public void run() {
//            //处理长按
//            mClickcount=0;
//        }
//    };
//    private Runnable mSingleClickTask=new Runnable() {
//    @Override
//    public void run() {
//            // 处理单击
//            mClickcount=0;
//        }
//    };
//    @SuppressLint("HandlerLeak")
//    private Handler mHandler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//
//            switch (msg.what){
//                case MSG_ONE_CLICK:
//
//                    if (callback!=null){
//                         callback.onClick();
//                    }
//                    break;
//            }
//            super.handleMessage(msg);
//        }
//    };

    public Love(Context context) {
        super(context);
        initView(context);
    }

    private long[] mHits=new long[3];//初始全部为0



    public Love(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public Love(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将mHints数组内的所有元素左移一个位置
        System.arraycopy(mHits,1,mHits,0,mHits.length-1);
        //获得当前系统已经启动的时间
        mHits[2]= SystemClock.uptimeMillis();

//        Log.e("----------1","="+mHits[mHits.length-1]);


        if (mHits[0]>0) {
            if ((mHits[2] - mHits[0]) <= 500) {

//                Log.e("----------2", "=" + mHits[0]);
//                Log.e("----------3", "=" + mHits[1]);
//                Log.e("----------4", "=" + mHits[2]);
//                mHits[0] = SystemClock.uptimeMillis();


//            if (event.getAction()==MotionEvent.ACTION_DOWN){
//                startX=event.getX();
//                startY=event.getY();
//                mLastDownTime=System.currentTimeMillis();
//                mDownX= (int) event.getX();
//                mDownY= (int) event.getY();
//                mClickcount++;
//                if (1==mClickcount){
//                    mFirstClick=System.currentTimeMillis();
//                }else if (mClickcount==2){
//                    mSecondClick=System.currentTimeMillis();
//                    if (mSecondClick-mFirstClick<=MAX_LONG_PRESS_TIME){
//                        Log.e("--------","处理双击");
//                        isDoubleClick=true;
//                        mClickcount=0;
//                        mFirstClick=0;
//                        mSecondClick=0;
//                        mHandler.removeCallbacks(mSingleClickTask);
//                        mHandler.removeCallbacks(mLongPressTask);
//                    }
//                }else if (mClickcount>=3){
//                    mThreeClick=System.currentTimeMillis();
//                    if (mThreeClick-mFirstClick<=MAX_LONG_PRESS_TIME){
//                        Log.e("--------","处理3击");
//                        isThreeClic=true;
//                        mClickcount=0;
//                        mSecondClick=0;
//                        mThreeClick=0;
//                    }
//                }
//                Log.e("-----","down");
//                isMove=false;
//            }else if (event.getAction()==MotionEvent.ACTION_MOVE){
//                if (event.getX()-startX>5 || event.getX()-startX<-5 || event.getY()-startY>5 || event.getY()-startY<-5){
//                    rawX=event.getRawX();
//                    rawY=event.getRawY();
//                    if (moveCallBack!=null){
//                        moveCallBack.onMove(rawX-startX,rawY-startY);
//                        isMove=true;
//                    }
//                    Log.e("-----","move");
//                }else if(event.getAction() == MotionEvent.ACTION_UP){
//                    if(!isMove){
//                        mHandler.sendEmptyMessageDelayed(MSG_ONE_CLICK , midMillis);
//                        firstClick = lastClick;
//                        lastClick = System.currentTimeMillis();
//                        if (lastClick - firstClick < midMillis)
//                        {
//                            //如果是双击，将之前发送的单击消息取消掉
//                            mHandler.removeMessages(MSG_ONE_CLICK);
//                            if(callback != null){
//                                callback.onDoubleClick();
//                            }
//                            Log.d("K_K", "double click");
//                        }
//                    }
//                    Log.e("-----","up");
//                }
//            }


                final ImageView imageView = new ImageView(mContext);
                LayoutParams params = new LayoutParams(300, 300);
                params.leftMargin = (int) event.getX() - 150;
                params.topMargin = (int) event.getY() - 300;
                imageView.setImageDrawable(getResources().getDrawable(R.mipmap.icon_home_like_after));
                imageView.setLayoutParams(params);
                addView(imageView);
                if (loveTrue!=null){
                    loveTrue.Onclick(true);
                }

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(scale(imageView, "scaleX", 2f, 0.9f, 100, 0))
                        .with(scale(imageView, "scaleY", 2f, 0.9f, 100, 0))
                        .with(rotation(imageView, 0, 0, num[new Random().nextInt(4)]))
                        .with(alpha(imageView, 0, 1, 100, 0))
                        .with(scale(imageView, "scaleX", 0.9f, 1, 50, 150))
                        .with(scale(imageView, "scaleY", 0.9f, 1, 50, 150))
                        .with(translationY(imageView, 0, -600, 800, 400))
                        .with(alpha(imageView, 1, 0, 300, 400))
                        .with(scale(imageView, "scaleX", 1, 3f, 700, 400))
                        .with(scale(imageView, "scaleY", 1, 3f, 700, 400));

                animatorSet.start();
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        removeViewInLayout(imageView);
                    }
                });
            }
        }

        return super.onTouchEvent(event);
    }

    public static ObjectAnimator scale(View view, String propertyName, float from, float to, long time, long delayTime) {
        ObjectAnimator translation = ObjectAnimator.ofFloat(view
                , propertyName
                , from, to);
        translation.setInterpolator(new LinearInterpolator());
        translation.setStartDelay(delayTime);
        translation.setDuration(time);
        return translation;
    }

    public static ObjectAnimator translationX(View view, float from, float to, long time, long delayTime) {
        ObjectAnimator translation = ObjectAnimator.ofFloat(view
                , "translationX"
                , from, to);
        translation.setInterpolator(new LinearInterpolator());
        translation.setStartDelay(delayTime);
        translation.setDuration(time);
        return translation;
    }

    public static ObjectAnimator translationY(View view, float from, float to, long time, long delayTime) {
        ObjectAnimator translation = ObjectAnimator.ofFloat(view
                , "translationY"
                , from, to);
        translation.setInterpolator(new LinearInterpolator());
        translation.setStartDelay(delayTime);
        translation.setDuration(time);
        return translation;
    }

    public static ObjectAnimator alpha(View view, float from, float to, long time, long delayTime) {
        ObjectAnimator translation = ObjectAnimator.ofFloat(view
                , "alpha"
                , from, to);
        translation.setInterpolator(new LinearInterpolator());
        translation.setStartDelay(delayTime);
        translation.setDuration(time);
        return translation;
    }

    public static ObjectAnimator rotation(View view, long time, long delayTime, float... values) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", values);
        rotation.setDuration(time);
        rotation.setStartDelay(delayTime);
        rotation.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                return input;
            }
        });
        return rotation;
    }


    public interface LoveTrue{
        void Onclick(boolean love);
    }
    private LoveTrue loveTrue;

    public void setLoveTrue(LoveTrue loveTrue) {
        this.loveTrue = loveTrue;
    }
}

