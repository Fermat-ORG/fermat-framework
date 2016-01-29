package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.graphics.Color;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.world.interfaces.CurrencyPair;
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
        CurrencyPair currencyPair = data.getCurrencyPair();
        currencyPairButton.setText(currencyPair.getCode());

        if (selected) {
            currencyPairButton.setBackgroundColor(Color.WHITE);
            currencyPairButton.setTextColor(Color.BLACK);
        }
    }
}
