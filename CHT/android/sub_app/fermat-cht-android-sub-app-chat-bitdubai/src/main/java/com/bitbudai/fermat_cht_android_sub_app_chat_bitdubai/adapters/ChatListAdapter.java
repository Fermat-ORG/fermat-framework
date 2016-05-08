package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.filters.ChatListFilter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.Utils;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeMessage;

import java.util.ArrayList;
import java.util.UUID;

//import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.holders.ChatsListHolder;

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
    ArrayList<String> contactId=new ArrayList<>();
    ArrayList<String> status=new ArrayList<>();
    ArrayList<String> typeMessage=new ArrayList<>();
    ArrayList<Integer> noReadMsgs=new ArrayList<>();
    ArrayList<Bitmap> imgId=new ArrayList<>();
    private ErrorManager errorManager;

    ArrayList<String> filteredData;
    private String filterString;

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
        this.contactName = contactName;
        this.message = message;
        this.dateMessage = dateMessage;
        this.chatId = chatId;
        this.contactId = contactId;
        this.status = status;
        this.typeMessage = typeMessage;
        this.noReadMsgs = noReadMsgs;
        this.imgId=imgId;
        this.filteredData = contactName;
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
            /*if(message.get(position).equals("Writing..")) {
                lastmessage.setTextColor(Color.parseColor("#FF33A900"));
            }else{*/
             //   lastmessage.setTextColor(Color.parseColor("#757575"));
            //}
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

    @Override
    public int getCount() {
        if (contactName != null) {
            if (filteredData != null) {
                if(filteredData.size()<contactName.size()) {
                    return filteredData.size();
                }else{
                    return contactName.size();
                }
            }else{
                return contactName.size();
            }
        } else {
            return 0;
        }
    }

    @Override
    public String getItem(int position) {
        return contactName.get(position);
    }

    public String getMessageItem(int position) {
        return message.get(position);
    }

    public String getDateMessageItem(int position) {
        return dateMessage.get(position);
    }

    public UUID getChatIdItem(int position) {
        return chatId.get(position);
    }

    public String getContactIdItem(int position) {
        return contactId.get(position);
    }

    public String getStatusItem(int position) {
        return status.get(position);
    }
    public String getTypeMessageItem(int position) {
        return typeMessage.get(position);
    }

    public int getNoReadMsgsItem(int position) {
        return noReadMsgs.get(position);
    }

    public Bitmap getImgIdItem(int position) {
        return imgId.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(ArrayList contactName,
                        ArrayList message,
                        ArrayList dateMessage,
                        ArrayList chatId,
                        ArrayList contactId,
                        ArrayList status,
                        ArrayList typeMessage,
                        ArrayList noReadMsgs,
                        ArrayList imgId) {
        this.filteredData = contactName;
        this.contactName = contactName;
        this.message = message;
        this.dateMessage = dateMessage;
        this.chatId = chatId;
        this.contactId = contactId;
        this.status = status;
        this.typeMessage = typeMessage;
        this.noReadMsgs = noReadMsgs;
        this.imgId=imgId;
    }

    public Filter getFilter() {
        return new ChatListFilter(contactName, message, dateMessage, chatId, contactId, status, typeMessage, noReadMsgs, imgId, this);
    }

    public void setFilterString(String filterString) {
        this.filterString = filterString;
    }

    public String getFilterString() {
        return filterString;
    }
}