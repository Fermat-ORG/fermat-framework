package com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.events.CryptoPaymentRequestNewsEvent;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.CryptoPaymentRequestPluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CryptoPaymentRequestPluginNotStartedException;
import com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.structure.CryptoPaymentRequestEventActions;

/**
 * Throw the event handler <code>com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.event_handlers.CryptoPaymentRequestNewsEventHandler</code> we can handle
 * Crypto Payment Request News events.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoPaymentRequestNewsEventHandler implements FermatEventHandler {

    private final CryptoPaymentRequestPluginRoot   cryptoPaymentRequestPluginRoot;
    private final CryptoPaymentRequestEventActions eventActions                  ;

    public CryptoPaymentRequestNewsEventHandler(final CryptoPaymentRequestPluginRoot   cryptoPaymentRequestPluginRoot,
                                                final CryptoPaymentRequestEventActions eventActions                  ) {

        this.cryptoPaymentRequestPluginRoot = cryptoPaymentRequestPluginRoot;
        this.eventActions                   = eventActions                  ;
    }

    /**
     * FermatEventHandler interface implementation
     */
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        if (this.cryptoPaymentRequestPluginRoot.getStatus() == ServiceStatus.STARTED) {

            if (fermatEvent instanceof CryptoPaymentRequestNewsEvent) {

                eventActions.executePendingRequestEventActions();

            } else {
                EventType eventExpected = EventType.CRYPTO_PAYMENT_REQUEST_NEWS;
                String context = "Event received: " + fermatEvent.getEventType().toString() + " - " + fermatEvent.getEventType().getCode()+"\n"+
                                 "Event expected: " + eventExpected.toString()              + " - " + eventExpected.getCode();
                throw new UnexpectedEventException(context);
            }
        } else {
            throw new CryptoPaymentRequestPluginNotStartedException(null, "Plugin is not started.", "");
        }
    }

}