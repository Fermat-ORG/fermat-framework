package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.CryptoAddressesNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentConnectionRequestNotificationEvent;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.event_handlers.CompleteComponentConnectionRequestNotificationEventHandler</code>
 * implements the handle to the event <code>com.bitdubai.fermat_api.layer.platform_service.event_manager.events.CompleteComponentConnectionRequestNotificationEvent</code><p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CompleteComponentConnectionRequestNotificationEventHandler implements FermatEventHandler {

    private final CryptoAddressesNetworkServicePluginRoot cryptoAddressesNetworkServicePluginRoot;

    /**
     * Constructor with parameter
     *
     * @param cryptoAddressesNetworkServicePluginRoot
     */
    public CompleteComponentConnectionRequestNotificationEventHandler(final CryptoAddressesNetworkServicePluginRoot cryptoAddressesNetworkServicePluginRoot) {
        this.cryptoAddressesNetworkServicePluginRoot = cryptoAddressesNetworkServicePluginRoot;
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

        System.out.println("CompleteComponentConnectionRequestNotificationEventHandler - handleEvent platformEvent ="+platformEvent+ " | NetworkServiceType: "+NetworkServiceType.CRYPTO_ADDRESSES);


        if (this.cryptoAddressesNetworkServicePluginRoot.getStatus() == ServiceStatus.STARTED) {

            CompleteComponentConnectionRequestNotificationEvent completeComponentConnectionRequestNotificationEvent = (CompleteComponentConnectionRequestNotificationEvent) platformEvent;



            if(completeComponentConnectionRequestNotificationEvent.getNetworkServiceTypeApplicant() == cryptoAddressesNetworkServicePluginRoot.getPlatformComponentProfilePluginRoot().getNetworkServiceType()){

                 /*
                 *  TemplateManager make the job
                 */
                this.cryptoAddressesNetworkServicePluginRoot.handleCompleteComponentConnectionRequestNotificationEvent(completeComponentConnectionRequestNotificationEvent.getRemoteComponent());

            }


        }
    }
}
