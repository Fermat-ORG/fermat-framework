package com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.R;

/**
 * Created by francisco on 01/10/15.
 */
public class AssetHolder extends FermatViewHolder {

    public FermatTextView title;
    public FermatTextView description;
    public FermatTextView state;
    public ImageView options;

    /**
     * Constructor
     *
     * @param itemView
     */
    public AssetHolder(View itemView) {
        super(itemView);
        title = (FermatTextView) itemView.findViewById(R.id.title);
        description = (FermatTextView) itemView.findViewById(R.id.description);
        state = (FermatTextView) itemView.findViewById(R.id.state);
        options = (ImageView) itemView.findViewById(R.id.options);
    }
}
