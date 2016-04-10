package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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

public class ChatAdapter extends FermatAdapter<ChatMessage, ChatHolder> {//ChatFactory

    //private final List<ChatMessage> chatMessages;
    List<ChatMessage> chatMessages = new ArrayList<>();
    //Typeface tf;
    //Typeface tf2;

    //private Activity context;

    public ChatAdapter(Context context) {
        super(context);
    }

    public ChatAdapter(Context context, List<ChatMessage> chatMessages) {//ChatFactory
        super(context, chatMessages);
        //tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeue Medium.ttf");
        //tf2 = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeue Light.ttf");
        //super(context, R.layout.chat_list_item);
//        this.chatMessages=chatMessages;
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
//        View convertView = getView();
//        //LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        LayoutInflater vi = LayoutInflater.from(context) ;
//        FermatTextView lblTitulo = (FermatTextView) convertView.findViewById(R.id.txtInfo);
//        lblTitulo.setText(data.getMessage());
//
//        FermatTextView lblSubtitulo = (FermatTextView) convertView.findViewById(R.id.txtMessage);
//        lblSubtitulo.setText(data.getMessage());
//
//        if (data == null) {
//            convertView = vi.inflate(R.layout.chat_list_item, null);
//            holder = createHolder(convertView, position);
//            convertView.setTag(holder);
//        } else {
//            holder = (ChatHolder) convertView.getTag();
//        }
        if (data != null) {
            boolean myMsg = data.getIsme();
            setAlignment(holder, myMsg, data);
            //holder.txtMessage.setText(data.getMessage());
            //holder.txtMessage.setTypeface(tf2, Typeface.NORMAL);
            //holder.txtInfo.setText(data.getDate());
            //holder.txtInfo.setTypeface(tf, Typeface.NORMAL);
//            if (data.getStatus() != null && myMsg==true && data.getType()== TypeMessage.OUTGOING.toString()) {
//                if (data.getStatus() == MessageStatus.SEND.toString() || data.getStatus() == MessageStatus.CREATED.toString())
//                    holder.tickstatusimage.setImageResource(R.drawable.cht_ticksent);
//                else if (data.getStatus() == MessageStatus.DELIVERED.toString() || data.getStatus() == MessageStatus.RECEIVE.toString())
//                    holder.tickstatusimage.setImageResource(R.drawable.cht_tickdelivered);
//                else if (data.getStatus() == MessageStatus.READ.toString())
//                    holder.tickstatusimage.setImageResource(R.drawable.cht_tickread);
//            }
        }
    }

    public int getCount() {
        if (chatMessages != null) {
            return chatMessages.size();
        } else {
            return 0;
        }
    }
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

    public View getView() {
        //View convertView = getView();
        //LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // LayoutInflater vi = LayoutInflater.from(context);
        LayoutInflater vi = LayoutInflater.from(context) ;

        View convertView = vi.inflate(R.layout.chat_list_item, null);

        // LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        /*if (data == null) {
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
        setAlignment(holder, data.getIsme());*/
        return convertView;


        /*LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.chat_list_item, null);*/
       /* LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = vi.inflate(R.layout.chat_list_item, null);
        FermatTextView lblTitulo = (FermatTextView)convertView.findViewById(R.id.txtInfo);
        lblTitulo.setText(datos[position].getTitulo());
        FermatTextView lblSubtitulo = (FermatTextView)convertView.findViewById(R.id.txtMessage);
        lblSubtitulo.setText(datos[position].getSubtitulo());
        return(convertView);//return(item);*/
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
            //layoutParams.leftMargin=10;
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

}