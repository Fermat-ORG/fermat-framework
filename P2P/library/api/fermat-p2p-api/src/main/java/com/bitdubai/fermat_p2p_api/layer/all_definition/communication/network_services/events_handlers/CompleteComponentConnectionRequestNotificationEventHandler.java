/*
 * @#CompleteComponentConnectionRequestNotificationEventHandler.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentConnectionRequestNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.event_handlers.CompleteComponentConnectionRequestNotificationEventHandler</code>
 * implements the handle to the event <code>com.bitdubai.fermat_api.layer.platform_service.event_manager.events.CompleteComponentConnectionRequestNotificationEvent</code><p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 22/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CompleteComponentConnectionRequestNotificationEventHandler implements FermatEventHandler<CompleteComponentConnectionRequestNotificationEvent> {

    /**
     * Represent the networkService
     */
    private AbstractNetworkServiceBase networkService;

    /**
     * Constructor with parameter
     *
     * @param networkService
     */
    public CompleteComponentConnectionRequestNotificationEventHandler(AbstractNetworkServiceBase networkService) {
        this.networkService = networkService;
    }

    /**
     * (non-Javadoc)
     *
     * @see FermatEventHandler#handleEvent(FermatEvent)
     *
     * @param completeComponentConnectionRequestNotificationEvent
     * @throws Exception
     */
    @Override
    public void handleEvent(CompleteComponentConnectionRequestNotificationEvent completeComponentConnectionRequestNotificationEvent) throws FermatException {

        if (this.networkService.getStatus() == ServiceStatus.STARTED) {

            if(networkService.getNetworkServiceProfile() == null ||
                    completeComponentConnectionRequestNotificationEvent.getNetworkServiceTypeApplicant() == null) {

                if (networkService.getNetworkServiceProfile() == null)
                    System.out.println("********************************************************** \n\n plugin " + this.networkService.eventSource + " networkService.getNetworkServiceProfile() == null \n\n\n **********************************************************");

                if (completeComponentConnectionRequestNotificationEvent.getNetworkServiceTypeApplicant() == null)
                    System.out.println("********************************************************** \n\n plugin " + this.networkService.eventSource + " completeComponentConnectionRequestNotificationEvent.getNetworkServiceTypeApplicant() == null \n\n\n **********************************************************");



            }else if(completeComponentConnectionRequestNotificationEvent.getNetworkServiceTypeApplicant() == networkService.getNetworkServiceProfile().getNetworkServiceType()){

                /*
                 *  networkService make the job
                 */
                this.networkService.handleCompleteComponentConnectionRequestNotificationEvent(completeComponentConnectionRequestNotificationEvent);

            }
        }
    }
}
