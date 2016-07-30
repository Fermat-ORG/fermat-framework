package com.bitdubai.sub_app.chat_community.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.chat_community.R;

/**
 * NotificationsHolder
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */
public class NotificationHolder extends FermatViewHolder {

    public ImageView userAvatar;
    public TextView userName;
    public TextView date;

    /**
     * Constructor
     *
     * @param itemView cast ui elements
     */
    public NotificationHolder(View itemView) {
        super(itemView);
        userName = (TextView) itemView.findViewById(R.id.username);
        date = (TextView) itemView.findViewById(R.id.date);
        userAvatar = (ImageView) itemView.findViewById(R.id.imageView_avatar);

    }
}
