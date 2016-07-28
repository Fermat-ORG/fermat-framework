package com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.database_abstract_classes.ActorConnectionDao;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes.ActorConnectionSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerLinkedActorIdentity;

/**
 * The class <code>com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoCustomerActorConnectionSearch</code>
 * represents a crypto broker actor connection search.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/11/2015.
 */
public abstract class CryptoBrokerActorConnectionSearch extends ActorConnectionSearch<CryptoBrokerLinkedActorIdentity, CryptoBrokerActorConnection> {

    public CryptoBrokerActorConnectionSearch(final CryptoBrokerLinkedActorIdentity actorIdentity,
                                             final ActorConnectionDao<CryptoBrokerLinkedActorIdentity, CryptoBrokerActorConnection> dao) {

        super(actorIdentity, dao);
    }

}
