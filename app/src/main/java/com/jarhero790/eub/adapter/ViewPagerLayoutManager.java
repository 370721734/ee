package com.jarhero790.eub.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.jarhero790.eub.bean.Video;
import com.jarhero790.eub.message.bean.MyFaBuBean;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerLayoutManager extends LinearLayoutManager {
    private static final String TAG = "ViewPagerLayoutManager";
    private PagerSnapHelper mPagerSnapHelper;
    private OnViewPagerListener mOnViewPagerListener;
    private RecyclerView mRecyclerView;
    private int mDrift;//位移，用来判断移动方向
    private List<Video> lists;
    private ArrayList<MyFaBuBean.DataBean> liststwo;
    private Context context;
    private OnNextPageImageListener onNextPageImageListener;

    public void setOnNextPageImageListener(OnNextPageImageListener onNextPageImageListener) {
        this.onNextPageImageListener = onNextPageImageListener;
    }

    public interface OnNextPageImageListener {
        void showNextImage(String video_img_url);
    }


    public ViewPagerLayoutManager(Context context, int orientation) {
        super(context, orientation, false);
        this.context=context;
        init();
    }

    public ViewPagerLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        init();
    }

    private void init() {
        mPagerSnapHelper = new PagerSnapHelper();
    }


    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        mPagerSnapHelper.attachToRecyclerView(view);
        this.mRecyclerView = view;
        mRecyclerView.addOnChildAttachStateChangeListener(mChildAttachStateChangeListener);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
    }

    /**
     * 滑动状态的改变
     * 缓慢拖拽-> SCROLL_STATE_DRAGGING
     * 快速滚动-> SCROLL_STATE_SETTLING
     * 空闲状态-> SCROLL_STATE_IDLE
     */
    @Override
    public void onScrollStateChanged(int state) {
        switch (state) {
            case RecyclerView.SCROLL_STATE_IDLE://RecyclerView当前没有滚动
                View viewIdle = mPagerSnapHelper.findSnapView(this);
                if (viewIdle==null)
                    return;
                int positionIdle = getPosition(viewIdle);
                if (mOnViewPagerListener != null && getChildCount() == 1) {
                    mOnViewPagerListener.onPageSelected(positionIdle,positionIdle == getItemCount() - 1);
                }
                //onNextPageImageListener.showNextImage(positionIdle+"--");
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING://RecyclerView当前正被外部输入（如用户触摸输入）拖动
//                View viewDrag = mPagerSnapHelper.findSnapView(this);
//                int positionDrag = getPosition(viewDrag);

               // Toast.makeText(AppUtils.getContext(),positionDrag,Toast.LENGTH_LONG).show();
                //String url=lists.get(positionDrag+1).getVideo_img();

                break;
            case RecyclerView.SCROLL_STATE_SETTLING://RecyclerView当前正在将动画设置到最终位置，而不受外部控制。
//                View viewSettling = mPagerSnapHelper.findSnapView(this);
                //int positionSettling = getPosition(viewSettling);
                break;

        }
    }



    /**
     * 监听竖直方向的相对偏移量
     */
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.mDrift = dy;
        return super.scrollVerticallyBy(dy, recycler, state);
    }


    /**
     * 监听水平方向的相对偏移量
     */
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.mDrift = dx;
        return super.scrollHorizontallyBy(dx, recycler, state);
    }

    /**
     * 设置监听
     */
    public void setOnViewPagerListener(OnViewPagerListener listener,List<Video> lists){
        this.mOnViewPagerListener = listener;
        this.lists=lists;
    }

    public void setOnViewPagerListenertwo(OnViewPagerListener listener, ArrayList<MyFaBuBean.DataBean> lists){
        this.mOnViewPagerListener = listener;
        this.liststwo=lists;
    }

    private RecyclerView.OnChildAttachStateChangeListener mChildAttachStateChangeListener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if (mOnViewPagerListener != null && getChildCount() == 1) {
                mOnViewPagerListener.onInitComplete();
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {
            if (mDrift >= 0){
                if (mOnViewPagerListener != null) {
                    mOnViewPagerListener.onPageRelease(true,getPosition(view));
                }
            }else {
                if (mOnViewPagerListener != null){
                    mOnViewPagerListener.onPageRelease(false,getPosition(view));
                }
            }

        }
    };




}
