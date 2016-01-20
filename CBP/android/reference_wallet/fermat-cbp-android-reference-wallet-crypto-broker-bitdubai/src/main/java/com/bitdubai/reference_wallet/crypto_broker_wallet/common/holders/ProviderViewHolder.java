package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

/**
 * Created by nelson on 31/12/15.
 */
public class ProviderViewHolder extends SingleDeletableItemViewHolder<CurrencyExchangeRateProviderManager> {

    private FermatTextView title;


    public ProviderViewHolder(View itemView) {
        super(itemView);

        title = (FermatTextView) itemView.findViewById(R.id.cbw_title);
        itemView.findViewById(R.id.cbw_sub_title).setVisibility(View.GONE);
    }

    @Override
    public void bind(CurrencyExchangeRateProviderManager data) {
        try {
            title.setText(data.getProviderName());
        } catch (CantGetProviderInfoException e) {
            //Nothing
        }
    }

    @Override
    public int getCloseButtonResource() {
        return R.id.cbw_close_img_button;
    }
}
