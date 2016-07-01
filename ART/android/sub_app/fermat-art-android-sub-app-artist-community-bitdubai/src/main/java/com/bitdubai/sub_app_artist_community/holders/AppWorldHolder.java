package com.bitdubai.sub_app_artist_community.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.SquareImageView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.artist_community.R;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 08/04/16.
 */
public class AppWorldHolder extends FermatViewHolder {

    public ImageView thumbnail;
    public FermatTextView name;
    public ImageView connectionState;


    /**
     * Constructor
     *
     * @param itemView cast elements in layout
     */
    public AppWorldHolder(View itemView) {
        super(itemView);
        connectionState = (ImageView) itemView.findViewById(R.id.aac_connection_state);
        thumbnail = (ImageView) itemView.findViewById(R.id.profile_image);
        name = (FermatTextView) itemView.findViewById(R.id.aac_community_name);
    }
}
