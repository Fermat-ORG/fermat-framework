package com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;

import java.util.UUID;

/**
 * Created by angel on 15/01/16.
 */
public interface QuotesExtraData {
    UUID getQuoteId();
    Currency getMerchandise();
    Currency getPaymentCurrency();
    Float getPrice();
}
