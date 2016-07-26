package com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 15/01/16.
 */
public interface QuotesExtraData extends Serializable {
    UUID getQuoteId();

    Currency getMerchandise();

    Currency getPaymentCurrency();

    Float getPrice();

    List<Platforms> getPlatformsSupported();
}
