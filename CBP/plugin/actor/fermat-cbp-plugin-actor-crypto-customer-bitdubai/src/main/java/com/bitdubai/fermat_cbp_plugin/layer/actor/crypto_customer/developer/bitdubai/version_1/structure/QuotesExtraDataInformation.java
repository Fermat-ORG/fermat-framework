package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.QuotesExtraData;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;


/**
 * Created by angel on 16/01/16.
 */
public class QuotesExtraDataInformation implements QuotesExtraData, Serializable {

    private UUID quoteId;
    private Currency mechandise;
    private Currency paymentCurrency;
    private Float price;
    private List<Platforms> platformsSupported;

    public QuotesExtraDataInformation(UUID quoteId, Currency mechandise, Currency paymentCurrency, Float price, List<Platforms> platformsSupported) {
        this.mechandise = mechandise;
        this.paymentCurrency = paymentCurrency;
        this.price = price;
        this.quoteId = quoteId;
        this.platformsSupported = platformsSupported;
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
    public Currency getPaymentCurrency() {
        return this.paymentCurrency;
    }

    @Override
    public Float getPrice() {
        return this.price;
    }

    @Override
    public List<Platforms> getPlatformsSupported() {
        return platformsSupported;
    }

}
