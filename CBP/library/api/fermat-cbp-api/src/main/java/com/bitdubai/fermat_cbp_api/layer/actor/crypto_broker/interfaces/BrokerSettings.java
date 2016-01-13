package com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.FiatIndex;

import java.util.Collection;
import java.util.Map;

/**
 * Created by angel on 13/1/16.
 */

public interface BrokerSettings {

    /**
     *
     * @return basic information broker
     */
    ActorIdentity getBrokerIdentity();

    /**
     *
     * @return all prices of goods Broker
     */
    Collection<FiatIndex> getQuotes();

    /**
     *
     * @return all currencies handled with platforms that support them
     */
    Map<Currency, Collection<Platforms>> getCurrencies();

    /**
     *
     * @return all currencies handled with platforms that support them
     */
    Collection<Platforms> getPlatformsSupport(Currency currency);

}
