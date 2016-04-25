/*
* @#RegisterServerRequestNotificationEventHandler.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.RegisterServerRequestNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.events_handlers.RegisterServerRequestNotificationEventHandler</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 12/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class RegisterServerRequestNotificationEventHandler implements FermatEventHandler<RegisterServerRequestNotificationEvent> {

    /**
     * Represent the networkService
     */
    private AbstractNetworkServiceBase networkService;

    /**
     * Constructor with parameter
     *
     * @param networkService
     */
    public RegisterServerRequestNotificationEventHandler(AbstractNetworkServiceBase networkService) {
        this.networkService = networkService;
    }

    @Override
    public void handleEvent(RegisterServerRequestNotificationEvent fermatEvent) throws FermatException {

        if (this.networkService.getStatus() == ServiceStatus.STARTED) {

            if(fermatEvent.getListNetworkServiceTypeToReconnecting() != null){

                for(NetworkServiceType NS : fermatEvent.getListNetworkServiceTypeToReconnecting()){
                    if(NS == networkService.getNetworkServiceProfile().getNetworkServiceType()){
                        this.networkService.setDesRegistered();
                        break;
                    }
                }

            }

        }

    }

}
