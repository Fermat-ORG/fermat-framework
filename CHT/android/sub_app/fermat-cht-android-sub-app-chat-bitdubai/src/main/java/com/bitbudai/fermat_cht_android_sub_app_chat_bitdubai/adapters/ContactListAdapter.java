package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.ChatsList;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.ContactList;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;

import java.util.ArrayList;
import java.util.List;

//import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.holders.ChatsListHolder;

/**
 * Contact List Adapter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 19/01/16.
 * @version 1.0
 *
 */

//public class ChatListAdapter extends FermatAdapter<ChatsList, ChatHolder> {//ChatFactory
public class ContactListAdapter extends ArrayAdapter<String> {

    List<ContactList> contactsList = new ArrayList<>();
    private final String[] contactinfo;
    private final Integer[] contacticon;
    //discoverActorsRegistered()
    public ContactListAdapter(Context context, String[] contactinfo, Integer[] contacticon) {
        super(context, R.layout.contact_list_item, contactinfo);
        this.contactinfo = contactinfo;
        this.contacticon = contacticon;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.contact_list_item, null, true);

        ImageView imagen = (ImageView) item.findViewById(R.id.icon);
        imagen.setImageResource(R.drawable.ic_contact_picture_holo_light);//contacticon[position]);

        TextView contactname = (TextView) item.findViewById(R.id.text1);
        contactname.setText(contactinfo[position]);
        return item;
    }
     /*public void refreshEvents(Parameters[] datos) {

        for(int i=0; i<datos.length; i++) {
            this.datos[i]=datos[i];
        }

        notifyDataSetChanged();

    }*/

//    @Override
//    protected ChatHolder createHolder(View itemView, int type) {
//        return new ChatHolder(itemView);
//    }
//
//    protected int getCardViewResource() {return R.layout.chats_item;  }
//
//    @Override
//    protected void bindHolder(ChatHolder holder, ChatsList data, int position) {
//        View convertView = getView();
//        /*if (convertView == null) {
//            convertView = inflater.inflate(R.layout.chat_list_item, parent, false);
//        }*/
//        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        if (data == null) {
//            convertView = vi.inflate(R.layout.chat_list_item, null);
//            holder = createHolder(convertView, position);
//            convertView.setTag(holder);
//        } else {
//            holder = (ChatHolder) convertView.getTag();
//        }
//
//        //holder.message_icon_text.setText(data.getId());
//       /* holder.firstLastName.setText(data.getName());
//        holder.lastMessage.setText(data.getLastMessage());
//        holder.contactItemTime.setText(data.getDate());*/
//
//    }
//
//    public View getView() {
//
//        View convertView;
//        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        convertView = vi.inflate(R.layout.chats_item, null);
//
//        TextView icon = (TextView) convertView.findViewById(R.id.message_icon_text);
//        TextView firstLastName = (TextView) convertView.findViewById(R.id.firstLastName);
//        TextView lastMessage = (TextView) convertView.findViewById(R.id.lastMessage);
//        TextView time = (TextView) convertView.findViewById(R.id.contactItemTime);
//        TextView notify = (TextView) convertView.findViewById(R.id.chat_notification);
//
//        final ImageView imageIcon = (ImageView) convertView.findViewById(R.id.message_icon_image);
//
//        imageIcon.setImageResource(R.drawable.cht_ic_placeholder);
//        lastMessage.setText("");
//        icon.setText("");
//
//        Utils.verifySetBackground(icon, null);
///*
//        TdApi.Chat item = getItem(position);
//        TdApi.ChatInfo info = item.type;
//        TdApi.MessageText text = null;
//        TdApi.Message message = item.topMessage;
//        long timeMls = (long) message.date;
//        Date date = new Date(timeMls * 1000);
//        if (message.message instanceof TdApi.MessageText) {
//            text = (TdApi.MessageText) message.message;
//            lastMessage.setTextColor(Color.BLACK);
//            lastMessage.setText(text.textWithSmilesAndUserRefs);
//        } else {
//            lastMessage.setTextColor(getContext().getResources().getColor(R.color.content_text_color));
//            if (message.message instanceof TdApi.MessagePhoto) {
//                lastMessage.setText(R.string.message_photo);
//            }
//            if (message.message instanceof TdApi.MessageAudio) {
//                lastMessage.setText(R.string.message_audio);
//            }
//            if (message.message instanceof TdApi.MessageContact) {
//                lastMessage.setText(R.string.message_contact);
//            }
//            if (message.message instanceof TdApi.MessageDocument) {
//                lastMessage.setText(R.string.message_document);
//            }
//            if (message.message instanceof TdApi.MessageGeoPoint) {
//                lastMessage.setText(R.string.message_geopoint);
//            }
//            if (message.message instanceof TdApi.MessageSticker) {
//                lastMessage.setText(R.string.message_sticker);
//            }
//            if (message.message instanceof TdApi.MessageVideo) {
//                lastMessage.setText(R.string.message_video);
//            }
//            if (message.message instanceof TdApi.MessageUnsupported) {
//                lastMessage.setText(R.string.message_unknown);
//            }
//        }
//        TdApi.File file = null;
//        long chatId = item.id;
//        String userFirstName = "";
//        String userLastName = "";
//        if (info.getConstructor() == TdApi.PrivateChatInfo.CONSTRUCTOR) {
//            TdApi.PrivateChatInfo privateChatInfo = (TdApi.PrivateChatInfo) info;
//            TdApi.User chatUser = privateChatInfo.user;
//            file = chatUser.photoBig;
//            userFirstName = privateChatInfo.user.firstName;
//            userLastName = privateChatInfo.user.lastName;
//        }
//        if (info.getConstructor() == TdApi.GroupChatInfo.CONSTRUCTOR) {
//            TdApi.GroupChatInfo groupChatInfo = (TdApi.GroupChatInfo) info;
//            file = groupChatInfo.groupChat.photoBig;
//            userFirstName = groupChatInfo.groupChat.title;
//            userLastName = "";
//        }
//        if (item.unreadCount != 0) {
//            notify.setText(String.valueOf(item.unreadCount));
//            Utils.verifySetBackground(notify, Utils.getShapeDrawable(R.dimen.chat_list_item_notification_size, getContext().getResources().getColor(R.color.message_notify)));
//        } else {
//            Utils.verifySetBackground(notify, null);
//            notify.setText("");
//        }
//        Utils.setIcon(file, (int) chatId, userFirstName, userLastName, imageIcon, icon, (Activity) getContext());
//        firstLastName.setText(userFirstName + " " + userLastName);
//        time.setText(Utils.getDateFormat(Const.TIME_PATTERN).format(date));
//*/
//        return convertView;
//    }
//
//    public void add(ChatsList chats) {
//        chatsList.add(chats);
//    }
}