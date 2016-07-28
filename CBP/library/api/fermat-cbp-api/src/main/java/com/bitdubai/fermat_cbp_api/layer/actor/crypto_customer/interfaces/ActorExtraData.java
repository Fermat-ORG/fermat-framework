package com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;

import java.util.Collection;
import java.util.Map;

/**
 * Created by angel on 13/1/16.
 */

public interface ActorExtraData {

    /**
     * @return basic information Customer
     */
    String getCustomerPublicKey();

    /**
     * @return basic information Customer
     */
    ActorIdentity getBrokerIdentity();

    /**
     * @return all prices of goods Customer
     */
    Collection<QuotesExtraData> getQuotes();

    /**
     * @return all currencies handled with platforms that support them
     */
    Map<Currency, Collection<Platforms>> getCurrencies();

}
