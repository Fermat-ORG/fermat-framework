package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.messages;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.ConnectionRequestAction;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.messages.RequestMessage</code>
 * contains the structure of a Request message for this plugin. Connection or Disconnection.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/11/2015.
 */
public class RequestMessage extends NetworkServiceMessage {

    private final UUID                    requestId           ;
    private final String                  senderPublicKey     ;
    private final Actors                  senderActorType     ;
    private final String                  senderAlias         ;
    private final String                  senderImage         ;
    private final String                  destinationPublicKey;
    private final ConnectionRequestAction requestAction       ;
    private final long                    sentTime            ;

    public RequestMessage(final UUID                    requestId           ,
                          final String                  senderPublicKey     ,
                          final Actors                  senderActorType     ,
                          final String                  senderAlias         ,
                          final String                  senderImage         ,
                          final String                  destinationPublicKey,
                          final ConnectionRequestAction requestAction       ,
                          final long                    sentTime            ) {

        this.requestId            = requestId           ;
        this.senderPublicKey      = senderPublicKey     ;
        this.senderActorType      = senderActorType     ;
        this.senderAlias          = senderAlias         ;
        this.senderImage          = senderImage         ;
        this.destinationPublicKey = destinationPublicKey;
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
    public final String getSenderImage() {
        return senderImage;
    }

    /**
     * @return a string representing the destination public key.
     */
    public final String getDestinationPublicKey() {
        return destinationPublicKey;
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
        return "CryptoBrokerConnectionRequest{" +
                "requestId=" + requestId +
                ", senderPublicKey='" + senderPublicKey + '\'' +
                ", senderActorType=" + senderActorType +
                ", senderAlias='" + senderAlias + '\'' +
                ", senderImage=" + senderImage + '\'' +
                ", destinationPublicKey='" + destinationPublicKey + '\'' +
                ", requestAction=" + requestAction +
                ", sentTime=" + sentTime +
                '}';
    }
}
