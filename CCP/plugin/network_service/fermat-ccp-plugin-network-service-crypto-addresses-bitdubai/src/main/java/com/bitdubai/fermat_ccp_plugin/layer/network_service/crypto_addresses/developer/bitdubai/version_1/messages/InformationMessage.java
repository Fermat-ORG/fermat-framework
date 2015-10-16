package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages;

import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.enums.MessageTypes;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.messages.InformationMessage</code>
 * contains the structure of a Information message for this plugin.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/10/2015.
 */
public class InformationMessage extends NetworkServiceMessage {

    private final UUID requestId;

    public InformationMessage(final UUID requestId) {

        super(MessageTypes.INFORMATION);

        this.requestId = requestId;
    }

    public UUID getRequestId() {
        return requestId;
    }


    @Override
    public String toString() {
        return "InformationMessage{" +
                "requestId=" + requestId +
                '}';
    }
}
