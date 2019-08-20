package com.jarhero790.eub.adapter;

import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jarhero790.eub.R;
import com.jarhero790.eub.bean.MessageEntity;
import com.jarhero790.eub.bean.MessageLike;
import com.jarhero790.eub.bean.MessageSystem;
import com.jarhero790.eub.utils.AppUtils;
import java.util.List;

public class MessageAdapter extends BaseMultiItemQuickAdapter<MessageEntity, BaseViewHolder> {

    public MessageAdapter(List<MessageEntity> data) {
        super(data);
        addItemType(MessageEntity.TYPE_SYSTEM, R.layout.fragment_message_system_recycleview_item);
        addItemType(MessageEntity.TYPE_LIKE, R.layout.fragment_message_like_recycleview_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageEntity messageEntity) {
        switch (messageEntity.getItemType()) {
            case MessageEntity.TYPE_SYSTEM:
                MessageSystem messageSystem = messageEntity.getMessageSystem();
//                helper.setText(R.id.system_name, messageSystem.getAdmin());
//                helper.setText(R.id.system_content, messageSystem.getContent());
//                Glide.with(mContext).load(messageSystem.getImg()).into((ImageView) helper.getView(R.id.system_userIcon));

            case MessageEntity.TYPE_LIKE:
                MessageLike messageLike = messageEntity.getMessageLike();
//                if(messageLike==null){
//                    Toast.makeText(AppUtils.getContext(),"哈哈哈",Toast.LENGTH_LONG).show();
//                    return;
//                }
//                Toast.makeText(AppUtils.getContext(),"哈哈哈11111122222222",Toast.LENGTH_LONG).show();
//
//                Toast.makeText(AppUtils.getContext(),"哈哈哈11111122222222"+messageLike.getNickname(),Toast.LENGTH_LONG).show();
//                 helper.setText(R.id.like_name, messageLike.getNickname());
//                if(messageLike.getIs_likeEach().equals("1")){
//                    helper.setText(R.id.like_content, "他也关注了你，快和他畅聊吧！");
//                }
//                Glide.with(mContext).load(messageLike.getHeadimgurl()).into((ImageView) helper.getView(R.id.like_icon));

        }
    }
}