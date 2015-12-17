package com.bitdubai.sub_app.intra_user_community.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.SquareImageView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.intra_user_community.R;

/**
 * Created by mati on 2015.10.18..
 * Modified by Jose Manuel De Sousa Dos Santos
 */
public class AppWorldHolder extends FermatViewHolder {

    public FermatTextView city;
    public FermatTextView country;
    public SquareImageView thumbnail;
    public FermatTextView name;
    public LinearLayout locationContainer;
    public ImageView imageView_connection;

    /**
     * Constructor
     *
     * @param itemView cast elements in layout
     */
    public AppWorldHolder(View itemView) {
        super(itemView);
        locationContainer = (LinearLayout) itemView.findViewById(R.id.location);
        thumbnail = (SquareImageView) itemView.findViewById(R.id.profile_Image);
        name = (FermatTextView) itemView.findViewById(R.id.community_name);
        imageView_connection = (ImageView) itemView.findViewById(R.id.imageView_connection);
        city =(FermatTextView)itemView.findViewById(R.id.community_city);
        country = (FermatTextView)itemView.findViewById(R.id.community_country);
    }
}
