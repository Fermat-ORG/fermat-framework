package com.bitdubai.sub_app.fan_community.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.SquareImageView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.fan_community.R;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/04/16.
 */
public class AppWorldHolder extends FermatViewHolder {

    public SquareImageView thumbnail;
    public FermatTextView name;
    public ImageView connectionState;

    /**
     * Default Constructor
     * @param itemView cast elements in layout
     */
    public AppWorldHolder(View itemView) {
        super(itemView);
        connectionState = (ImageView) itemView.findViewById(R.id.afc_connection_state);
        thumbnail = (SquareImageView) itemView.findViewById(R.id.profile_image);
        name = (FermatTextView) itemView.findViewById(R.id.afc_community_name);
    }
}