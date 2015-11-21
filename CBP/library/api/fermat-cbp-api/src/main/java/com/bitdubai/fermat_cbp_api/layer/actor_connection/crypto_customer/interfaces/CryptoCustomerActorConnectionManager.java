package com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.interfaces.ActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.utils.CryptoCustomerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.utils.CryptoCustomerActorIdentity;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoCustomerActorConnectionManager</code>
 * provide the methods to manage actor connections for the crypto broker actors.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/11/2015.
 */
public interface CryptoCustomerActorConnectionManager extends ActorConnectionManager<CryptoCustomerActorIdentity, CryptoCustomerActorConnection, CryptoCustomerActorConnectionSearch> {

}
