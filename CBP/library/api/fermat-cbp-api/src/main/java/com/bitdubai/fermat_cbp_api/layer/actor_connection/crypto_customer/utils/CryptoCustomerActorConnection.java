package com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.utils;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.common.CBPActorConnection;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoCustomerActorConnection</code>
 * represents an actor connection for the crypto broker actor.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/11/2015.
 */
public final class CryptoCustomerActorConnection
        extends CBPActorConnection<CryptoCustomerLinkedActorIdentity> {

    /**
     * Default constructor with parameters
     *
     * @param connectionId
     * @param linkedIdentity
     * @param publicKey
     * @param alias
     * @param image
     * @param connectionState
     * @param creationTime
     * @param updateTime
     * @param location
     */
    public CryptoCustomerActorConnection(
            UUID connectionId,
            CryptoCustomerLinkedActorIdentity linkedIdentity,
            String publicKey,
            String alias,
            byte[] image,
            ConnectionState connectionState,
            long creationTime,
            long updateTime,
            Location location) {
        super(
                connectionId,
                linkedIdentity,
                publicKey,
                alias,
                image,
                connectionState,
                creationTime,
                updateTime,
                location);
    }

    /**
     * This class only extends CBPActorConnection, it can be changed in future versions
     */

}
