package com.jarhero790.eub.message.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jarhero790.eub.R;
import com.jarhero790.eub.api.Api;
import com.jarhero790.eub.message.bean.OtherPingLBean;
import com.jarhero790.eub.message.souye.NoScrollListView;
import com.jarhero790.eub.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PingLenVideoAdapter extends RecyclerView.Adapter<PingLenVideoAdapter.MyHolder> {


    private Context context;
    private List<OtherPingLBean.DataBean.CommentListBean> list;
    private Myclick myclick;
    List<OtherPingLBean.DataBean.CommentListBean.UcommentBean> ucommentBeanList = new ArrayList<>();




    public PingLenVideoAdapter(Context context, List<OtherPingLBean.DataBean.CommentListBean> list, Myclick myclick) {
        this.context = context;
        this.list = list;
        this.myclick = myclick;


    }


    ListAdapter listAdapter=new ListAdapter();
    Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    listAdapter.notifyDataSetChanged();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = View.inflate(context, R.layout.item_pinglenvideo, null);
        return new MyHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int position) {
        OtherPingLBean.DataBean.CommentListBean bean = list.get(position);
        Glide.with(context).load(bean.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.zuanshi_logo).error(R.mipmap.zuanshi_logo)).into(myHolder.touImage);
        myHolder.tvName.setText(bean.getNickname());
        myHolder.content.setText(bean.getContent() + "  " + bean.getAddtime().substring(0,10));
        myHolder.tvZan.setText(CommonUtil.showzannum(bean.getZan()));


        myHolder.rltou.setTag(position);
        myHolder.rltou.setOnLongClickListener(myclick);


        ucommentBeanList = list.get(position).getUcomment();
        if (ucommentBeanList!=null && ucommentBeanList.size()>0){
            myHolder.lv.setVisibility(View.VISIBLE);
            myHolder.rlzhankan.setVisibility(View.VISIBLE);

            myHolder.lv.setAdapter(listAdapter);
            myHolder.tvtext.setText("展开"+ucommentBeanList.size()+"条回复");

            myHolder.tvtext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isShowMore){
                        myHolder.tvtext.setText("展开"+ucommentBeanList.size()+"条回复");
                        Log.e("--------------------1","展开");
                    }else {
                        myHolder.tvtext.setText("收起"+ucommentBeanList.size()+"条回复");
                        Log.e("--------------------2","收起");
                    }

                    isShowMore=!isShowMore;
                    mhandler.sendEmptyMessage(1);

                    Log.e("--------------------3","isShowMore="+isShowMore);
                }
            });


        }else {
            myHolder.lv.setVisibility(View.GONE);
            myHolder.rlzhankan.setVisibility(View.GONE);
        }



//
//        if (bean.getAddtime().length() > 9) {
//            myHolder.tvTime.setText(bean.getAddtime().substring(0, 10));
//        }
//
//
//        myHolder.tvGuanzu.setText(bean.getIs_likeEach() == 1 ? "已互关" : "+关注");
//
//        myHolder.tvGuanzu.setTag(position);
//        myHolder.tvGuanzu.setOnClickListener(myclick);

//
//        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//
//        long stamp=Long.valueOf(bean.getAddtime());
//          Date date=new Date(stamp*1000);


//        myHolder.tvPinglunText.setText(bean.getName());
//        myHolder.tvZhanli.setText(bean.getMoney()+"");

//        PinLenBean.DataBean dataBean=


