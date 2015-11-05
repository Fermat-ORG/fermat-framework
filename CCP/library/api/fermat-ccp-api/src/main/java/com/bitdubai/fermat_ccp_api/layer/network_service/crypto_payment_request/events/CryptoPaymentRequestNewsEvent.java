package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_ccp_api.all_definition.events.AbstractCCPEvent;

/**
 * The event <code>com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.events.CryptoPaymentRequestNewsEvent</code>
 * is raised when there is crypto payment request news.
 *
 * Created by by Leon Acosta (laion.cj91@gmail.com) on 01/10/2015.
 */
public class CryptoPaymentRequestNewsEvent extends AbstractCCPEvent {

    public CryptoPaymentRequestNewsEvent(FermatEventEnum eventType) {
        super(eventType);
    }

}
