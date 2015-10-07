/*
 * @#CompleteComponentConnectionRequestNotificationEventHandler.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.AssetTransmissionPluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentConnectionRequestNotificationEvent;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.event_handlers.CompleteComponentConnectionRequestNotificationEventHandler</code>
 * implements the handle to the event <code>com.bitdubai.fermat_api.layer.platform_service.event_manager.events.CompleteComponentConnectionRequestNotificationEvent</code><p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CompleteComponentConnectionRequestNotificationEventHandler implements FermatEventHandler {

    /*
    * Represent the templateNetworkServicePluginRoot
    */
    private AssetTransmissionPluginRoot assetTransmissionPluginRoot;

    /**
     * Constructor with parameter
     *
     * @param assetTransmissionPluginRoot
     */
    public CompleteComponentConnectionRequestNotificationEventHandler(AssetTransmissionPluginRoot assetTransmissionPluginRoot) {
        this.assetTransmissionPluginRoot = assetTransmissionPluginRoot;
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


        if (((Service) this.assetTransmissionPluginRoot).getStatus() == ServiceStatus.STARTED) {

            CompleteComponentConnectionRequestNotificationEvent completeComponentConnectionRequestNotificationEvent = (CompleteComponentConnectionRequestNotificationEvent) platformEvent;


            if (completeComponentConnectionRequestNotificationEvent.getRemoteComponent().getPlatformComponentType()  == PlatformComponentType.NETWORK_SERVICE &&
                    completeComponentConnectionRequestNotificationEvent.getRemoteComponent().getNetworkServiceType() == NetworkServiceType.TEMPLATE){
                 /*
                 *  TemplateManager make the job
                 */
                this.assetTransmissionPluginRoot.handleCompleteComponentConnectionRequestNotificationEvent(completeComponentConnectionRequestNotificationEvent.getRemoteComponent());

            }


        }
    }
}
