package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders;

import android.content.res.Resources;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.MerchandiseExchangeRate;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * View Holder for the {@link com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.BrokerExchangeRatesAdapter}
 *
 * @author Nelson Ramirez
 * @version 1.0
 * @since 24/11/2015
 */
public class BrokerExchangeRatesViewHolder extends FermatViewHolder {
    private static final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
    private final Resources res;

    public FermatTextView exchangeRateItem;

    public BrokerExchangeRatesViewHolder(View itemView) {
        super(itemView);
        res = itemView.getResources();

        exchangeRateItem = (FermatTextView) itemView.findViewById(R.id.ccw_broker_exchange_rate_item);
    }

    public void bind(MerchandiseExchangeRate data) {
        String exchangeRate = decimalFormat.format(data.getExchangeRate());
        String merchandiseCurrency = data.getMerchandiseCurrency().getCode();
        String paymentCurrency = data.getPaymentCurrency().getCode();

        String text = res.getString(R.string.ccw_broker_exchange_rate_for_selling_item, merchandiseCurrency, paymentCurrency, exchangeRate);
        exchangeRateItem.setText(text);
    }
}
