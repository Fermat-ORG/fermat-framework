package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentRegistrationNotificationEvent;

/**
 * Created by Gabriel Araujo on 05/01/16.
 */
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

        //System.out.println("CompleteComponentRegistrationNotificationEventHandler - handleEvent platformEvent ="+platformEvent );

        if (((Service) this.networkService).getStatus() == ServiceStatus.STARTED) {


            CompleteComponentRegistrationNotificationEvent completeComponentRegistrationNotificationEvent = (CompleteComponentRegistrationNotificationEvent) fermatEvent;

            if (completeComponentRegistrationNotificationEvent.getPlatformComponentProfileRegistered().getNetworkServiceType() == networkService.getNetworkServiceType()) {

                /*
                 *  networkService make the job
                 */
                this.networkService.handleCompleteComponentRegistrationNotificationEvent(completeComponentRegistrationNotificationEvent.getPlatformComponentProfileRegistered());

            }
        }
    }
}
