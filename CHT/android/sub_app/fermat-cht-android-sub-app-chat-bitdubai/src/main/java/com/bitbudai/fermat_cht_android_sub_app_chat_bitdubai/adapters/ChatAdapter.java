package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.filters.ChatFilter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.filters.ChatListFilter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.holders.ChatHolder;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.ChatMessage;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;

import java.util.ArrayList;
import java.util.List;

//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.RecyclerView;
//import com.bitdubai.fermat_cht_api.layer.cht_middleware.cht_chat_factory.interfaces.ChatFactory; //data del middleware

/**
 * ChatAdapter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 05/01/15.
 * @version 1.0
 */

public class ChatAdapter extends FermatAdapter<ChatMessage, ChatHolder> /*implements Filterable*/ {

    List<ChatMessage> chatMessages = new ArrayList<>();
    ArrayList<String> messagesData = new ArrayList<>();

    ArrayList<ChatMessage> filteredData;
    ArrayList<String> originalData;
    private String filterString;

    public ChatAdapter(Context context) {
        super(context);
    }

    public ChatAdapter(Context context, List<ChatMessage> chatMessages) {//ChatFactory
        super(context, chatMessages);
    }

    @Override
    protected ChatHolder createHolder(View itemView, int type) {
        return new ChatHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {return R.layout.chat_list_item;  }

    @Override
    protected void bindHolder(ChatHolder holder, ChatMessage data, int position) {
        if (data != null) {
            boolean myMsg = data.getIsme();
            setAlignment(holder, myMsg, data);
        }
    }

    public View getView() {
        LayoutInflater vi = LayoutInflater.from(context) ;
        View convertView = vi.inflate(R.layout.chat_list_item, null);
        return convertView;
    }

    public void refreshEvents(ArrayList<ChatMessage> chatHistory) {
        for (int i = 0; i < chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            add(message);
            changeDataSet(chatHistory);
            notifyDataSetChanged();
        }
    }

    public void add(ChatMessage message) {
        chatMessages.add(message);
    }

    private void setAlignment(ChatHolder holder, boolean isMe, ChatMessage data) {
        holder.tickstatusimage.setImageResource(0);
        holder.txtMessage.setText(data.getMessage());
        holder.txtInfo.setText(data.getDate());
        if (isMe) {
            holder.contentWithBG.setBackgroundResource(R.drawable.burble_green_shadow);

            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.contentWithBG.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.content.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.txtMessage.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) holder.txtInfo.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.txtInfo.setLayoutParams(layoutParams);
            if(data.getStatus() != null) {
                if (data.getStatus().equals(MessageStatus.SEND.toString()) /*|| data.getStatus().equals(MessageStatus.CREATED.toString())*/)
                    holder.tickstatusimage.setImageResource(R.drawable.cht_ticksent);
                else if (data.getStatus().equals(MessageStatus.DELIVERED.toString()) || data.getStatus().equals(MessageStatus.RECEIVE.toString()))
                    holder.tickstatusimage.setImageResource(R.drawable.cht_tickdelivered);
                else if (data.getStatus().equals(MessageStatus.READ.toString()))
                    holder.tickstatusimage.setImageResource(R.drawable.cht_tickread);
            }
        } else {
            holder.contentWithBG.setBackgroundResource(R.drawable.burble_white_shadow);

            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.contentWithBG.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            holder.content.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.txtMessage.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) holder.txtInfo.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.txtInfo.setLayoutParams(layoutParams);
        }
//        if (!isMe) {
//            holder.contentWithBG.setBackgroundResource(R.drawable.out_message_bg);
//
//            LinearLayout.LayoutParams layoutParams =
//                    (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
//            layoutParams.gravity = Gravity.START;
//            holder.contentWithBG.setLayoutParams(layoutParams);
//
//            RelativeLayout.LayoutParams lp =
//                    (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
//            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
//            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//            holder.content.setLayoutParams(lp);
//            layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
//            layoutParams.gravity = Gravity.START;
//            holder.txtMessage.setLayoutParams(layoutParams);
//
//            layoutParams = (LinearLayout.LayoutParams) holder.txtInfo.getLayoutParams();
//            layoutParams.gravity = Gravity.START;
//            holder.txtInfo.setLayoutParams(layoutParams);
//        } else {
//            holder.contentWithBG.setBackgroundResource(R.drawable.in_message_bg);
//
//            LinearLayout.LayoutParams layoutParams =
//                    (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
//            layoutParams.gravity = Gravity.END;
//            holder.contentWithBG.setLayoutParams(layoutParams);
//
//            RelativeLayout.LayoutParams lp =
//                    (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
//            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
//            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//            holder.content.setLayoutParams(lp);
//            layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
//            layoutParams.gravity = Gravity.END;
//            holder.txtMessage.setLayoutParams(layoutParams);
//
//            layoutParams = (LinearLayout.LayoutParams) holder.txtInfo.getLayoutParams();
//            layoutParams.gravity = Gravity.END;
//            holder.txtInfo.setLayoutParams(layoutParams);
//        }
    }

//    public int getCount() {
//        if (chatMessages != null) {
//            if(filteredData.size()<chatMessages.size()) {
//                return filteredData.size();
//            }else{
//                return chatMessages.size();}
//        } else {
//            return 0;
//        }
//    }
//
//    @Override
//    public ChatMessage getItem(int position) {
//        return filteredData.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    public void setData(ArrayList<ChatMessage> data) {
//        this.filteredData = data;
//    }
//
//    public Filter getFilter() {
//        messagesData=null;
//        for(ChatMessage data:chatMessages){
//            messagesData.add(data.getMessage());
//        }
//        return new ChatFilter(messagesData, this);
//    }
//
//    public void setFilterString(String filterString) {
//        this.filterString = filterString;
//    }
//
//    public String getFilterString() {
//        return filterString;
//    }


//
//    @Override
//    public ChatMessage getItem(int position) {
//        if (chatMessages != null) {
//            return chatMessages.get(position);
//        } else {
//            return null;
//        }
//    }
//
//
//    public long getItemId(int position) {
//        return position;
//    }


}