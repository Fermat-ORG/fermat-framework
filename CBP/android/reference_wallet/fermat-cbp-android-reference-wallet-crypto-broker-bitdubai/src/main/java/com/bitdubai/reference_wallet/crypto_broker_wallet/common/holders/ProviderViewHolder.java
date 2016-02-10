package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.CurrencyPairAndProvider;

/**
 * Created by nelson on 31/12/15.
 */
public class ProviderViewHolder extends SingleDeletableItemViewHolder<CurrencyPairAndProvider> {

    private final FermatTextView subTitle;
    private FermatTextView title;


    public ProviderViewHolder(View itemView) {
        super(itemView);

        title = (FermatTextView) itemView.findViewById(R.id.cbw_title);
        subTitle = (FermatTextView) itemView.findViewById(R.id.cbw_sub_title);
    }

    @Override
    public void bind(CurrencyPairAndProvider data) {
        try {
            title.setText(String.format("%s / %s", data.getCurrencyFrom().getCode(), data.getCurrencyTo().getCode()));
            subTitle.setText(data.getProvider().getProviderName());

        } catch (CantGetProviderInfoException ignored) {
        }
    }

    @Override
    public int getCloseButtonResource() {
        return R.id.cbw_close_img_button;
    }
}
