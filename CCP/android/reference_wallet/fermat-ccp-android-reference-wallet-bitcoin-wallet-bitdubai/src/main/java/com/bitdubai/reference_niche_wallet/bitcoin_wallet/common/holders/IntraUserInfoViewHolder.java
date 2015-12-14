package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;

/**
 * Created by Matias Furszyfer on 2015.10.18..
 */
public class IntraUserInfoViewHolder extends FermatViewHolder {

    public ImageView thumbnail;
    public FermatTextView name;
    public RelativeLayout container_data;
    /**
     * Constructor
     *
     * @param itemView
     */
    public IntraUserInfoViewHolder(View itemView) {
        super(itemView);
        thumbnail = (ImageView) itemView.findViewById(R.id.intra_user_image);
        name = (FermatTextView) itemView.findViewById(R.id.intra_user_alias);
        container_data = (RelativeLayout) itemView.findViewById(R.id.container_data);
    }

    public ImageView getThumbnail() {
        return thumbnail;
    }

    public FermatTextView getName() {
        return name;
    }

    public RelativeLayout getContainer_data() {
        return container_data;
    }
}
