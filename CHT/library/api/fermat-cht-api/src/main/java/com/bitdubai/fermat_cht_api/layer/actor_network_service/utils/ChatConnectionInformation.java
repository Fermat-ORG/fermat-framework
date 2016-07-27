package com.bitdubai.fermat_cht_api.layer.actor_network_service.utils;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 05/04/16.
 */
public class ChatConnectionInformation {

    private UUID connectionId;
    private String senderPublicKey;
    private Actors senderActorType;
    private String senderAlias;
    private byte[] senderImage;
    private String destinationPublicKey;
    private long sendingTime;

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

    public ChatConnectionInformation(String stringValue, String stringValue1, byte[] image, long longValue, ConnectionState byCode) {

    }

    public ChatConnectionInformation(String A, String stringValue, byte[] image, long longValue, ConnectionState byCode, String stringValue1) {


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
        return new StringBuilder()
                .append("ChatConnectionInformation{")
                .append("connectionId=").append(connectionId)
                .append(", senderPublicKey='").append(senderPublicKey)
                .append('\'')
                .append(", senderActorType=").append(senderActorType)
                .append(", senderAlias='").append(senderAlias)
                .append('\'')
                .append(", senderImage=").append(Arrays.toString(senderImage))
                .append(", destinationPublicKey='").append(destinationPublicKey)
                .append('\'')
                .append(", sendingTime=").append(sendingTime)
                .append('}').toString();
    }
}
