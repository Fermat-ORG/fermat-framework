package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import java.util.Arrays;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerConnectionInformation</code>
 * represents a crypto broker and exposes all the functionality of it.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/11/2015.
 */
public final class CryptoBrokerConnectionInformation {

    private final String senderPublicKey     ;
    private final Actors senderActorType     ;
    private final String senderAlias         ;
    private final byte[] senderImage         ;
    private final String destinationPublicKey;
    private final long   sendingTime         ;

    public CryptoBrokerConnectionInformation(final String senderPublicKey     ,
                                             final Actors senderActorType     ,
                                             final String senderAlias         ,
                                             final byte[] senderImage         ,
                                             final String destinationPublicKey,
                                             final long   sendingTime         ) {

        this.senderPublicKey      = senderPublicKey     ;
        this.senderActorType      = senderActorType     ;
        this.senderAlias          = senderAlias         ;
        this.senderImage          = senderImage         ;
        this.destinationPublicKey = destinationPublicKey;
        this.sendingTime          = sendingTime         ;
    }

    /**
     * @return a string representing the public key.
     */
    public final String getSenderPublicKey() {
        return senderPublicKey;
    }

    /**
     * @return an element of actors enum representing the actor type of the sender.
     */
    public final Actors getSenderActorType() {
        return senderActorType;
    }

    /**
     * @return a string representing the alias of the crypto broker.
     */
    public final String getSenderAlias() {
        return senderAlias;
    }

    /**
     * @return an array of bytes with the image exposed by the Crypto Broker.
     */
    public final byte[] getSenderImage() {
        return senderImage;
    }

    /**
     * @return a string representing the destination public key.
     */
    public final String getDestinationPublicKey() {
        return destinationPublicKey;
    }

    /**
     * @return the time when the action was performed.
     */
    public final long getSendingTime() {
        return sendingTime;
    }

    @Override
    public String toString() {
        return "CryptoBrokerConnectionInformation{" +
                "senderPublicKey='" + senderPublicKey + '\'' +
                ", senderActorType=" + senderActorType +
                ", senderAlias='" + senderAlias + '\'' +
                ", senderImage=" + Arrays.toString(senderImage) +
                ", destinationPublicKey='" + destinationPublicKey + '\'' +
                ", sendingTime=" + sendingTime +
                '}';
    }

}
