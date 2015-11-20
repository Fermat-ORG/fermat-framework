package com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.interfaces.ActorConnectionSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerActorIdentity;

/**
 * The class <code>com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoBrokerActorConnectionSearch</code>
 * represents a crypto broker actor connection search.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/11/2015.
 */
public abstract class CryptoBrokerActorConnectionSearch extends ActorConnectionSearch<CryptoBrokerActorIdentity, CryptoBrokerActorConnection> {

    public CryptoBrokerActorConnectionSearch(final CryptoBrokerActorIdentity actorIdentity) {
        super(actorIdentity);
    }

}
