package com.jarhero790.eub.message.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jarhero790.eub.R;
import com.jarhero790.eub.message.bean.SearchBean;
import com.jarhero790.eub.utils.CommonUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;
import silladus.basic.adapter.page.BasePage;
import silladus.basic.adapter.recyclerview.GridItemDecoration;

/**
 * Created by silladus on 2018/6/12/0012.
 * GitHub: https://github.com/silladus
 * Description:
 */
public class MenuPage extends BasePage<SearchBean.DataBean.VisitBean, RecyclerView, MultiTypeAdapter>
        implements OnItemClickListener {
    public MenuPage(int reqCode, int pagerIndex, Context mContext) {
        super(reqCode, pagerIndex, mContext, R.layout.page_item_recyclerview_home_menu);

        setMode(PtrFrameLayout.Mode.NONE);
    }

    @Override
    public MultiTypeAdapter createListAdapter(RecyclerView listView) {
        MultiTypeAdapter adapter = new MultiTypeAdapter();
        adapter.register(SearchBean.DataBean.VisitBean.class, new MenuItemViewBinder(getPagerIndex(), this));
        GridLayoutManager lm = new GridLayoutManager(listView.getContext(), 3);
        listView.setLayoutManager(lm);

        listView.addItemDecoration(new GridItemDecoration(0, 20, 3));
        listView.setHasFixedSize(true);
        listView.setNestedScrollingEnabled(false);
        listView.setAdapter(adapter);
        return adapter;
    }

    private void setItemCheck(int position) {
        for (int i = 0; i < getListAdapter().getItemCount(); i++) {
            SearchBean.DataBean.VisitBean menu = (SearchBean.DataBean.VisitBean) getListAdapter().getItems().get(i);
            Log.e("------menu:", menu.getId() + " " + menu.getCaifu());
//            SearchBean.DataBean.VisitBean.Men men = (SearchBean.DataBean.VisitBean.Men) getListAdapter().getItems().get(i);
//            if (i == position) {
//                if (!men.isCheck) {
//                    men.isCheck = true;
//                    getListAdapter().notifyItemChanged(i);
//                }
//                continue;
//            }
//            if (men.isCheck) {
//                men.isCheck = false;
//                getListAdapter().notifyItemChanged(i);
//            }
        }

    }

    public void setItemCheck(int position, boolean isCheck) {
        if (isCheck) {
            setItemCheck(position);
        } else {
            setItemUncheck(position);
        }
    }

    /**
     * 某一页选中某个item时之前页选中的item要取消选中，
     * 但未显示过的页面数据可能尚未初始化从而抛出数组越界异常，这里直接捕获异常解决
     *
     * @param position 选中的位置
     */
    private void setItemUncheck(int position) {
        try {
            SearchBean.DataBean.VisitBean menu =
                    (SearchBean.DataBean.VisitBean) getListAdapter()
                            .getItems()
                            .get(position);
//            SearchBean.DataBean.VisitBean.Men men = (SearchBean.DataBean.VisitBean.Men) getListAdapter().getItems().get(position);
//            men.isCheck = false;
//            getListAdapter().notifyItemChanged(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveData(List<SearchBean.DataBean.VisitBean> mData, String tipsText) {
        getData().clear();
        getData().addAll(mData);
        getListAdapter().setItems(mData);
        getListAdapter().notifyDataSetChanged();

        mPtrFrameLayout.refreshComplete();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void initData(int reqCode, int partIndex, int pagerIndex) {
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onItemClick(View view, int i, int pagerPosition) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, i, pagerPosition);
        }
    }


    static class MenuItemViewBinder extends ItemViewBinder<SearchBean.DataBean.VisitBean, MenuItemViewBinder.ViewHolder> {

        private OnItemClickListener onItemClickListener;
        private int pagerIndex;

        public MenuItemViewBinder(int pagerIndex, OnItemClickListener onItemClickListener) {
            this.pagerIndex = pagerIndex;
            this.onItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
            return new ViewHolder(inflater.inflate(R.layout.item_gv_home, parent, false));
        }

        @Override
        protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final SearchBean.DataBean.VisitBean item) {


            //tu
//            if (item.getIcon() == null) {
//                holder.fragment0GvItemIv.setImageResource(R.mipmap.ic_more_bank);
//            } else {
//
//                if (item.getState().equals("0")) {
//                    Glide.with(holder.itemView.getContext())
//                            .load(item.getIcon())
//                            .into(holder.fragment0GvItemIv);
//
//                    if (item.getCost().contains(".")) {
//                        int b = item.getCost().indexOf(".");
//                        holder.tvcost.setText("¥" + item.getCost().substring(0, b));
//                    } else {
//                        holder.tvcost.setText("¥" + item.getCost());
//                    }
//
//                } else {
//                    if (item.getCost2()!=null){
//                        if (item.getCost2().contains(".")) {
//                            int b = item.getCost2().indexOf(".");
//                            holder.tvcost.setText("¥" + item.getCost2().substring(0, b));
//                        } else {
//                            holder.tvcost.setText("¥" + item.getCost2());
//                        }
//                    }
//
//
//
//                    Glide.with(holder.itemView.getContext()).load(item.getIcon2())
////                            .placeholder(R.mipmap.icon_progress_loading)
////                            .diskCacheStrategy(DiskCacheStrategy.NONE)
//                            .into(holder.fragment0GvItemIv);
//                }
//            }
//            holder.fragment0GvItemTv.setText(item.getName());
//
//
//            SearchBean.DataBean.VisitBean.Men me = new SearchBean.DataBean.VisitBean.Men();////////////
//            if (me.isCheck) {
//                buildBg(holder.llContainer);
//            } else {
//                holder.llContainer.setBackgroundColor(Color.TRANSPARENT);
//            }


            Glide.with(holder.itemView.getContext()).load(item.getVideo_img()).apply(new RequestOptions().placeholder(R.mipmap.mine_bg).error(R.mipmap.mine_bg)).into(holder.ivIcons);
            holder.renNum.setText(CommonUtil.showzannum(item.getVisit_val()));

            holder.itemView.setTag(getPosition(holder));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, (Integer) v.getTag(), pagerIndex);
                    }
                }
            });
//            holder.itemView.setOnClickListener(v -> {
//
//            });
        }

        private void buildBg(View v) {
            GradientDrawable bgDrawable = new GradientDrawable();
            bgDrawable.setShape(GradientDrawable.RECTANGLE);
//            bgDrawable.setCornerRadius(DrawableUtil.dp2px(v.getContext(), 6));
//            bgDrawable.setStroke(DrawableUtil.dp2px(v.getContext(), 1), Color.LTGRAY);
            v.setBackground(bgDrawable);
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.iv_icon)
            ImageView ivIcons;
            @BindView(R.id.ren_num)
            TextView renNum;
            @BindView(R.id.ivhot)
            ImageView ivhot;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }


}
