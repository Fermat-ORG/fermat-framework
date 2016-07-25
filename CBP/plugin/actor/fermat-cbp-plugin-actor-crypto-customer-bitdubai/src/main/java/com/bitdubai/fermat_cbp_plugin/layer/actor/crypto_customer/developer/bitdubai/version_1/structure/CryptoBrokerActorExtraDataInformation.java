package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.CryptoBrokerActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.CryptoBrokerActorQuotes;

import java.util.Collection;

/**
 * Created by angel on 4/02/16.
 */
public class CryptoBrokerActorExtraDataInformation implements CryptoBrokerActorExtraData {

    private Collection<CryptoBrokerActorQuotes> quotes;

    public CryptoBrokerActorExtraDataInformation(Collection<CryptoBrokerActorQuotes> quotes) {
        this.quotes = quotes;
    }

    @Override
    public Collection<CryptoBrokerActorQuotes> quotes() {
        return this.quotes;
    }
}
