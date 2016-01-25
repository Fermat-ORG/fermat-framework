package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.QuotesExtraData;

import java.util.UUID;

/**
 * Created by angel on 16/01/16.
 */
public class QuotesExtraDataInformation implements QuotesExtraData {

    private UUID quoteId;
    private Currency mechandise;
    private Currency paymentCurrency;
    private Float price;

    public QuotesExtraDataInformation(UUID quoteId, Currency mechandise, Currency paymentCurrency, Float price){
        this.mechandise = mechandise;
        this.paymentCurrency = paymentCurrency;
        this.price = price;
        this.quoteId = quoteId;
    }

    @Override
    public UUID getQuoteId() {
        return this.quoteId;
    }

    @Override
    public Currency getMerchandise() {
        return this.mechandise;
    }

    @Override
    public Currency getPaymentMethod() {
        return this.paymentCurrency;
    }

    @Override
    public Float getPrice() {
        return this.price;
    }
}
