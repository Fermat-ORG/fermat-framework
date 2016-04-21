package com.bitdubai.sub_app.chat_community.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.SquareImageView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.chat_community.R;

/**
 * CommunityWorldHolder
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */
public class CommunityWorldHolder extends FermatViewHolder {

    public SquareImageView thumbnail;
    public FermatTextView name;
    public ImageView connectionState;
    public FermatTextView row_connection_state;

    /**
     * Constructor
     *
     * @param itemView cast elements in layout
     */
    public CommunityWorldHolder(View itemView) {
        super(itemView);
        connectionState = (ImageView) itemView.findViewById(R.id.connection_state);
        row_connection_state = (FermatTextView) itemView.findViewById(R.id.connection_state_user);
        thumbnail = (SquareImageView) itemView.findViewById(R.id.profile_image);
        name = (FermatTextView) itemView.findViewById(R.id.community_name);
    }
}
