/*
 * @#CompleteComponentConnectionRequestNotificationEventHandler.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentConnectionRequestNotificationEvent;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.event_handlers.CompleteComponentConnectionRequestNotificationEventHandler</code> is
 * throw when error occurred updating new record in a table of the data base
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 07/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
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

        System.out.println("CompleteComponentConnectionRequestNotificationEventHandler - handleEvent platformEvent ="+platformEvent );


        if (((Service) this.networkService).getStatus() == ServiceStatus.STARTED) {

            CompleteComponentConnectionRequestNotificationEvent completeComponentConnectionRequestNotificationEvent = (CompleteComponentConnectionRequestNotificationEvent) platformEvent;


            if (completeComponentConnectionRequestNotificationEvent.getNetworkServiceTypeApplicant() == networkService.getNetworkServiceType()){
            if (completeComponentConnectionRequestNotificationEvent.getNetworkServiceTypeApplicant() == networkService.getNetworkServiceType()){

                /*
                 *  networkService make the job
                 */
                this.networkService.handleCompleteComponentConnectionRequestNotificationEvent(completeComponentConnectionRequestNotificationEvent.getApplicantComponent(), completeComponentConnectionRequestNotificationEvent.getRemoteComponent());

            }


        }
    }
}
