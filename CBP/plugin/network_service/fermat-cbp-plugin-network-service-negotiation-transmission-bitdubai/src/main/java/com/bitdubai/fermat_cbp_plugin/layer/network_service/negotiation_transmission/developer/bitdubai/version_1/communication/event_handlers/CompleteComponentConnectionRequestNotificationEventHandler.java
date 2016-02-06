package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.communication.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractCompleteComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentConnectionRequestNotificationEvent;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.communication.event_handlers.CompleteComponentConnectionRequestNotificationEventHandler</code>
 * implements the handle to the event <code>com.bitdubai.fermat_api.layer.platform_service.event_manager.events.CompleteComponentConnectionRequestNotificationEvent</code><p/>
 *
 * The method <code>handleCompleteComponentRegistrationNotificationEvent</code> can be override to modify its behavior.
 *
 * Create Yordin Alayn 16.09.15
 * Based in CompleteComponentConnectionRequestNotificationEventHandler by lnacosta (laion.cj91@gmail.com) on 30/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
/*public final class CompleteComponentConnectionRequestNotificationEventHandler extends AbstractCompleteComponentConnectionRequestNotificationEventHandler {

    public CompleteComponentConnectionRequestNotificationEventHandler(AbstractNetworkService networkService) {
        super(networkService);
    }

}*/

public class CompleteComponentConnectionRequestNotificationEventHandler implements FermatEventHandler {

    /*
    * Represent the networkService
    */
    private NetworkService networkService;

    /**
     * Constructor with parameter
     *
     * @param networkService
     */
    public CompleteComponentConnectionRequestNotificationEventHandler(NetworkService networkService) {
        this.networkService = networkService;
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

         System.out.println("Negotiation Transmission - CompleteComponentConnectionRequestNotificationEventHandler - handleEvent platformEvent ="+platformEvent+" end NT\n" );
         System.out.println("Negotiation Transmission - CompleteComponentConnectionRequestNotificationEventHandler - network service  ="+networkService.getNetworkServiceType()+" end NT\n" );

        if (((Service) this.networkService).getStatus() == ServiceStatus.STARTED) {

            CompleteComponentConnectionRequestNotificationEvent completeComponentConnectionRequestNotificationEvent = (CompleteComponentConnectionRequestNotificationEvent) platformEvent;


            if (completeComponentConnectionRequestNotificationEvent.getNetworkServiceTypeApplicant() == this.networkService.getNetworkServiceType()) {

                /*
                 *  networkService make the job
                 */
                this.networkService.handleCompleteComponentConnectionRequestNotificationEvent(completeComponentConnectionRequestNotificationEvent.getApplicantComponent(), completeComponentConnectionRequestNotificationEvent.getRemoteComponent());

            }
        }
    }
}
