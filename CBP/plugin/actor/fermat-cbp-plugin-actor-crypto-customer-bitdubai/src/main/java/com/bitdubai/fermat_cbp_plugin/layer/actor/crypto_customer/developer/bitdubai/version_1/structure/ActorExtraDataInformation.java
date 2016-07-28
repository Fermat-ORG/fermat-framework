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
public final class ActorExtraDataInformation implements ActorExtraData {

    private final String customer;
    private final ActorIdentity broker;
    private final Collection<QuotesExtraData> quotes;
    private final Map<Currency, Collection<Platforms>> currencies;

    public ActorExtraDataInformation(final String customer,
                                     final ActorIdentity broker,
                                     final Collection<QuotesExtraData> quotes,
                                     final Map<Currency, Collection<Platforms>> currencies) {

        this.customer = customer;
        this.broker = broker;
        this.quotes = quotes;
        this.currencies = currencies;
    }

    @Override
    public String getCustomerPublicKey() {
        return this.customer;
    }

    @Override
    public ActorIdentity getBrokerIdentity() {
        return this.broker;
    }

    @Override
    public Collection<QuotesExtraData> getQuotes() {
        return this.quotes;
    }

    @Override
    public Map<Currency, Collection<Platforms>> getCurrencies() {
        return this.currencies;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("ActorExtraDataInformation{")
                .append("customer='").append(customer)
                .append('\'')
                .append(", broker=").append(broker)
                .append(", quotes=").append(quotes)
                .append(", currencies=").append(currencies)
                .append('}').toString();
    }

}
