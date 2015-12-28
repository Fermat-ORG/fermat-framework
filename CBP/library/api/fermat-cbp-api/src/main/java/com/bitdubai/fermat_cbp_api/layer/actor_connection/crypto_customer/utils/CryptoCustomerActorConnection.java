package com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.utils;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes.ActorConnection;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoCustomerActorConnection</code>
 * represents an actor connection for the crypto broker actor.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/11/2015.
 */
public final class CryptoCustomerActorConnection extends ActorConnection<CryptoCustomerLinkedActorIdentity> {

    public CryptoCustomerActorConnection(final UUID                              connectionId   ,
                                         final CryptoCustomerLinkedActorIdentity linkedIdentity ,
                                         final String                            publicKey      ,
                                         final String                            alias          ,
                                         final byte[]                            image          ,
                                         final ConnectionState                   connectionState,
                                         final long                              creationTime   ,
                                         final long                              updateTime     ) {

        super(
                connectionId   ,
                linkedIdentity ,
                publicKey      ,
                alias          ,
                image          ,
                connectionState,
                creationTime   ,
                updateTime
        );
    }

}
