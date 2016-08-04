package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders;

import android.content.res.Resources;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.MerchandiseExchangeRate;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

import java.math.BigDecimal;
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

    private NumberFormat numberFormat = DecimalFormat.getInstance();

    private final Resources res;

    public FermatTextView exchangeRateItem;

    public BrokerExchangeRatesViewHolder(View itemView) {
        super(itemView, 0);
        res = itemView.getResources();


        exchangeRateItem = (FermatTextView) itemView.findViewById(R.id.ccw_broker_exchange_rate_item);
    }

    public void bind(MerchandiseExchangeRate data) {


        String exchangeRate = fixFormat(data.getExchangeRate());
        String merchandiseCurrency = data.getMerchandiseCurrency().getCode();
        String paymentCurrency = data.getPaymentCurrency().getCode();

        String text = res.getString(R.string.ccw_broker_exchange_rate_for_selling_item, merchandiseCurrency, exchangeRate, paymentCurrency);
        exchangeRateItem.setText(text);
    }


    private String fixFormat(Double value) {


            if (compareLessThan1(value)) {
                numberFormat.setMaximumFractionDigits(8);
            } else {
                numberFormat.setMaximumFractionDigits(2);
            }
            return numberFormat.format(new BigDecimal(value));

    }

    private Boolean compareLessThan1(Double value) {
        return BigDecimal.valueOf(value).compareTo(BigDecimal.ONE) == -1;
    }

}
