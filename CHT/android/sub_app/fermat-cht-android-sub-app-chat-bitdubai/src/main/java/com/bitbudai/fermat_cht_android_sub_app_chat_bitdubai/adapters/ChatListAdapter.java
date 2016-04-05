package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.holders.ChatsListHolder;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.Utils;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeMessage;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;


import java.util.ArrayList;
import java.util.UUID;

/**
 * Chat List Adapter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 19/01/16.
 * @version 1.0
 *
 */

public class ChatListAdapter extends ArrayAdapter implements Filterable {//public class ChatListAdapter extends FermatAdapter<ChatsList, ChatHolder> {//ChatFactory

    ArrayList<String> contactName=new ArrayList<>();
    ArrayList<String> message=new ArrayList<>();
    ArrayList<String> dateMessage=new ArrayList<>();
    ArrayList<UUID> chatId=new ArrayList<>();
    ArrayList<UUID> contactId=new ArrayList<>();
    ArrayList<String> status=new ArrayList<>();
    ArrayList<String> typeMessage=new ArrayList<>();
    ArrayList<Integer> noReadMsgs=new ArrayList<>();
    ArrayList<Bitmap> imgId=new ArrayList<>();
    private ErrorManager errorManager;
    //Typeface tf;

    public ChatListAdapter(Context context, ArrayList<String> contactName,
                           ArrayList message,
                           ArrayList dateMessage,
                           ArrayList chatId,
                           ArrayList contactId,
                           ArrayList status,
                           ArrayList typeMessage,
                           ArrayList noReadMsgs,
                           ArrayList imgId, ErrorManager errorManager) {
        super(context, R.layout.chat_list_listview, contactName );
        //tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeue Medium.ttf");
        this.contactName = contactName;
        this.message = message;
        this.dateMessage = dateMessage;
        this.chatId = chatId;
        this.contactId = contactId;
        this.status = status;
        this.typeMessage = typeMessage;
        this.noReadMsgs = noReadMsgs;
        this.imgId=imgId;
        this.errorManager=errorManager;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.chat_list_listview, null, true);
        try {
            ImageView imagen = (ImageView) item.findViewById(R.id.image);//imagen.setImageResource(imgid.get(position));
            imagen.setImageBitmap(Utils.getRoundedShape(imgId.get(position), 400));

            TextView contactname = (TextView) item.findViewById(R.id.tvtitle);
            contactname.setText(contactName.get(position));
            //contactname.setTypeface(tf, Typeface.NORMAL);

            TextView lastmessage = (TextView) item.findViewById(R.id.tvdesc);
            lastmessage.setText(message.get(position));


            TextView dateofmessage = (TextView) item.findViewById(R.id.tvdate);
            dateofmessage.setText(dateMessage.get(position));

            ImageView imagetick = (ImageView) item.findViewById(R.id.imagetick);//imagen.setImageResource(imgid.get(position));
            imagetick.setImageResource(0);
            if(typeMessage.get(position).equals(TypeMessage.OUTGOING.toString())){
                imagetick.setVisibility(View.VISIBLE);
                if (status.get(position).equals(MessageStatus.SEND.toString()) /*|| status.get(position).equals(MessageStatus.CREATED.toString())*/)
                {    imagetick.setImageResource(R.drawable.cht_ticksent);}
                else if (status.get(position).equals(MessageStatus.DELIVERED.toString()) || status.get(position).equals(MessageStatus.RECEIVE.toString()))
                {    imagetick.setImageResource(R.drawable.cht_tickdelivered);}
                else if (status.get(position).equals(MessageStatus.READ.toString()))
                {    imagetick.setImageResource(R.drawable.cht_tickread);}
            }else
                imagetick.setVisibility(View.GONE);

            TextView tvnumber = (TextView) item.findViewById(R.id.tvnumber);
            if(noReadMsgs.get(position)>0)
            {
                tvnumber.setText(noReadMsgs.get(position).toString());
                tvnumber.setVisibility(View.VISIBLE);
            }else
                tvnumber.setVisibility(View.GONE);

        }catch (Exception e)
        {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
        return (item);
    }

    public void refreshEvents(ArrayList contactName,
                              ArrayList message,
                              ArrayList dateMessage,
                              ArrayList chatId,
                              ArrayList contactId,
                              ArrayList status,
                              ArrayList typeMessage,
                              ArrayList noReadMsgs,
                              ArrayList imgId) {
        this.contactName = contactName;
        this.message = message;
        this.dateMessage = dateMessage;
        this.chatId = chatId;
        this.contactId = contactId;
        this.status = status;
        this.typeMessage = typeMessage;
        this.noReadMsgs = noReadMsgs;
        this.imgId=imgId;
        notifyDataSetChanged();
    }
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