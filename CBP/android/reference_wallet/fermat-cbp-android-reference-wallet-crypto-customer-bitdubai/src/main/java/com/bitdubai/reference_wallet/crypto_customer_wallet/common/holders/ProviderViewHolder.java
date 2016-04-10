package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.CurrencyPairAndProvider;

/**
 * Created by nelson on 31/12/15.
 */
public class ProviderViewHolder extends SingleDeletableItemViewHolder<CurrencyPairAndProvider> {

    private FermatTextView title;


    public ProviderViewHolder(View itemView) {
        super(itemView);

        title = (FermatTextView) itemView.findViewById(R.id.ccw_title);
    }

    @Override
    public void bind(CurrencyPairAndProvider data) {
        try {
            title.setText(data.getCurrencyFrom().getCode()+"/"+data.getCurrencyTo().getCode()+" - "+data.getProvider().getProviderName());
        } catch (CantGetProviderInfoException ignore) {
        }
    }

    @Override
    public int getCloseButtonResource() {
        return R.id.ccw_close_img_button;
    }
}
