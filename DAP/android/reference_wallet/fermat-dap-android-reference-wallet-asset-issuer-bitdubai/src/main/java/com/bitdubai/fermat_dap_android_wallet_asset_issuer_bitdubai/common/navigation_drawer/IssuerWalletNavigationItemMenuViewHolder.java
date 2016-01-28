package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.common.navigation_drawer;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

/**
 * Created by frank on 12/9/15.
 */
public class IssuerWalletNavigationItemMenuViewHolder extends FermatViewHolder {

    private TextView label;
    private ImageView icon;
    private LinearLayout row_container;
    private View navigation_row_divider;

    protected IssuerWalletNavigationItemMenuViewHolder(View itemView) {
        super(itemView);

        label = (TextView) itemView.findViewById(R.id.textView_label);
        icon = (ImageView) itemView.findViewById(R.id.imageView_icon);
//        row_container = (LinearLayout) itemView.findViewById(R.id.row_container);
//        navigation_row_divider = (View) itemView.findViewById(R.id.navigation_row_divider);
    }

    public TextView getLabel() {
        return label;
    }

    public ImageView getIcon() {
        return icon;
    }

    public LinearLayout getRow_container() {
        return row_container;
    }

    public View getNavigation_row_divider() {
        return navigation_row_divider;
    }
}
