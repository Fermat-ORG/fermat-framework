/*
 * @#CompleteComponentRegistrationNotificationEventHandler.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_network_service.template.TemplateManager;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.TemplateNetworkServicePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure.TemplateNetworkServiceManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.CompleteComponentRegistrationNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.event_handlers.TemplateIncomingNetworkServiceConnectionRequestHandler</code>
 * implements the handle to the event <code>com.bitdubai.fermat_api.layer.platform_service.event_manager.events.CompleteComponentRegistrationNotificationEvent</code><p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 14/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CompleteComponentRegistrationNotificationEventHandler implements EventHandler {

    /*
    * Represent the templateNetworkServicePluginRoot
    */
    private TemplateNetworkServicePluginRoot templateNetworkServicePluginRoot;

    /**
     * Constructor with parameter
     *
     * @param templateNetworkServicePluginRoot
     */
    public CompleteComponentRegistrationNotificationEventHandler(TemplateNetworkServicePluginRoot templateNetworkServicePluginRoot) {
        this.templateNetworkServicePluginRoot = templateNetworkServicePluginRoot;
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

        if (((Service) this.templateNetworkServicePluginRoot).getStatus() == ServiceStatus.STARTED) {

            /*
             *  TemplateManager make the job
             */
            CompleteComponentRegistrationNotificationEvent completeComponentRegistrationNotificationEvent = (CompleteComponentRegistrationNotificationEvent) platformEvent;
            this.templateNetworkServicePluginRoot.handleCompleteComponentRegistrationNotificationEvent();

        }
    }
}
