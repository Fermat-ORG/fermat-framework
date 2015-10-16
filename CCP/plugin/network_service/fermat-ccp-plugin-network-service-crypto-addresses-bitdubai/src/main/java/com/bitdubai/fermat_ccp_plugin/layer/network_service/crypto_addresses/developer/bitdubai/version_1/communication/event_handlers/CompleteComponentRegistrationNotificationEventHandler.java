package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.CryptoAddressesNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentRegistrationNotificationEvent;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.event_handlers.CompleteComponentRegistrationNotificationEventHandler</code>
 * implements the handle to the event <code>com.bitdubai.fermat_api.layer.platform_service.event_manager.events.CompleteComponentRegistrationNotificationEvent</code><p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CompleteComponentRegistrationNotificationEventHandler implements FermatEventHandler {

    private final CryptoAddressesNetworkServicePluginRoot cryptoAddressesNetworkServicePluginRoot;

    /**
     * Constructor with parameter
     *
     * @param cryptoAddressesNetworkServicePluginRoot
     */
    public CompleteComponentRegistrationNotificationEventHandler(final CryptoAddressesNetworkServicePluginRoot cryptoAddressesNetworkServicePluginRoot) {
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

        System.out.println("CompleteComponentRegistrationNotificationEventHandler - handleEvent platformEvent ="+platformEvent+ " | NetworkServiceType: "+NetworkServiceType.CRYPTO_ADDRESSES);

        if (this.cryptoAddressesNetworkServicePluginRoot.getStatus() == ServiceStatus.STARTED) {


            CompleteComponentRegistrationNotificationEvent completeComponentRegistrationNotificationEvent = (CompleteComponentRegistrationNotificationEvent) platformEvent;

            if (completeComponentRegistrationNotificationEvent.getPlatformComponentProfileRegistered().getPlatformComponentType() == PlatformComponentType.ACTOR_NETWORK_SERVICE &&
                    completeComponentRegistrationNotificationEvent.getPlatformComponentProfileRegistered().getNetworkServiceType() == NetworkServiceType.CRYPTO_ADDRESSES) {

                /*
                 *  TemplateManager make the job
                 */
                 this.cryptoAddressesNetworkServicePluginRoot.handleCompleteComponentRegistrationNotificationEvent(completeComponentRegistrationNotificationEvent.getPlatformComponentProfileRegistered());

            }
        }
    }
}
