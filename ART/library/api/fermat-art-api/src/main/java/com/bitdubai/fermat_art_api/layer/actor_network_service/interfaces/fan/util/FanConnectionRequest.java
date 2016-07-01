package com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.ConnectionRequestAction;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.ProtocolState;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.RequestType;

import java.util.Arrays;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.FanConnectionRequest</code>
 * represents a crypto broker connection new, it can be a connection request or a disconnection.
 * <p>
 * Created by lnacosta (laion.cj91@gmail.com) on 17/11/2015.
 */
public final class FanConnectionRequest {

    private final UUID                    requestId           ;
    private final String                  senderPublicKey     ;
    private final PlatformComponentType senderActorType     ;
    private final String                  senderAlias         ;
    private final byte[]                  senderImage         ;
    private final String                  destinationPublicKey;
    private final PlatformComponentType   destinationActorType;
    private final RequestType requestType         ;
    private final ProtocolState protocolState       ;
    private final ConnectionRequestAction requestAction       ;
    private int sentCount                                     ;
    private final long                    sentTime            ;

    public FanConnectionRequest(final UUID requestId,
                                final String senderPublicKey,
                                final PlatformComponentType senderActorType,
                                final String senderAlias,
                                final byte[] senderImage,
                                final String destinationPublicKey,
                                PlatformComponentType destinationActorType,
                                final RequestType requestType,
                                final ProtocolState protocolState,
                                final ConnectionRequestAction requestAction,
                                final int sentCount,
                                final long sentTime) {

        this.requestId            = requestId           ;
        this.senderPublicKey      = senderPublicKey     ;
        this.senderActorType      = senderActorType     ;
        this.senderAlias          = senderAlias         ;
        this.senderImage          = senderImage         ;
        this.destinationPublicKey = destinationPublicKey;
        this.destinationActorType = destinationActorType;
        this.requestType          = requestType         ;
        this.protocolState        = protocolState       ;
        this.requestAction        = requestAction       ;
        this.sentCount            = sentCount           ;
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
    public final PlatformComponentType getSenderActorType() {
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
     * @return an element of ProtocolState indicating the state of the crypto broker connection request.
     */
    public ProtocolState getProtocolState() {
        return protocolState;
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

    public PlatformComponentType getDestinationActorType() {
        return destinationActorType;
    }

    public int getSentCount() {
        return sentCount;
    }

    public void setSentCount(int sentCount) {
        this.sentCount = sentCount;
    }

    @Override
    public String toString() {
        return "FanConnectionRequest{" +
                "requestId=" + requestId +
                ", senderPublicKey='" + senderPublicKey + '\'' +
                ", senderActorType=" + senderActorType +
                ", senderAlias='" + senderAlias + '\'' +
                ", senderImage=" + Arrays.toString(senderImage) +
                ", destinationPublicKey='" + destinationPublicKey + '\'' +
                ", destinationActorType=" + destinationActorType +
                ", requestType=" + requestType +
                ", protocolState=" + protocolState +
                ", requestAction=" + requestAction +
                ", sentTime=" + sentTime +
                '}';
    }
}
