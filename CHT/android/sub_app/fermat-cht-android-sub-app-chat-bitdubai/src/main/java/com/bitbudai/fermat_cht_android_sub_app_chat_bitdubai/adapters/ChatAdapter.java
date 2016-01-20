package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters;
/**
 * ChatAdapter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 05/01/15.
 * @version 1.0
 */

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments.ChatFragment;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.ChatMessage;
//import com.bitdubai.fermat_cht_api.layer.cht_middleware.cht_chat_factory.interfaces.ChatFactory; //data del middleware
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.holders.ChatHolder;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

public class ChatAdapter extends FermatAdapter<ChatMessage, ChatHolder> {//ChatFactory

    //private final List<ChatMessage> chatMessages;
    List<ChatMessage> chatMessages = new ArrayList<>();

    //private Activity context;

    public ChatAdapter(Context context) {
        super(context);
    }

    public ChatAdapter(Context context, List<ChatMessage> chatMessages) {//ChatFactory
        super(context, chatMessages);
        //this.chatMessages = chatMessages;
    }

    /*@Override
    protected ChatHolder createHolder(View itemView) {
        return new ChatHolder(itemView);
    }*/

    @Override
    protected ChatHolder createHolder(View itemView, int type) {
        return new ChatHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {return R.layout.chat_list_item;  }

    @Override
    protected void bindHolder(ChatHolder holder, ChatMessage data, int position) {
        View convertView = getView();
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (data == null) {
            convertView = vi.inflate(R.layout.chat_list_item, null);
            holder = createHolder(convertView, position);
            convertView.setTag(holder);
        } else {
            holder = (ChatHolder) convertView.getTag();
        }

        boolean myMsg = data.getIsme() ;//test to simulate whether it me or other sender
        setAlignment(holder, myMsg);
        holder.txtMessage.setText(data.getMessage());
        holder.txtInfo.setText(data.getDate());

    }

    public int getCount() {
        if (chatMessages != null) {
            return chatMessages.size();
        } else {
            return 0;
        }
    }

    @Override
    public ChatMessage getItem(int position) {
        if (chatMessages != null) {
            return chatMessages.get(position);
        } else {
            return null;
        }
    }


    public long getItemId(int position) {
        return position;
    }

    public View getView() {
        View convertView;
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = vi.inflate(R.layout.chat_list_item, null);
        return convertView;
    }

    public void add(ChatMessage message) {
        chatMessages.add(message);
    }

    private void setAlignment(ChatHolder holder, boolean isMe) {
        if (!isMe) {
            //holder.contentWithBG.setBackgroundResource(R.drawable.in_message_bg);

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
        } else {
            //holder.contentWithBG.setBackgroundResource(R.drawable.out_message_bg);

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

}