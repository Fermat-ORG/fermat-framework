package com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;

import java.util.Arrays;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.ArtistConnectionInformation</code>
 * represents a crypto broker and exposes all the functionality of it.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/11/2015.
 */
public final class ArtistConnectionInformation {

    private final UUID   connectionId        ;
    private final String senderPublicKey     ;
    private final PlatformComponentType senderActorType     ;
    private final String senderAlias         ;
    private final byte[] senderImage         ;
    private final String destinationPublicKey;

    private final PlatformComponentType destinationActorType;
    private final long   sendingTime         ;

    public ArtistConnectionInformation(final UUID connectionId,
                                       final String senderPublicKey,
                                       final PlatformComponentType senderActorType,
                                       final String senderAlias,
                                       final byte[] senderImage,
                                       final String destinationPublicKey,
                                       PlatformComponentType destinationActorType,
                                       final long sendingTime) {

        this.connectionId         = connectionId        ;
        this.senderPublicKey      = senderPublicKey     ;
        this.senderActorType      = senderActorType     ;
        this.senderAlias          = senderAlias         ;
        this.senderImage          = senderImage         ;
        this.destinationPublicKey = destinationPublicKey;
        this.destinationActorType = destinationActorType;
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
     * @return the time when the action was performed.
     */
    public final long getSendingTime() {
        return sendingTime;
    }

    public UUID getConnectionId() {
        return connectionId;
    }

    public PlatformComponentType getDestinationActorType() {
        return destinationActorType;
    }


    @Override
    public String toString() {
        return "ArtistConnectionInformation{" +
                "connectionId=" + connectionId +
                ", senderPublicKey='" + senderPublicKey + '\'' +
                ", senderActorType=" + senderActorType +
                ", senderAlias='" + senderAlias + '\'' +
                ", senderImage=" + Arrays.toString(senderImage) +
                ", destinationPublicKey='" + destinationPublicKey + '\'' +
                ", sendingTime=" + sendingTime +
                '}';
    }
}
