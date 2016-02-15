package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.communication.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractCompleteComponentRegistrationNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentRegistrationNotificationEvent;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.CompleteComponentRegistrationNotificationEventHandler</code>
 * contains all the basic functionality of a CompleteComponentRegistrationNotificationEventHandler.
 *
 * The method <code>handleCompleteComponentRegistrationNotificationEvent</code> can be override to modify its behavior.
 *
 * Created by lnacosta (laion.cj91@gmail.com) on 30/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
/*public final class CompleteComponentRegistrationNotificationEventHandler extends AbstractCompleteComponentRegistrationNotificationEventHandler {

    public CompleteComponentRegistrationNotificationEventHandler(final AbstractNetworkService networkService) {
        super(networkService);
    }

}*/

public class CompleteComponentRegistrationNotificationEventHandler implements FermatEventHandler {

    /*
    * Represent the networkService
    */
    private NetworkService networkService;
    /**
     * Constructor with parameter.
     *
     * @param networkService
     */
    public CompleteComponentRegistrationNotificationEventHandler(NetworkService networkService) {
        this.networkService = networkService;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

//        System.out.println("Negotiation Transmission - CompleteComponentRegistrationNotificationEventHandler - handleEvent platformEvent = "+fermatEvent.toString()+" end NT\n" );

        if (((Service) this.networkService).getStatus() == ServiceStatus.STARTED) {


            CompleteComponentRegistrationNotificationEvent completeComponentRegistrationNotificationEvent = (CompleteComponentRegistrationNotificationEvent) fermatEvent;

            if (completeComponentRegistrationNotificationEvent.getPlatformComponentProfileRegistered().getNetworkServiceType() == networkService.getNetworkServiceType() ||
                    completeComponentRegistrationNotificationEvent.getPlatformComponentProfileRegistered().getPlatformComponentType() == PlatformComponentType.COMMUNICATION_CLOUD_CLIENT) {

                /*
                 *  networkService make the job
                 */
                this.networkService.handleCompleteComponentRegistrationNotificationEvent(completeComponentRegistrationNotificationEvent.getPlatformComponentProfileRegistered());

            }
        }
    }
}
