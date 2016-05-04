package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.filters;

import android.graphics.Bitmap;
import android.widget.Filter;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ChatListAdapter;

import java.util.ArrayList;
import java.util.UUID;

/**
 * ContactListFilter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 16/04/16.
 * @version 1.0
 */
public class ChatListFilter extends Filter {

    ArrayList<String> contactName=new ArrayList<>();
    ArrayList<String> message=new ArrayList<>();
    ArrayList<String> dateMessage=new ArrayList<>();
    ArrayList<UUID> chatId=new ArrayList<>();
    ArrayList<String> contactId=new ArrayList<>();
    ArrayList<String> status=new ArrayList<>();
    ArrayList<String> typeMessage=new ArrayList<>();
    ArrayList<Integer> noReadMsgs=new ArrayList<>();
    ArrayList<Bitmap> imgId=new ArrayList<>();

    ArrayList<String> contactNameDatan=new ArrayList<>();
    ArrayList<String> messageDatan=new ArrayList<>();
    ArrayList<String> dateMessageDatan=new ArrayList<>();
    ArrayList<UUID> chatIdDatan=new ArrayList<>();
    ArrayList<String> contactIdDatan=new ArrayList<>();
    ArrayList<String> statusDatan=new ArrayList<>();
    ArrayList<String> typeMessageDatan=new ArrayList<>();
    ArrayList<Integer> noReadMsgsDatan=new ArrayList<>();
    ArrayList<Bitmap> imgIdDatan=new ArrayList<>();
    private ChatListAdapter adapter;

    public ChatListFilter(ArrayList contactName,
                          ArrayList message,
                          ArrayList dateMessage,
                          ArrayList chatId,
                          ArrayList contactId,
                          ArrayList status,
                          ArrayList typeMessage,
                          ArrayList noReadMsgs,
                          ArrayList imgId, ChatListAdapter adapter) {
        this.contactName = contactName;
        this.message = message;
        this.dateMessage = dateMessage;
        this.chatId = chatId;
        this.contactId = contactId;
        this.status = status;
        this.typeMessage = typeMessage;
        this.noReadMsgs = noReadMsgs;
        this.imgId=imgId;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        String filterString = constraint.toString().toLowerCase();
        adapter.setFilterString(filterString);

        FilterResults results = new FilterResults();

        int count = contactName.size();

        String filterableString;
        String resource;
        contactNameDatan.clear();
        messageDatan.clear();
        dateMessageDatan.clear();
        chatIdDatan.clear();
        contactIdDatan.clear();
        statusDatan.clear();
        typeMessageDatan.clear();
        noReadMsgsDatan.clear();
        imgIdDatan.clear();

        for (int i = 0; i < count; i++) {
            resource = contactName.get(i);
            filterableString = resource;
            if (filterableString.toLowerCase().contains(filterString)) {
                contactNameDatan.add(contactName.get(i));
                messageDatan.add(message.get(i));
                dateMessageDatan.add(dateMessage.get(i));
                chatIdDatan.add(chatId.get(i));
                contactIdDatan.add(contactId.get(i));
                statusDatan.add(status.get(i));
                typeMessageDatan.add(typeMessage.get(i));
                noReadMsgsDatan.add(noReadMsgs.get(i));
                imgIdDatan.add(imgId.get(i));
            }
        }
        results.values = contactNameDatan;
        results.count = contactNameDatan.size();

        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapter.setData((ArrayList<String>) filterResults.values, messageDatan, dateMessageDatan, chatIdDatan, contactIdDatan,
                statusDatan, typeMessageDatan, noReadMsgsDatan, imgIdDatan);
        adapter.notifyDataSetChanged();
    }
}
