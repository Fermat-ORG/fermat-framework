package com.bitdubai.sub_app.chat_community.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.chat_community.R;

/**
 * CommunityWorldHolder
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */
public class CommunityWorldHolder extends FermatViewHolder {

    public ImageView thumbnail;//SquareImageView
    public TextView name;
    public ImageView connectionState;
    public TextView row_connection_state;
    public RadioButton connectedButton;
    public RadioButton blockedButton;
    public RadioButton pendingButton;

    /**
     * Constructor
     *
     * @param itemView cast elements in layout
     */
    public CommunityWorldHolder(View itemView) {
        super(itemView);
        //connectionState = (ImageView) itemView.findViewById(R.id.connection_state);
        connectedButton = (RadioButton) itemView.findViewById(R.id.connectedButton);
        blockedButton = (RadioButton) itemView.findViewById(R.id.blockedButton);
        pendingButton = (RadioButton) itemView.findViewById(R.id.pendingButton);
        row_connection_state = (TextView) itemView.findViewById(R.id.connection_state_user);
        thumbnail = (ImageView) itemView.findViewById(R.id.profile_image);
        name = (TextView) itemView.findViewById(R.id.community_name);
    }
}
