package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequestEvent</code>
 * haves all the common methods of crypto payment request events.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 07/10/2015.
 */
public interface CryptoPaymentRequestEvent {

    UUID getRequestId();

    void setRequestId(UUID requestId);

}
