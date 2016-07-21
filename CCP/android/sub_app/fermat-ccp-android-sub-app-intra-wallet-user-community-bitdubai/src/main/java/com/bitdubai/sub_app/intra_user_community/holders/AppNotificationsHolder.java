package com.bitdubai.sub_app.intra_user_community.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.intra_user_community.R;

/**
 * @author Jose Manuel De Sousa.
 */
public class AppNotificationsHolder extends FermatViewHolder {

    public ImageView userAvatar;
    public FermatTextView userName;
    public FermatTextView receptionTime;
    /**
     * Constructor
     *
     * @param itemView cast ui elements
     */
    public AppNotificationsHolder(View itemView) {
        super(itemView);
        userName = (FermatTextView) itemView.findViewById(R.id.username);
        userAvatar = (ImageView)itemView.findViewById(R.id.imageView_avatar);
        receptionTime = (FermatTextView) itemView.findViewById(R.id.reception_time);
    }
}
