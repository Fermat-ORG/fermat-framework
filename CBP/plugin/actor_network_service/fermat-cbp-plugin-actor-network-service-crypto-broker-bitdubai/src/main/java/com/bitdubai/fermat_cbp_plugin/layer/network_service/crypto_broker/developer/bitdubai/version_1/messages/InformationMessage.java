package com.bitdubai.fermat_cbp_plugin.layer.network_service.crypto_broker.developer.bitdubai.version_1.messages;

import com.bitdubai.fermat_cbp_plugin.layer.network_service.crypto_broker.developer.bitdubai.version_1.enums.MessageTypes;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.enums.RequestAction;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.messages.InformationMessage</code>
 * contains the structure of a Information message for this plugin.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/10/2015.
 */
public class InformationMessage extends NetworkServiceMessage {

    private final UUID          requestId;
    private final RequestAction action   ;

    public InformationMessage(final UUID          requestId,
                              final RequestAction action   ) {

        super(MessageTypes.INFORMATION);

        this.requestId = requestId;
        this.action    = action   ;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public RequestAction getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "InformationMessage{" +
                "requestId=" + requestId +
                ", action=" + action +
                '}';
    }
}
