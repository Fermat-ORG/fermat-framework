package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.graphics.Color;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

/**
 * Created by nelson on 29/01/16.
 */
public class EarningsCurrencyPairsViewHolder extends FermatViewHolder {
    private FermatButton currencyPairButton;

    public EarningsCurrencyPairsViewHolder(View itemView) {
        super(itemView);

        currencyPairButton = (FermatButton) itemView.findViewById(R.id.cbw_earning_currency_pair_button);
    }

    public void bind(EarningsPair data, boolean selected) {
        Currency linkedCurrency = data.getLinkedCurrency();
        Currency earningCurrency = data.getEarningCurrency();
        currencyPairButton.setText(new StringBuilder().append(linkedCurrency.getCode()).append(" / ").append(earningCurrency.getCode()).toString());

        // TODO commented due to change of the behavior of the earning pair.

        if (selected) {
            currencyPairButton.setBackgroundColor(Color.WHITE);
            currencyPairButton.setTextColor(Color.BLACK);
        } else {
            currencyPairButton.setBackgroundColor(Color.parseColor("#51ffffff"));
            currencyPairButton.setTextColor(Color.WHITE);
        }
    }
}
