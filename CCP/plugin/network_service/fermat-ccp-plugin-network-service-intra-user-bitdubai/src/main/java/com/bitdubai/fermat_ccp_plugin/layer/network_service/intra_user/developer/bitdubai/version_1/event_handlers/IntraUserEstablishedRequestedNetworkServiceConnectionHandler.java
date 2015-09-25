/*
 * @#IntraUserStablishedRequestedNetworkServiceConnectionHandler.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.ccp_network_service.intra_user.interfaces.IntraUserManager;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.network_service.IntraUserNetworkServiceCommunicationManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.events.EstablishedNetworkServiceConnectionEvent;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers.IntraUserEstablishedRequestedNetworkServiceConnectionHandler</code>
 * is work when a new connection was stablished between two network services<p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 24/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IntraUserEstablishedRequestedNetworkServiceConnectionHandler implements FermatEventHandler {

    /*
    * Represent the intraUserManager
    */
    private IntraUserManager intraUserManager;

    /**
     * Constructor with parameter
     *
     * @param intraUserManager the Intra User Manager
     */
    public IntraUserEstablishedRequestedNetworkServiceConnectionHandler(IntraUserManager intraUserManager) {
        this.intraUserManager = intraUserManager;
    }

    /**
     * (non-Javadoc)
     *
     * @see FermatEventHandler#handleEvent(FermatEvent)
     *
     * @param fermatEvent the platform event
     * @throws Exception the exception
     */
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        if (((Service) this.intraUserManager).getStatus() == ServiceStatus.STARTED) {

            /*
             *  ActorIntraUserManager make the job
             */
            EstablishedNetworkServiceConnectionEvent stablishedNetworkServiceConnectionEvent = (EstablishedNetworkServiceConnectionEvent) fermatEvent;
            ((IntraUserNetworkServiceCommunicationManager) this.intraUserManager).handleEstablishedRequestedNetworkServiceConnection(stablishedNetworkServiceConnectionEvent.getCommunicationChannels(),
                    stablishedNetworkServiceConnectionEvent.getRemoteNetworkServicePublicKey());

        }
    }
}
