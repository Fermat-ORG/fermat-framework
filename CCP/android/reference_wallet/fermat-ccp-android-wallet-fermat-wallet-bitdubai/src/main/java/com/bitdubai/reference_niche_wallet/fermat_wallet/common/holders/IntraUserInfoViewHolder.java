package com.bitdubai.reference_niche_wallet.fermat_wallet.common.holders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;

/**
 * Created by Matias Furszyfer on 2015.10.18..
 */
public class IntraUserInfoViewHolder extends FermatViewHolder {

    public ImageView thumbnail;
    public FermatTextView name;
    public LinearLayout container_data;
    public CheckBox checkbox_connection;
    /**
     * Constructor
     *
     * @param itemView
     */
    public IntraUserInfoViewHolder(View itemView) {
        super(itemView);
        thumbnail = (ImageView) itemView.findViewById(R.id.intra_user_image);
        name = (FermatTextView) itemView.findViewById(R.id.intra_user_alias);
        container_data = (LinearLayout) itemView.findViewById(R.id.container_data);
        checkbox_connection = (CheckBox) itemView.findViewById(R.id.checkbox_connection);
    }
}
