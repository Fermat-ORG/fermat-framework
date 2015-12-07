package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.communication.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractFailureComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.FailureComponentConnectionRequestNotificationEvent;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.communication.event_handlers.FailureComponentConnectionRequestNotificationEventHandler</code>
 * implements the handle of the event <code>com.bitdubai.fermat_api.layer.platform_service.event_manager.events.FailureComponentConnectionRequestNotificationEvent</code><p/>
 *
 * Created by lnacosta (laion.cj91@gmail.com) on 30/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class FailureComponentConnectionRequestNotificationEventHandler implements FermatEventHandler {

    /*
    * Represent the networkService
    */
    private NetworkService networkService;

    /**
     * Constructor with parameter
     *
     * @param networkService
     */
    public FailureComponentConnectionRequestNotificationEventHandler(NetworkService networkService) {
        this.networkService = networkService;
        System.out.println("FAILURE CONNECTION HANDLER STARTING FROM CRYTO PAYMENT");
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

        //System.out.println("FailureComponentConnectionRequestNotificationEventListener - handleEvent platformEvent ="+platformEvent.getEventType() );

        if (((Service) this.networkService).getStatus() == ServiceStatus.STARTED) {

            FailureComponentConnectionRequestNotificationEvent failureComponentConnectionRequestNotificationEvent = (FailureComponentConnectionRequestNotificationEvent) platformEvent;

            if (failureComponentConnectionRequestNotificationEvent.getNetworkServiceApplicant().getPlatformComponentType()  == networkService.getPlatformComponentType() &&
                    failureComponentConnectionRequestNotificationEvent.getNetworkServiceApplicant().getNetworkServiceType() == networkService.getNetworkServiceType()){

                /*
                 *  networkService make the job
                 */
                this.networkService.handleFailureComponentRegistrationNotificationEvent(failureComponentConnectionRequestNotificationEvent.getNetworkServiceApplicant(), failureComponentConnectionRequestNotificationEvent.getRemoteParticipant());

            }


        }
    }
}
