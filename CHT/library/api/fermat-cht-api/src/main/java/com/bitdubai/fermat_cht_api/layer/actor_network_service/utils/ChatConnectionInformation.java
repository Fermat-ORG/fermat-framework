package com.bitdubai.fermat_cht_api.layer.actor_network_service.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 05/04/16.
 */
public class ChatConnectionInformation {

    private final UUID connectionId;
    private final String senderPublicKey;
    private final Actors senderActorType;
    private final String senderAlias;
    private final byte[] senderImage;
    private final String destinationPublicKey;
    private final long sendingTime;

    public ChatConnectionInformation(final UUID connectionId,
                                     final String senderPublicKey,
                                     final Actors senderActorType,
                                     final String senderAlias,
                                     final byte[] senderImage,
                                     final String destinationPublicKey,
                                     final long sendingTime) {

        this.senderActorType = senderActorType;
        this.connectionId = connectionId;
        this.senderPublicKey = senderPublicKey;
        this.senderAlias = senderAlias;
        this.senderImage = senderImage;
        this.destinationPublicKey = destinationPublicKey;
        this.sendingTime = sendingTime;
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

    /**
     * @return a string with the sender alias
     */
    public String getSenderAlias() {
        return senderAlias;
    }

    public UUID getConnectionId() {
        return connectionId;
    }

    @Override
    public String toString() {
        return "ChatConnectionInformation{" +
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
