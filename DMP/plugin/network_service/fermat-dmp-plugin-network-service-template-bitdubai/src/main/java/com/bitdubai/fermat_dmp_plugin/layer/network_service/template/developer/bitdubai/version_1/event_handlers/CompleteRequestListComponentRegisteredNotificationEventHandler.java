/*
 * @#CompleteRequestListComponentRegisteredNotificationEventHandler.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.TemplateNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteRequestListComponentRegisteredNotificationEvent;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.event_handlers.CompleteRequestListComponentRegisteredNotificationEventHandler</code>
 * implements the handle to the event <code>com.bitdubai.fermat_api.layer.platform_service.event_manager.events.CompleteRequestListComponentRegisteredNotificationEvent</code><p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 22/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CompleteRequestListComponentRegisteredNotificationEventHandler implements FermatEventHandler {

    /*
    * Represent the templateNetworkServicePluginRoot
    */
    private TemplateNetworkServicePluginRoot templateNetworkServicePluginRoot;

    /**
     * Constructor with parameter
     *
     * @param templateNetworkServicePluginRoot
     */
    public CompleteRequestListComponentRegisteredNotificationEventHandler(TemplateNetworkServicePluginRoot templateNetworkServicePluginRoot) {
        this.templateNetworkServicePluginRoot = templateNetworkServicePluginRoot;
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

        System.out.println("CompleteRequestListComponentRegisteredNotificationEventHandler - handleEvent platformEvent ="+platformEvent );


        if (((Service) this.templateNetworkServicePluginRoot).getStatus() == ServiceStatus.STARTED) {

            CompleteRequestListComponentRegisteredNotificationEvent completeRequestListComponentRegisteredNotificationEvent = (CompleteRequestListComponentRegisteredNotificationEvent) platformEvent;


            if (completeRequestListComponentRegisteredNotificationEvent.getPlatformComponentType()  == PlatformComponentType.NETWORK_SERVICE_COMPONENT &&
                    completeRequestListComponentRegisteredNotificationEvent.getNetworkServiceType() == NetworkServiceType.NETWORK_SERVICE_TEMPLATE_TYPE){

                 /*
                 *  TemplateManager make the job
                 */
                this.templateNetworkServicePluginRoot.handleCompleteRequestListComponentRegisteredNotificationEvent(completeRequestListComponentRegisteredNotificationEvent.getRegisteredComponentList());

            }


        }
    }
}
