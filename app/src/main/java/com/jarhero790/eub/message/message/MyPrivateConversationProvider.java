package com.jarhero790.eub.message.message;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.rong.imkit.model.ConversationProviderTag;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.PrivateConversationProvider;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

//会话列表界面
@ConversationProviderTag(conversationType = "private", portraitPosition = 1)
public class MyPrivateConversationProvider extends PrivateConversationProvider {


    @Override
    public void bindView(View view, int position, UIConversation data) {
        super.bindView(view, position, data);
//                TextView textView = (TextView) view;
//        textView.setTextColor(Color.parseColor("#3F3F3F"));
//        LinearLayout linearLayout= (LinearLayout) view;
//        linearLayout.setBackgroundColor(Color.RED);
//        if (data.getMessageContent(). == Message.MessageDirection.SEND) {
//
//        } else {
//            textView.setTextColor(Color.parseColor("#3F3F3F"));
//        }
    }

    //    @Override
//    public void bindView(View v, int position, TextMessage content, UIMessage data) {
//        super.bindView(v, position, content, data);
//        TextView textView = (TextView) v;
//        if (data.getMessageDirection() == Message.MessageDirection.SEND) {
//            textView.setTextColor(Color.parseColor("#3F3F3F"));
//        } else {
//            textView.setTextColor(Color.parseColor("#3F3F3F"));
//        }
//    }
}
