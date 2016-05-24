package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders;

import android.content.res.Resources;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.MerchandiseExchangeRate;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.ibm.icu.text.DecimalFormatSymbols;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * View Holder for the {@link com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.BrokerExchangeRatesAdapter}
 *
 * @author Nelson Ramirez
 * @version 1.0
 * @since 24/11/2015
 */
public class BrokerExchangeRatesViewHolder extends FermatViewHolder {

    private NumberFormat formatter;

    private final Resources res;

    public FermatTextView exchangeRateItem;

    public BrokerExchangeRatesViewHolder(View itemView) {
        super(itemView);
        res = itemView.getResources();

        formatter = DecimalFormat.getInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setRoundingMode(RoundingMode.DOWN);

        exchangeRateItem = (FermatTextView) itemView.findViewById(R.id.ccw_broker_exchange_rate_item);
    }

    public void bind(MerchandiseExchangeRate data) {
        String exchangeRate = formatter.format(data.getExchangeRate());
        String merchandiseCurrency = data.getMerchandiseCurrency().getCode();
        String paymentCurrency = data.getPaymentCurrency().getCode();


        //Verificar si el precio del quote es extremadamente pequeno e invertir el quote
        //Si el precio es 0, no hacer nada pues dara infinito...
        //TODO: Revisar este hack (abicelis)
        if(data.getExchangeRate() < 0.5 && data.getExchangeRate() > 0)
        {
            exchangeRate = formatter.format(1/data.getExchangeRate());
            merchandiseCurrency = data.getPaymentCurrency().getCode();
            paymentCurrency = data.getMerchandiseCurrency().getCode();
        }

        String text = res.getString(R.string.ccw_broker_exchange_rate_for_selling_item, merchandiseCurrency, exchangeRate, paymentCurrency);
        exchangeRateItem.setText(text);
    }
}
