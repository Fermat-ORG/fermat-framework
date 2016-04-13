package com.bitdubai.sub_app.chat_community.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.chat_community.R;

/**
 * NotificationsHolder
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */
public class NotificationsHolder extends FermatViewHolder {

    public ImageView userAvatar;
    public FermatTextView userName;

    /**
     * Constructor
     *
     * @param itemView cast ui elements
     */
    public NotificationsHolder(View itemView) {
        super(itemView);
        userName = (FermatTextView) itemView.findViewById(R.id.username);
        userAvatar = (ImageView)itemView.findViewById(R.id.imageView_avatar);

    }
}
