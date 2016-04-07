/*
* @#CompleteUpdateActorNotificationEventHandler.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteUpdateActorNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers.communication.CompleteUpdateActorNotificationEventHandler</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 08/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CompleteUpdateActorNotificationEventHandler implements FermatEventHandler<CompleteUpdateActorNotificationEvent> {

    /**
     * Represent the networkService
     */
    private AbstractNetworkServiceBase networkService;

    /**
     * Constructor with parameter
     * @param networkService
     */
    public CompleteUpdateActorNotificationEventHandler(AbstractNetworkServiceBase networkService){
        this.networkService = networkService;
    }

    /**
     * (non-javadoc)
     * @see FermatEventHandler#handleEvent(FermatEvent)
     */
    @Override
    public void handleEvent(CompleteUpdateActorNotificationEvent completeUpdateActorNotificationEvent) throws FermatException {
        if (this.networkService.getStatus() == ServiceStatus.STARTED) {

            if(this.networkService.getNetworkServiceProfile() == null ||
                    completeUpdateActorNotificationEvent.getNetworkServiceTypeApplicant() == null) {

                if (networkService.getNetworkServiceProfile() == null)
                    System.out.println("********************************************************** \n\n plugin " + this.networkService.eventSource + " networkService.getNetworkServiceProfile() == null \n\n\n **********************************************************");

                if (completeUpdateActorNotificationEvent.getNetworkServiceTypeApplicant() == null)
                    System.out.println("********************************************************** \n\n plugin " + this.networkService.eventSource + " completeUpdateActorNotificationEvent.getNetworkServiceTypeApplicant() == null \n\n\n **********************************************************");



            }else if(completeUpdateActorNotificationEvent.getNetworkServiceTypeApplicant() == this.networkService.getNetworkServiceProfile().getNetworkServiceType()) {
                this.networkService.handleCompleteUpdateActorNotificationEvent(completeUpdateActorNotificationEvent);
            }
        }

    }

}
