package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.ActorCallRequest</code>
 * represent the message to request a network call between actors<p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class MessageTransmitRespond extends MsgRespond {

    /**
     * Represent the id of the message
     */
    private UUID messageId;

    /**
     * Constructor
     *
     * @param status
     * @param details
     * @param messageId
     */
    public MessageTransmitRespond(final STATUS status, final String details, UUID messageId) {

        super(status, details);
        this.messageId = messageId;
    }

    /**
     * Get the message id
     * @return uuid
     */
    public UUID getMessageId() {
        return messageId;
    }

    /**
     * Generate the json representation
     * @return String
     */
    @Override
    public String toJson() {
        return GsonProvider.getGson().toJson(this, getClass());
    }

    /**
     * Get the object
     *
     * @param content
     * @return PackageContent
     */
    public static MessageTransmitRespond parseContent(String content) {
        return GsonProvider.getGson().fromJson(content, MessageTransmitRespond.class);
    }
}
