package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders;

import android.view.View;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.CurrencyPairAndProvider;

/**
 * Created by nelson on 31/12/15.
 */
public class ProviderViewHolder extends SingleDeletableItemViewHolder<CurrencyPairAndProvider> {

    private FermatTextView title;
    private TextView subtitle;


    public ProviderViewHolder(View itemView) {
        super(itemView);

        title = (FermatTextView) itemView.findViewById(R.id.ccw_title);
        subtitle = (TextView) itemView.findViewById(R.id.ccw_sub_title);
    }

    @Override
    public void bind(CurrencyPairAndProvider data) {
//        title.setText(data.getCurrencyFrom().getCode()+"/"+data.getCurrencyTo().getCode()+" - "+data.getProviderName());
        title.setText(new StringBuilder().append(data.getCurrencyFrom().getCode()).append("/").append(data.getCurrencyTo().getCode()).toString());
        subtitle.setText(data.getProviderName());
    }

    @Override
    public int getCloseButtonResource() {
        return R.id.ccw_close_img_button;
    }
}
