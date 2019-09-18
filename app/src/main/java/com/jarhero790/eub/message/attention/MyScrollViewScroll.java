package com.jarhero790.eub.message.attention;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 *  拦截滑动事件，不由recyclerview处理
 */
public class MyScrollViewScroll extends ScrollView {
    // private int downX;
    private int downY;
    private int touchSlop;

    public MyScrollViewScroll(Context context) {
        super(context);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyScrollViewScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyScrollViewScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

//    @Override
////    public boolean onInterceptTouchEvent(MotionEvent e) {
////        int action = e.getAction();
////        switch (action) {
////            case MotionEvent.ACTION_DOWN:
////                // downX = (int) e.getRawX();
////                downY = (int) e.getRawY();
////                break;
////            case MotionEvent.ACTION_MOVE:
////                int moveY = (int) e.getRawY();
////                if (Math.abs(moveY - downY) > touchSlop) {
////                    return true;
////                }
////        }
////        return super.onInterceptTouchEvent(e);
////    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE: {
                int moveY = (int) ev.getRawY();
//                if (Math.abs(moveY - downY) > touchSlop) {//父容器需要事件
//                    intercepted = true;
//                } else {
//                    intercepted = false;
//                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                intercepted = false;
                break;
            }
        }
        return intercepted;
    }
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }



}


