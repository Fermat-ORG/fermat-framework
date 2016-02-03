package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.ContactList;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    private final ArrayList<String> contactAlias;
    private final ArrayList<String> contactName;
    private final ArrayList<UUID> contactUUID;
    private final String action;
    public ContactAdapter(Context context, ArrayList<String> contactName, ArrayList<String> contactAlias,ArrayList<UUID> contactUUID, String action) {
        super(context, R.layout.contact_detail_item, contactName);
//        switch (action)
//        {
//            case "detail":
//                super(context, R.layout.contact_detail_item, contactName);
//                break;
//            case "edit":
//                super(context, R.layout.contact_edit_item, contactName);
//                break;
//        }
        this.contactAlias = contactAlias;
        this.contactName = contactName;
        this.contactUUID = contactUUID;
        this.action = action;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.contact_detail_item, null, true);
        switch (action)
        {
            case "edit":
                item = inflater.inflate(R.layout.contact_edit_item, null, true);

                FermatTextView name = (FermatTextView) item.findViewById(R.id.contact_detail_header);
                name.setText(contactName.get(0));

                EditText alias = (EditText) item.findViewById(R.id.aliasEdit);
                alias.setText(contactAlias.get(0));
                break;
            case "detail":
                item = inflater.inflate(R.layout.contact_detail_item, null, true);

                FermatTextView name2 = (FermatTextView) item.findViewById(R.id.contact_detail_header);
                name2.setText(contactName.get(0));

                FermatTextView alias2 = (FermatTextView) item.findViewById(R.id.alias);
                alias2.setText(contactAlias.get(0));
                break;
        }
        return item;
    }

}