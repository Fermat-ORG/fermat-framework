package com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.interfaces.CryptoCustomerActorConnectionSearch;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.utils.CryptoCustomerLinkedActorIdentity;
import com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorConnectionDao;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.structure.ActorConnectionSearch</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 03/02/2016.
 */
public class ActorConnectionSearch extends CryptoCustomerActorConnectionSearch {

    public ActorConnectionSearch(final CryptoCustomerLinkedActorIdentity actorIdentity,
                                 final CryptoCustomerActorConnectionDao dao) {

        super(actorIdentity, dao);
    }

}
