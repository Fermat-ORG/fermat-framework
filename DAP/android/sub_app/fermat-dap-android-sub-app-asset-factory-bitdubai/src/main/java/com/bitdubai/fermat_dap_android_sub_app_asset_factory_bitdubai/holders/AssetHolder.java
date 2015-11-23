package com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.R;

/**
 * Created by francisco on 01/10/15.
 */
public class AssetHolder extends FermatViewHolder {


    public LinearLayout rowView;
    public ImageView thumbnail;
    public FermatTextView name;
    public FermatTextView state;
    public FermatTextView amount;
    public FermatTextView bitcoins;

    /**
     * Constructor
     *
     * @param itemView
     */
    public AssetHolder(View itemView) {
        super(itemView);
        rowView = (LinearLayout) itemView.findViewById(R.id.row_view);
        thumbnail = (ImageView) itemView.findViewById(R.id.asset_image);
        name = (FermatTextView) itemView.findViewById(R.id.asset_name);
        state = (FermatTextView) itemView.findViewById(R.id.state);
        amount = (FermatTextView) itemView.findViewById(R.id.asset_amount);
        bitcoins = (FermatTextView) itemView.findViewById(R.id.asset_bitcoins);
    }
}
