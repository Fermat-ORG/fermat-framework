package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages;

import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.enums.MessageTypes;
import com.google.gson.Gson;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages.NetworkServiceMessage</code>
 * indicates all the basic functionality of a network service message,
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/10/2015.
 */
public class NetworkServiceMessage {

    private MessageTypes messageType;
    private UUID requestId;
    private String identitySender;
    private String actorDestination;

    public NetworkServiceMessage(UUID requestId,final MessageTypes messageType,String identitySender,String actorDestination) {
        this.messageType = messageType;
        this.requestId = requestId;
        this.identitySender = identitySender;
        this.actorDestination = actorDestination;
    }

    public String toJson() {

        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public MessageTypes getMessageType() {
        return messageType;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public String getIdentitySender() {
        return identitySender;
    }

    public String getActorDestination() {
        return actorDestination;
    }
}
