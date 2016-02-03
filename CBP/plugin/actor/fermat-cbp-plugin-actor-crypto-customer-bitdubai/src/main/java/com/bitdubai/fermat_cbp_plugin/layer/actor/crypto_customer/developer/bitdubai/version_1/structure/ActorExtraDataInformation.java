package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.ActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.QuotesExtraData;

import java.util.Collection;
import java.util.Map;

/**
 * Created by angel on 13/1/16.
 */

public class ActorExtraDataInformation implements ActorExtraData {

    private ActorIdentity customer;
    private Collection<QuotesExtraData> quotes;
    private Map<Currency, Collection<Platforms>> currencies;

    public ActorExtraDataInformation(ActorIdentity broker, Collection<QuotesExtraData> quotes, Map<Currency, Collection<Platforms>> currencies){
        this.customer = broker;
        this.quotes = quotes;
        this.currencies = currencies;
    }

    @Override
    public ActorIdentity getBrokerIdentity() {
        return this.customer;
    }

    @Override
    public Collection<QuotesExtraData> getQuotes() {
        return this.quotes;
    }

    @Override
    public Map<Currency, Collection<Platforms>> getCurrencies() {
        return this.currencies;
    }
}
