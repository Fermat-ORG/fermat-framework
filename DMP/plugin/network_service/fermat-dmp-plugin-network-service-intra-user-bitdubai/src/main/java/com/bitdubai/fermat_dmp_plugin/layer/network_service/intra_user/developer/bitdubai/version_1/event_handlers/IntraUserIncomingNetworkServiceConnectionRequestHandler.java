/*
 * @#IntraUserIncomingNetworkServiceConnectionRequestHandler.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.IntraUserManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.network_service.IntraUserNetworkServiceCommunicationManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.events.IncomingNetworkServiceConnectionRequestEvent;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers.IntraUserIncomingNetworkServiceConnectionRequestHandler</code>
 * implements the handle to the event <code>com.bitdubai.fermat_api.layer.platform_service.event_manager.events.IncomingNetworkServiceConnectionRequestEvent</code><p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 07/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IntraUserIncomingNetworkServiceConnectionRequestHandler implements EventHandler {

    /*
    * Represent the intraUserManager
    */
    private IntraUserManager intraUserManager;

    /**
     * Constructor with parameter
     *
     * @param intraUserManager
     */
    public IntraUserIncomingNetworkServiceConnectionRequestHandler(IntraUserManager intraUserManager) {
        this.intraUserManager = intraUserManager;
    }

    /**
     * (non-Javadoc)
     *
     * @see EventHandler#handleEvent(PlatformEvent)
     *
     * @param platformEvent
     * @throws Exception
     */
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws FermatException {

        if (((Service) this.intraUserManager).getStatus() == ServiceStatus.STARTED) {

            /*
             *  ActorIntraUserManager make the job
             */
            IncomingNetworkServiceConnectionRequestEvent incomingNetworkServiceConnectionRequestEvent = (IncomingNetworkServiceConnectionRequestEvent) platformEvent;
            ((IntraUserNetworkServiceCommunicationManager) this.intraUserManager).acceptIncomingNetworkServiceConnectionRequest(incomingNetworkServiceConnectionRequestEvent.getCommunicationChannels(),
                                                                                                                      incomingNetworkServiceConnectionRequestEvent.getRemoteNetworkServicePublicKey());

        }
    }
}
