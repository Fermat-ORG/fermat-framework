package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.filters.ChatFilter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.holders.ChatHolder;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.ChatMessage;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;

import java.util.ArrayList;

/**
 * ChatAdapter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 05/01/15.
 * @version 1.0
 */

public class ChatAdapter extends FermatAdapter<ChatMessage, ChatHolder>
        implements Filterable {

    ArrayList<ChatMessage> chatMessages = new ArrayList<>();
    ArrayList<ChatMessage> messagesData = new ArrayList<>();

    ArrayList<ChatMessage> filteredData;
    private String filterString;

    public ChatAdapter(Context context) {
        super(context);
    }

    public ChatAdapter(Context context, ArrayList<ChatMessage> chatMessages) {//ChatFactory
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
            final String copiedMessage = holder.txtMessage.getText().toString();
            holder.txtMessage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("simple text",copiedMessage);
                        clipboard.setPrimaryClip(clip);}
                    else{
                        android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        clipboard.setText(copiedMessage);
                    }
                    Toast.makeText(context, "Copied: "+copiedMessage, Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
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
    }

//    public int getCount() {
//        if (chatMessages != null) {
//            if (filteredData != null) {
//                if (filteredData.size() <= chatMessages.size()) {
//                    return filteredData.size();
//                } else {
//                    return chatMessages.size();
//                }
//            }else  return chatMessages.size();
//        } else {
//            return 0;
//        }
//    }
//
//    @Override
//    public ChatMessage getItem(int position) {
//        ChatMessage dataM;
//        if (chatMessages != null) {
//            if (filteredData != null) {
//                if (filteredData.size() <= chatMessages.size()) {
//                    dataM= filteredData.get(position);
//                } else {
//                    dataM= chatMessages.get(position);
//                }
//            }else dataM=chatMessages.get(position);
//        } else {
//            dataM=chatMessages.get(position);
//        }
//        return dataM;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }

    public void changeDataSet(ArrayList<ChatMessage> data) {
        if(filterString.equals(""))
            this.chatMessages = data;
        else
            this.filteredData = data;
    }

    public Filter getFilter() {
//        messagesData=null;
//        for(ChatMessage data:chatMessages){
//            messagesData.add(data);
//        }
        return new ChatFilter(chatMessages, this);
    }

    public void setFilterString(String filterString) {
        this.filterString = filterString;
    }

    public String getFilterString() {
        return filterString;
    }

}