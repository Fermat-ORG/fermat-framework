package com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils;

import com.bitdubai.fermat_api.layer.actor_connection.common.abstract_classes.ActorIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

/**
 * The class <code>com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerActorIdentity</code>
 * represents a crypto broker actor identity.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/11/2015.
 */
public final class CryptoBrokerActorIdentity extends ActorIdentity {

    public CryptoBrokerActorIdentity(final String publicKey) {
        super(publicKey, Actors.CBP_CRYPTO_BROKER);
    }

}
