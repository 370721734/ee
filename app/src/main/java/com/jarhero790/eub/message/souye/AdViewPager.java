package com.jarhero790.eub.message.souye;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2018/4/16 0016.
 */

public class AdViewPager extends ViewPager {
    public AdViewPager(@NonNull Context context) {
        super(context);
    }

    public AdViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //是否分发点击事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action=ev.getAction();
        switch (action){
            //用户按下的时候
            case MotionEvent.ACTION_DOWN:
                //停止自动轮播的线程
                stopLoop();
                break;
            case MotionEvent.ACTION_UP:
                //开始自动轮播的线程
                startLoop();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private Thread mLooperThread;
    //开始轮播
    public void startLoop(){
        //线程不为空，并且在活动中
     if (mLooperThread!=null && mLooperThread.isAlive()){
         return;
     }
     mLooperThread=new Thread(){
         @Override
         public void run() {
             try {
                 while (true){
                     Thread.sleep(3000);
                     mHandler.sendEmptyMessage(1);
                 }
             }catch (Exception e){
                 e.printStackTrace();
             }
         }
     };
     mLooperThread.start();
    }

    //停止轮播
    public void stopLoop(){
        if (mLooperThread!=null){
            mHandler.removeMessages(1);
            mLooperThread.interrupt();
            mLooperThread=null;
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //收到消息，将当前页面的下一个页面当做显示的页面
            int index=getCurrentItem();
            //是最后一个
            if (index==getAdapter().getCount()-1){
                index=0;
            }else {
                index++;
            }
            setCurrentItem(index);
        }
    };
}
