package com.bitdubai.sub_app.crypto_customer_community.common.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.SquareImageView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.crypto_customer_community.R;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class AppWorldHolder extends FermatViewHolder {

    public SquareImageView thumbnail;
    public FermatTextView name;
    public ImageView connectionState;

    /**
     * Constructor
     *
     * @param itemView cast elements in layout
     */
    public AppWorldHolder(View itemView, int type) {
        super(itemView, type);
        connectionState = (ImageView) itemView.findViewById(R.id.ccc_connection_state);
        thumbnail = (SquareImageView) itemView.findViewById(R.id.profile_image);
        name = (FermatTextView) itemView.findViewById(R.id.community_name);
    }
}
