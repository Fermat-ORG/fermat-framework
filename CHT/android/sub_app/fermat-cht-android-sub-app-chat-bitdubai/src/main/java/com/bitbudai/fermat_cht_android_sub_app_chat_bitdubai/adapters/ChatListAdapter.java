package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.holders.ChatHolder;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.holders.ChatsListHolder;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.ChatsList;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.Utils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;


import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * Chat List Adapter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 19/01/16.
 * @version 1.0
 *
 */

public class ChatListAdapter extends FermatAdapter<ChatsList, ChatsListHolder> {//ChatFactory

    //private final LayoutInflater inflater;
    List<ChatsList> chatsList = new ArrayList<>();

    public ChatListAdapter(Context context) {
        super(context);
    }

    public ChatListAdapter(Context context, List<ChatsList> chatsList) {
        super(context, chatsList);
        //inflater = LayoutInflater.from(context);
    }

    @Override
    protected ChatsListHolder createHolder(View itemView, int type) {
        return new ChatsListHolder(itemView);
    }

    protected int getCardViewResource() {return R.layout.chats_item;  }

    @Override
    protected void bindHolder(ChatsListHolder holder, ChatsList data, int position) {
        View convertView = getView();
        /*if (convertView == null) {
            convertView = inflater.inflate(R.layout.chat_list_item, parent, false);
        }*/
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (data == null) {
            convertView = vi.inflate(R.layout.chat_list_item, null);
            holder = createHolder(convertView, position);
            convertView.setTag(holder);
        } else {
            holder = (ChatsListHolder) convertView.getTag();
        }

        //holder.message_icon_text.setText(data.getId());
        holder.firstLastName.setText(data.getName());
        holder.lastMessage.setText(data.getLastMessage());
        holder.contactItemTime.setText(data.getDate());

    }

    public View getView() {

        View convertView;
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = vi.inflate(R.layout.chats_item, null);

        TextView icon = (TextView) convertView.findViewById(R.id.message_icon_text);
        TextView firstLastName = (TextView) convertView.findViewById(R.id.firstLastName);
        TextView lastMessage = (TextView) convertView.findViewById(R.id.lastMessage);
        TextView time = (TextView) convertView.findViewById(R.id.contactItemTime);
        TextView notify = (TextView) convertView.findViewById(R.id.chat_notification);

        final ImageView imageIcon = (ImageView) convertView.findViewById(R.id.message_icon_image);

        imageIcon.setImageResource(R.drawable.cht_ic_placeholder);
        lastMessage.setText("");
        icon.setText("");

        Utils.verifySetBackground(icon, null);
/*
        TdApi.Chat item = getItem(position);
        TdApi.ChatInfo info = item.type;

        TdApi.MessageText text = null;
        TdApi.Message message = item.topMessage;

        long timeMls = (long) message.date;
        Date date = new Date(timeMls * 1000);

        if (message.message instanceof TdApi.MessageText) {
            text = (TdApi.MessageText) message.message;


            lastMessage.setTextColor(Color.BLACK);
            lastMessage.setText(text.textWithSmilesAndUserRefs);
        } else {
            lastMessage.setTextColor(getContext().getResources().getColor(R.color.content_text_color));
            if (message.message instanceof TdApi.MessagePhoto) {
                lastMessage.setText(R.string.message_photo);
            }
            if (message.message instanceof TdApi.MessageAudio) {
                lastMessage.setText(R.string.message_audio);
            }
            if (message.message instanceof TdApi.MessageContact) {
                lastMessage.setText(R.string.message_contact);
            }
            if (message.message instanceof TdApi.MessageDocument) {
                lastMessage.setText(R.string.message_document);
            }
            if (message.message instanceof TdApi.MessageGeoPoint) {
                lastMessage.setText(R.string.message_geopoint);
            }
            if (message.message instanceof TdApi.MessageSticker) {
                lastMessage.setText(R.string.message_sticker);
            }
            if (message.message instanceof TdApi.MessageVideo) {
                lastMessage.setText(R.string.message_video);
            }
            if (message.message instanceof TdApi.MessageUnsupported) {
                lastMessage.setText(R.string.message_unknown);
            }
        }

        TdApi.File file = null;
        long chatId = item.id;
        String userFirstName = "";
        String userLastName = "";

        if (info.getConstructor() == TdApi.PrivateChatInfo.CONSTRUCTOR) {
            TdApi.PrivateChatInfo privateChatInfo = (TdApi.PrivateChatInfo) info;
            TdApi.User chatUser = privateChatInfo.user;
            file = chatUser.photoBig;
            userFirstName = privateChatInfo.user.firstName;
            userLastName = privateChatInfo.user.lastName;
        }
        if (info.getConstructor() == TdApi.GroupChatInfo.CONSTRUCTOR) {
            TdApi.GroupChatInfo groupChatInfo = (TdApi.GroupChatInfo) info;
            file = groupChatInfo.groupChat.photoBig;
            userFirstName = groupChatInfo.groupChat.title;
            userLastName = "";
        }

        if (item.unreadCount != 0) {
            notify.setText(String.valueOf(item.unreadCount));
            Utils.verifySetBackground(notify, Utils.getShapeDrawable(R.dimen.chat_list_item_notification_size, getContext().getResources().getColor(R.color.message_notify)));
        } else {
            Utils.verifySetBackground(notify, null);
            notify.setText("");
        }

        Utils.setIcon(file, (int) chatId, userFirstName, userLastName, imageIcon, icon, (Activity) getContext());

        firstLastName.setText(userFirstName + " " + userLastName);
        time.setText(Utils.getDateFormat(Const.TIME_PATTERN).format(date));
*/
        return convertView;
    }

    public void add(ChatsList chats) {
        chatsList.add(chats);
    }
}
