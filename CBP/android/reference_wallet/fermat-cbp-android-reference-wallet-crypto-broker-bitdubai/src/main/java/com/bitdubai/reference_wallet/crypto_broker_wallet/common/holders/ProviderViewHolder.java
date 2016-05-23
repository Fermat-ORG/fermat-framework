package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.CurrencyPairAndProvider;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;


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
        title.setText(String.format("%s / %s", data.getCurrencyFrom().getCode(), data.getCurrencyTo().getCode()));
        subTitle.setText(data.getProviderName());

    }

    @Override
    public int getCloseButtonResource() {
        return R.id.cbw_close_img_button;
    }
}
