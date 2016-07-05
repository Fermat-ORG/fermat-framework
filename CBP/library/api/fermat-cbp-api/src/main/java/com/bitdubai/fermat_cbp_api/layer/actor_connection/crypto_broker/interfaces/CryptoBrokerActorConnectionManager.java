package com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnsupportedActorTypeException;
import com.bitdubai.fermat_api.layer.actor_connection.common.interfaces.ActorConnectionManager;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_common_classes.ActorIdentityInformation;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.common.CBPActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerLinkedActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerActorConnection;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoCustomerActorConnectionManager</code>
 * provide the methods to manage actor connections for the crypto broker actors.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/11/2015.
 */
public interface CryptoBrokerActorConnectionManager extends CBPActorConnectionManager<CryptoBrokerLinkedActorIdentity, CryptoBrokerActorConnection, CryptoBrokerActorConnectionSearch> {



}
