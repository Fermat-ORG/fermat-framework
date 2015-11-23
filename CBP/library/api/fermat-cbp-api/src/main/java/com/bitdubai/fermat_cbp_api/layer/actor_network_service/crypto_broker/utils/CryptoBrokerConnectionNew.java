package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.ConnectionRequestAction;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.RequestType;

import java.util.Arrays;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerConnectionNew</code>
 * represents a crypto broker connection new, it can be a connection request or a disconnection.
 * <p>
 * Created by lnacosta (laion.cj91@gmail.com) on 17/11/2015.
 */
public final class CryptoBrokerConnectionNew {

    private final UUID                    requestId           ;
    private final String                  senderPublicKey     ;
    private final Actors                  senderActorType     ;
    private final String                  senderAlias         ;
    private final byte[]                  senderImage         ;
    private final String                  destinationPublicKey;
    private final RequestType             requestType         ;
    private final ConnectionRequestAction requestAction       ;
    private final long                    sentTime            ;

    public CryptoBrokerConnectionNew(UUID                    requestId           ,
                                     String                  senderPublicKey     ,
                                     Actors                  senderActorType     ,
                                     String                  senderAlias         ,
                                     byte[]                  senderImage         ,
                                     String                  destinationPublicKey,
                                     RequestType             requestType         ,
                                     ConnectionRequestAction requestAction       ,
                                     long                    sentTime            ) {

        this.requestId            = requestId           ;
        this.senderPublicKey      = senderPublicKey     ;
        this.senderActorType      = senderActorType     ;
        this.senderAlias          = senderAlias         ;
        this.senderImage          = senderImage         ;
        this.destinationPublicKey = destinationPublicKey;
        this.requestType          = requestType         ;
        this.requestAction        = requestAction       ;
        this.sentTime             = sentTime            ;
    }

    /**
     * @return an uuid representing the request id.
     */
    public final UUID getRequestId() {
        return requestId;
    }

    /**
     * @return a string representing the sender public key.
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
     * @return an element of RequestType indicating the type of the crypto broker connection request.
     */
    public final RequestType getRequestType() {
        return requestType;
    }

    /**
     * @return an element of ConnectionRequestAction enum indicating the action that must to be done.
     */
    public final ConnectionRequestAction getRequestAction() {
        return requestAction;
    }

    /**
     * @return the time when the action was performed.
     */
    public final long getSentTime() {
        return sentTime;
    }

    @Override
    public String toString() {
        return "CryptoBrokerConnectionNew{" +
                "requestId=" + requestId +
                ", senderPublicKey='" + senderPublicKey + '\'' +
                ", senderActorType=" + senderActorType +
                ", senderAlias='" + senderAlias + '\'' +
                ", senderImage=" + Arrays.toString(senderImage) +
                ", destinationPublicKey='" + destinationPublicKey + '\'' +
                ", requestType=" + requestType +
                ", requestAction=" + requestAction +
                ", sentTime=" + sentTime +
                '}';
    }

}
