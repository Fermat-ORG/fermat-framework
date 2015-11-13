/*
 * @#FailureComponentConnectionRequestNotificationEventHandler.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers.communication;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.FailureComponentConnectionRequestNotificationEvent;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.event_handlers.FailureComponentConnectionRequestNotificationEventHandler</code>
 * implements the handle to the event <code>com.bitdubai.fermat_api.layer.platform_service.event_manager.events.FailureComponentConnectionRequestNotificationEventListener</code><p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 09/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class FailureComponentConnectionRequestNotificationEventHandler implements FermatEventHandler {

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

        System.out.println("FailureComponentConnectionRequestNotificationEventListener - handleEvent platformEvent ="+platformEvent.getEventType() );

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
