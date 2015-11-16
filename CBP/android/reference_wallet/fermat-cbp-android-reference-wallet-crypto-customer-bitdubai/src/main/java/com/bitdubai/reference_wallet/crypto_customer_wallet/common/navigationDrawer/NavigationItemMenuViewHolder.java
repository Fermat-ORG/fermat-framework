package com.bitdubai.reference_wallet.crypto_customer_wallet.common.navigationDrawer;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;


/**
 * Created by Matias Furszyfer 22/09/2015
 */


public class NavigationItemMenuViewHolder extends FermatViewHolder {
    private TextView label;
    private ImageView icon;


    public NavigationItemMenuViewHolder(View itemView) {
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
