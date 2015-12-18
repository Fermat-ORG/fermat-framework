package com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.navigation_drawer;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.R;

/**
 * Created by frank on 12/9/15.
 */
public class RedeemPointWalletNavigationItemMenuViewHolder extends FermatViewHolder {
    private TextView label;
    private ImageView icon;
    /**
     * Constructor
     *
     * @param itemView
     */
    protected RedeemPointWalletNavigationItemMenuViewHolder(View itemView) {
        super(itemView);
        label = (TextView) itemView.findViewById(R.id.menuItemText);
        icon = (ImageView) itemView.findViewById(R.id.menuItemImage);
    }

    public TextView getLabel() {
        return label;
    }

    public ImageView getIcon() {
        return icon;
    }
}
