package com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.interfaces.ActorConnection;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerActorConnection</code>
 * represents an actor connection for the crypto broker actor.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/11/2015.
 */
public final class CryptoBrokerActorConnection extends ActorConnection<CryptoBrokerActorIdentity> {

    public CryptoBrokerActorConnection(final UUID                      connectionId   ,
                                       final CryptoBrokerActorIdentity linkedIdentity ,
                                       final String                    publicKey      ,
                                       final Actors                    actorType      ,
                                       final String                    alias          ,
                                       final byte[]                    image          ,
                                       final ConnectionState           connectionState,
                                       final long                      creationTime   ,
                                       final long                      updateTime     ) {

        super(
                connectionId   ,
                linkedIdentity ,
                publicKey      ,
                actorType      ,
                alias          ,
                image          ,
                connectionState,
                creationTime   ,
                updateTime
        );
    }

}