//        myHolder.tvTgContext.setText(bean.getTitle());
//        myHolder.tvTgContextText.setText(bean.getContext());
//
//        myHolder.tvTgTime.setText(bean.getCreateTime());
//        myHolder.tvTgShape.setText("已分享" + bean.getCount() + "次");
//
//
//        if (bean.getImgs().equals("-1")){
//            myHolder.ivTgContextTu.setVisibility(View.GONE);
//            myHolder.llTgTu.setVisibility(View.GONE);
//        }
//
//
//
//        if (bean.getImgs().length() > 0 && bean.getImgs().contains("|")) {
//            myHolder.ivTgContextTu.setVisibility(View.GONE);
//            myHolder.llTgTu.setVisibility(View.VISIBLE);
//            String[] str = bean.getImgs().split("\\|");
//            if (str.length==3){
//                myHolder.ivTgContextTuone.setVisibility(View.VISIBLE);
//                myHolder.ivTgContextTutwo.setVisibility(View.VISIBLE);
//                myHolder.ivTgContextTuthree.setVisibility(View.VISIBLE);
//
//                Glide.with(context).load(str[0]).into(myHolder.ivTgContextTuone);//图片
//                Glide.with(context).load(str[1]).into(myHolder.ivTgContextTutwo);//图片
//                Glide.with(context).load(str[2]).into(myHolder.ivTgContextTuthree);//图片
//            }else if (str.length==2){
//                myHolder.ivTgContextTuone.setVisibility(View.VISIBLE);
//                myHolder.ivTgContextTutwo.setVisibility(View.VISIBLE);
//
//                Glide.with(context).load(str[0]).into(myHolder.ivTgContextTuone);//图片
//                Glide.with(context).load(str[1]).into(myHolder.ivTgContextTutwo);//图片
//            }else {
//                myHolder.ivTgContextTuone.setVisibility(View.VISIBLE);
//                Glide.with(context).load(str[0]).into(myHolder.ivTgContextTuone);//图片
//            }
//
//        }
//
//
//
//        if (bean.getImgs().length() > 0 && !bean.getImgs().contains("|")) {
//            Glide.with(context).load(bean.getImgs()).into(myHolder.ivTgContextTu);//图片
//        }


    }


    public static abstract class Myclick implements View.OnLongClickListener {
        public abstract void myClick(int position, View view);

        @Override
        public boolean onLongClick(View v) {
            myClick((Integer) v.getTag(), v);
            return true;
        }

//        @Override
//        public void onClick(View v) {
//
//        }
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tou_image)
        CircleImageView touImage;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_xin)
        ImageView ivXin;
        @BindView(R.id.tv_zan)
        TextView tvZan;
        @BindView(R.id.rl_tou)
        RelativeLayout rltou;
        @BindView(R.id.content)
        TextView content;

        @BindView(R.id.lv)
        NoScrollListView lv;
        @BindView(R.id.rl_zhankan)
        RelativeLayout rlzhankan;

        @BindView(R.id.tv_text)
        TextView tvtext;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private boolean isShowMore = false;
    private int mCount = 1;

    class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            Log.e("--------------------4","isShowMore="+isShowMore);
            if (isShowMore){
                return ucommentBeanList.size();
            }else {
                return mCount;
            }

        }

        @Override
        public OtherPingLBean.DataBean.CommentListBean.UcommentBean getItem(int position) {
            return ucommentBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_pinglenvideo2, null);
                holder=new ViewHolder(convertView);
                convertView.setTag(holder);
            }else {
                holder= (ViewHolder) convertView.getTag();
            }
            holder.bindView(ucommentBeanList.get(position));
            return convertView;
        }


        class ViewHolder {
            @BindView(R.id.tou_image_two)
            CircleImageView touImageTwo;
            @BindView(R.id.tv_name)
            TextView tvName2;
            @BindView(R.id.iv_xin)
            ImageView ivXin;
            @BindView(R.id.tv_zan)
            TextView tvZan;
            @BindView(R.id.rl)
            RelativeLayout rl;
            @BindView(R.id.content)
            TextView content2;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }

            public void bindView(OtherPingLBean.DataBean.CommentListBean.UcommentBean bean){
                Glide.with(context).load(bean.getHeadimgurl()).apply(new RequestOptions().placeholder(R.mipmap.zuanshi_logo).error(R.mipmap.zuanshi_logo)).into(touImageTwo);
                tvName2.setText(bean.getNickname());
                content2.setText(bean.getContent());

            }
        }
    }

}
