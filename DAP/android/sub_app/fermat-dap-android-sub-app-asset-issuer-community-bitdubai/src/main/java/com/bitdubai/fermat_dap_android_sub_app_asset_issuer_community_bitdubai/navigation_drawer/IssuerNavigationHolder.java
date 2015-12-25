package com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.navigation_drawer;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.R;

/**
 * Created by Nerio on 23/12/15.
 */
public class IssuerNavigationHolder extends FermatViewHolder {


    private TextView label;
    private ImageView icon;
    /**
     * Constructor
     *
     * @param itemView
     */
    protected IssuerNavigationHolder(View itemView) {
        super(itemView);

        label = (TextView) itemView.findViewById(R.id.textView_label);
        icon = (ImageView) itemView.findViewById(R.id.imageView_icon);
    }



    public TextView getLabel() {
        return label;
    }

    public ImageView getIcon() {
        return icon;
    }
}
