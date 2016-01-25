package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages;

import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.enums.MessageTypes;

import java.util.UUID;

/**
 * Created by root on 22/01/16.
 */
public class ReceivedMessage extends NetworkServiceMessage {



    public ReceivedMessage(final UUID   requestId,
                       String identitySender,
                       String actorDestination) {

        super(requestId, MessageTypes.RECEIVED,identitySender,actorDestination);


    }


    @Override
    public String toString() {
        return "ReceivedMessage{" +
                "requestId=" + getRequestId() +
                '}';
    }
}
