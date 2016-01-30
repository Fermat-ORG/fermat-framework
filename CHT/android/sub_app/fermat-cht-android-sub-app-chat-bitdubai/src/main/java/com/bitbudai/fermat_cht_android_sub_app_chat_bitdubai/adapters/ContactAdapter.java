package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.ContactList;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;

import java.util.ArrayList;
import java.util.List;

//import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.holders.ChatsListHolder;

/**
 * Contact Adapter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 19/01/16.
 * @version 1.0
 *
 */

//public class ChatListAdapter extends FermatAdapter<ChatsList, ChatHolder> {//ChatFactory
public class ContactAdapter extends ArrayAdapter<String> {

    private final String[] addressDetail;
    private final String[] contactName;
    public ContactAdapter(Context context, String[] contactName, String[] addressDetail) {
        super(context, R.layout.contact_list_item, contactName);
        this.addressDetail = addressDetail;
        this.contactName = contactName;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.contact_detail_item, null, true);

        TextView name = (TextView) item.findViewById(R.id.contact_detail_header);
        name.setText(contactName[0]);

        TextView address = (TextView) item.findViewById(R.id.contact_detail);
        address.setText(addressDetail[0]);
        return item;
    }

}