package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.communication.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.CryptoPaymentRequestNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteRequestListComponentRegisteredNotificationEvent;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.communication.event_handlers.CompleteRequestListComponentRegisteredNotificationEventHandler</code>
 * implements the handle to the event <code>com.bitdubai.fermat_api.layer.platform_service.event_manager.events.CompleteRequestListComponentRegisteredNotificationEvent</code><p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 06/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CompleteRequestListComponentRegisteredNotificationEventHandler implements FermatEventHandler {

    private final CryptoPaymentRequestNetworkServicePluginRoot cryptoPaymentRequestNetworkServicePluginRoot;

    /**
     * Constructor with parameter
     *
     * @param cryptoPaymentRequestNetworkServicePluginRoot
     */
    public CompleteRequestListComponentRegisteredNotificationEventHandler(final CryptoPaymentRequestNetworkServicePluginRoot cryptoPaymentRequestNetworkServicePluginRoot) {
        this.cryptoPaymentRequestNetworkServicePluginRoot = cryptoPaymentRequestNetworkServicePluginRoot;
    }

    /**
     * (non-Javadoc)
     *
     * @see FermatEventHandler#handleEvent(FermatEvent)
     *
     * @param platformEvent
     * @throws Exception
     */
    @Override
    public void handleEvent(FermatEvent platformEvent) throws FermatException {

        System.out.println("CompleteRequestListComponentRegisteredNotificationEventHandler - handleEvent platformEvent ="+platformEvent+ " | NetworkServiceType: "+NetworkServiceType.CRYPTO_PAYMENT_REQUEST);


        if (this.cryptoPaymentRequestNetworkServicePluginRoot.getStatus() == ServiceStatus.STARTED) {

            CompleteRequestListComponentRegisteredNotificationEvent completeRequestListComponentRegisteredNotificationEvent = (CompleteRequestListComponentRegisteredNotificationEvent) platformEvent;


            if (completeRequestListComponentRegisteredNotificationEvent.getPlatformComponentType()  == PlatformComponentType.ACTOR_NETWORK_SERVICE &&
                    completeRequestListComponentRegisteredNotificationEvent.getNetworkServiceType() == NetworkServiceType.CRYPTO_PAYMENT_REQUEST){

                 /*
                 *  TemplateManager make the job
                 */
                this.cryptoPaymentRequestNetworkServicePluginRoot.handleCompleteRequestListComponentRegisteredNotificationEvent(completeRequestListComponentRegisteredNotificationEvent.getRegisteredComponentList());

            }


        }
    }
}
