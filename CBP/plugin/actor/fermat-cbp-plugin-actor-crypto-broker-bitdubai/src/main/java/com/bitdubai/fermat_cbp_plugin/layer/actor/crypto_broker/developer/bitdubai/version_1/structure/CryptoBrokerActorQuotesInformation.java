package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.CryptoBrokerActorQuotes;

/**
 * Created by angel on 4/02/16.
 */
public class CryptoBrokerActorQuotesInformation implements CryptoBrokerActorQuotes {

    private Currency merchandise;
    private Currency paymentCurrency;
    private Float price;

    public CryptoBrokerActorQuotesInformation(Currency merchandise, Currency paymentCurrency, Float price) {
        this.merchandise = merchandise;
        this.paymentCurrency = paymentCurrency;
        this.price = price;
    }


    @Override
    public Currency getMerchandise() {
        return this.merchandise;
    }

    @Override
    public Currency getPaymentCurrency() {
        return this.paymentCurrency;
    }

    @Override
    public Float getPrice() {
        return this.price;
    }
}
