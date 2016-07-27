package com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.layer.actor_connection.common.CBPActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerLinkedActorIdentity;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoCustomerActorConnectionManager</code>
 * provide the methods to manage actor connections for the crypto broker actors.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/11/2015.
 */
public interface CryptoBrokerActorConnectionManager extends CBPActorConnectionManager<CryptoBrokerLinkedActorIdentity, CryptoBrokerActorConnection, CryptoBrokerActorConnectionSearch> {

    /**
     * This interface only extends CBPActorConnectionManager, it can be change in future versions
     */

}
