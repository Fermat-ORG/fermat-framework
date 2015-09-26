/*
 * @#TemplateEstablishedRequestedNetworkServiceConnectionHandler.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.dmp_network_service.template.TemplateManager;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure.TemplateNetworkServiceManager;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.events.EstablishedNetworkServiceConnectionEvent;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.event_handlers.TemplateEstablishedRequestedNetworkServiceConnectionHandler</code>
 * is work when a new connection was stablished between two network services<p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 21/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class TemplateEstablishedRequestedNetworkServiceConnectionHandler implements FermatEventHandler {

    /*
    * Represent the templateManager
    */
    private TemplateManager templateManager;

    /**
     * Constructor with parameter
     *
     * @param templateManager the Intra User Manager
     */
    public TemplateEstablishedRequestedNetworkServiceConnectionHandler(TemplateManager templateManager) {
        this.templateManager = templateManager;
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

        if (((Service) this.templateManager).getStatus() == ServiceStatus.STARTED) {

            /*
             *  TemplateManager make the job
             */
            EstablishedNetworkServiceConnectionEvent stablishedNetworkServiceConnectionEvent = (EstablishedNetworkServiceConnectionEvent) fermatEvent;
            ((TemplateNetworkServiceManager) this.templateManager).handleEstablishedRequestedNetworkServiceConnection(stablishedNetworkServiceConnectionEvent.getRemoteNetworkServicePublicKey());

        }
    }
}
