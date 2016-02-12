package com.bitdubai.reference_wallet.crypto_broker_wallet.common.navigationDrawer;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

/**
 * Created by nelson on 19/12/15.
 */
public class NavItemFooterViewHolder extends FermatViewHolder {
    private FermatTextView navFooterItemCurrency;
    private FermatTextView navFooterItemCurrencyAmount;

    protected NavItemFooterViewHolder(View itemView) {
        super(itemView);
        navFooterItemCurrency = (FermatTextView) itemView.findViewById(R.id.cbw_nav_footer_item_currency);
        navFooterItemCurrencyAmount = (FermatTextView) itemView.findViewById(R.id.cbw_nav_footer_item_currency_amount);
    }

    public FermatTextView getNavFooterItemCurrency() {
        return navFooterItemCurrency;
    }

    public FermatTextView getNavFooterItemCurrencyAmount() {
        return navFooterItemCurrencyAmount;
    }
}
