package com.bitdubai.sub_app.intra_user_community.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.SquareImageView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.intra_user_community.R;

/**
 * Created by mati on 2015.10.18..
 */
public class IntraUserInformationHolder extends FermatViewHolder {

    public FermatTextView city;
    public FermatTextView country;
    public SquareImageView thumbnail;
    public FermatTextView name;
    public ImageView imageView_connection;

    /**
     * Constructor
     *
     * @param itemView
     */
    public IntraUserInformationHolder(View itemView) {
        super(itemView);
        thumbnail = (SquareImageView) itemView.findViewById(R.id.profile_Image);
        name = (FermatTextView) itemView.findViewById(R.id.community_name);
        imageView_connection = (ImageView) itemView.findViewById(R.id.imageView_connection);
        city =(FermatTextView)itemView.findViewById(R.id.community_city);
        country = (FermatTextView)itemView.findViewById(R.id.community_country);
    }
}
