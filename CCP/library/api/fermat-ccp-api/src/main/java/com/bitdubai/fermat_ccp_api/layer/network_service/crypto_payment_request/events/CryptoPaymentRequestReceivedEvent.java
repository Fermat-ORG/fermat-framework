package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_ccp_api.all_definition.events.AbstractCCPEvent;

import java.util.UUID;

/**
 * The event <code>com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.events.CryptoPaymentRequestReceivedEvent</code>
 * is raised when an actor send us a crypto payment request.
 *
 * Created by by Leon Acosta (laion.cj91@gmail.com) on 05/10/2015.
 */
public class CryptoPaymentRequestReceivedEvent extends AbstractCCPEvent implements com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequestEvent {

    private UUID requestId;

    public CryptoPaymentRequestReceivedEvent(FermatEventEnum eventType) {
        super(eventType);
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(final UUID requestId) {
        this.requestId = requestId;
    }

}
