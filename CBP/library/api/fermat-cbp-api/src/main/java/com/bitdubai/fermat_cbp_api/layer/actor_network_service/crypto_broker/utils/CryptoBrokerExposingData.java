package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerExposingData</code>
 * represents a crypto broker and exposes all the functionality of it.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/11/2015.
 */
public final class CryptoBrokerExposingData {

    private final String publicKey;
    private final Actors actorType;
    private final String alias    ;
    private final byte[] image    ;

    public CryptoBrokerExposingData(final String publicKey,
                                    final Actors actorType,
                                    final String alias    ,
                                    final byte[] image    ) {

        this.publicKey = publicKey;
        this.actorType = actorType;
        this.alias     = alias    ;
        this.image     = image    ;
    }

    /**
     * @return a string representing the public key.
     */
    public final String getPublicKey() {
        return publicKey;
    }

    /**
     * @return an element of actors enum representing the actor type of the sender.
     */
    public final Actors getActorType() {
        return actorType;
    }

    /**
     * @return a string representing the alias of the crypto broker.
     */
    public final String getAlias() {
        return alias;
    }

    /**
     * @return an array of bytes with the image exposed by the Crypto Broker.
     */
    public final byte[] getImage() {
        return image;
    }

}
