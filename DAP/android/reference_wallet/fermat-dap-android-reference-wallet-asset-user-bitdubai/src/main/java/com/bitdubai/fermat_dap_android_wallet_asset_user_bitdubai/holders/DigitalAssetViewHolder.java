package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

/**
 * Created by francisco on 19/10/15.
 */
public class DigitalAssetViewHolder extends FermatViewHolder {

    public FermatTextView name;
    public FermatTextView balance;
    public ImageView options;

    /**
     * Constructor
     *
     * @param itemView
     */
    public DigitalAssetViewHolder(View itemView) {
        super(itemView);
        name = (FermatTextView) itemView.findViewById(R.id.name);
        balance = (FermatTextView) itemView.findViewById(R.id.balance);
        options = (ImageView) itemView.findViewById(R.id.options);
    }
}
