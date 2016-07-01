package com.bitdubai.sub_app.chat_community.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.chat_community.R;

/**
 * ContactsListHolder
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */
public class ContactsListHolder extends FermatViewHolder {

    public ImageView friendAvatar;
    public TextView friendName;
    public TextView location;

    /**
     * Constructor
     *
     * @param itemView cast ui elements
     */
    public ContactsListHolder(View itemView) {
        super(itemView);
        friendName = (TextView) itemView.findViewById(R.id.username);
        friendAvatar = (ImageView) itemView.findViewById(R.id.imageView_avatar);
        location = (TextView) itemView.findViewById(R.id.location_text);
    }
}
