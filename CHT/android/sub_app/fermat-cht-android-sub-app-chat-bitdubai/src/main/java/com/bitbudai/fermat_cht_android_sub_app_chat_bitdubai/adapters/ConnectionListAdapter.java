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
import java.util.UUID;

//import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.holders.ChatsListHolder;

/**
 * Contact List Adapter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 19/01/16.
 * @version 1.0
 *
 */

//public class ChatListAdapter extends FermatAdapter<ChatsList, ChatHolder> {//ChatFactory
public class ConnectionListAdapter extends ArrayAdapter<String> {


    List<ContactList> contactsList = new ArrayList<>();
    ArrayList<String> contactinfo=new ArrayList<String>();
    ArrayList<Integer> contacticon=new ArrayList<Integer>();
    ArrayList<UUID> contactid=new ArrayList<UUID>();

    public ConnectionListAdapter(Context context, ArrayList contactinfo, ArrayList contacticon, ArrayList contactid) {
        super(context, R.layout.connection_list_item, contactinfo);
        this.contactinfo = contactinfo;
        this.contacticon = contacticon;
        this.contactid = contactid;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.connection_list_item, null, true);

        ImageView imagen = (ImageView) item.findViewById(R.id.icon);
        imagen.setImageResource(contacticon.get(position));

        TextView contactname = (TextView) item.findViewById(R.id.text1);
        contactname.setText(contactinfo.get(position));

        return item;
    }

    public void refreshEvents(ArrayList contactinfo, ArrayList contacticon, ArrayList contactid) {
        this.contactinfo=contactinfo;
        this.contacticon=contacticon;
        this.contacticon=contacticon;
        notifyDataSetChanged();
    }

}