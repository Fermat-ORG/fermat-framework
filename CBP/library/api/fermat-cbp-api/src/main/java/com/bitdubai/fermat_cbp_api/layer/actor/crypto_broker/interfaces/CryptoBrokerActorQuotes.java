package com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;

/**
 * Created by angel on 15/01/16.
 */

public interface CryptoBrokerActorQuotes {

    /**
     * @return
     */
    Currency getMerchandise();

    /**
     * @return
     */
    Currency getPaymentCurrency();

    /**
     * @return
     */
    Float getPrice();
}