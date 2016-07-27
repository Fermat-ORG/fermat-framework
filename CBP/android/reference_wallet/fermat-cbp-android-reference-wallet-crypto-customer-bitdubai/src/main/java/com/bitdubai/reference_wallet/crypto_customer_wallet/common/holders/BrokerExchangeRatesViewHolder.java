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
import java.text.ParseException;

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
        super(itemView);
        res = itemView.getResources();


        exchangeRateItem = (FermatTextView) itemView.findViewById(R.id.ccw_broker_exchange_rate_item);
    }

    public void bind(MerchandiseExchangeRate data) {


        String exchangeRate = fixFormat(String.valueOf(data.getExchangeRate()));
        String merchandiseCurrency = data.getMerchandiseCurrency().getCode();
        String paymentCurrency = data.getPaymentCurrency().getCode();


        //Verificar si el precio del quote es extremadamente pequeno e invertir el quote
        //Si el precio es 0, no hacer nada pues dara infinito...
        //TODO: Revisar este hack (abicelis)
        //change lostwood
/*        if(data.getExchangeRate() < 0.5 && data.getExchangeRate() > 0)
        {
            exchangeRate = formatter.format(1/data.getExchangeRate());
            merchandiseCurrency = data.getPaymentCurrency().getCode();
            paymentCurrency = data.getMerchandiseCurrency().getCode();
        }*/

        String text = res.getString(R.string.ccw_broker_exchange_rate_for_selling_item, merchandiseCurrency, exchangeRate, paymentCurrency);
        exchangeRateItem.setText(text);
    }


    private String fixFormat(String value) {

        try {
            if (compareLessThan1(value)) {
                numberFormat.setMaximumFractionDigits(8);
            } else {
                numberFormat.setMaximumFractionDigits(2);
            }
            return numberFormat.format(new BigDecimal(numberFormat.parse(value).toString()));
        } catch (ParseException e) {
            e.printStackTrace();
            return "0";
        }

    }

    private Boolean compareLessThan1(String value) {
        Boolean lessThan1 = true;
        try {
            if (BigDecimal.valueOf(numberFormat.parse(value).doubleValue()).
                    compareTo(BigDecimal.ONE) == -1) {
                lessThan1 = true;
            } else {
                lessThan1 = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return lessThan1;
    }

}
