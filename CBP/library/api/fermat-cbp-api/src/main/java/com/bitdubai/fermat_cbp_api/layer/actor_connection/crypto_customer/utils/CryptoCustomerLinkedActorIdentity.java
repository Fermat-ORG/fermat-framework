package com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.utils;

import com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes.LinkedActorIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import java.io.Serializable;


/**
 * The class <code>com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoCustomerLinkedActorIdentity</code>
 * represents a crypto customer actor identity.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/11/2015.
 */
public final class CryptoCustomerLinkedActorIdentity extends LinkedActorIdentity implements Serializable {

    public CryptoCustomerLinkedActorIdentity(final String publicKey, final Actors actorType) {
        super(publicKey, actorType);
    }

}
