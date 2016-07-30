package com.bitdubai.sub_app.chat_community.holders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    public Button add_contact_button;
    public ImageView connectionState;
    public TextView row_connection_state;
    public TextView location_text;
    public TextView connection_text;
    public ImageView connectedButton;
    public ImageView blockedButton;
    public ImageView pendingButton;
    public ImageView acceptButton;
//    public RadioButton connectedButton;
//    public RadioButton blockedButton;
//    public RadioButton pendingButton;

    /**
     * Constructor
     *
     * @param itemView cast elements in layout
     */
    public CommunityWorldHolder(View itemView) {
        super(itemView);
        //connectionState = (ImageView) itemView.findViewById(R.id.connection_state);
        add_contact_button = (Button) itemView.findViewById(R.id.add_contact_button);
        connectedButton = (ImageView) itemView.findViewById(R.id.connectedButton);
        blockedButton = (ImageView) itemView.findViewById(R.id.blockedButton);
        acceptButton = (ImageView) itemView.findViewById(R.id.acceptButton);
        pendingButton = (ImageView) itemView.findViewById(R.id.pendingButton);
        //row_connection_state = (TextView) itemView.findViewById(R.id.connection_state_user);
        connection_text = (TextView) itemView.findViewById(R.id.connection_text);
        thumbnail = (ImageView) itemView.findViewById(R.id.profile_image);
        name = (TextView) itemView.findViewById(R.id.community_name);
        location_text = (TextView) itemView.findViewById(R.id.location_text);
    }
}
