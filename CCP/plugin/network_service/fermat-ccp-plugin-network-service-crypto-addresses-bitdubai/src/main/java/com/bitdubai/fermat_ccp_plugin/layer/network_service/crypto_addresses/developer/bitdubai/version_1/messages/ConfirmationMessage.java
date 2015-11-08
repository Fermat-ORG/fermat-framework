package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages;

import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.enums.MessageTypes;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages.DenyMessage</code>
 * contains the structure of a deny message for this plugin.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/10/2015.
 */
public class ConfirmationMessage extends NetworkServiceMessage {

    private final UUID   requestId;

    public ConfirmationMessage(final UUID requestId) {

        super(MessageTypes.CONFIRMATION);

        this.requestId = requestId;
    }

    public UUID getRequestId() {
        return requestId;
    }


    @Override
    public String toString() {
        return "ConfirmationMessage{" +
                "requestId=" + requestId +
                '}';
    }
}
