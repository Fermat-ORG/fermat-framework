/*
 * @#NewReceiveMessagesNotificationEventHandler.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventType;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.AssetTransmissionNetworkServicePluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.database.communications.DAPMessageDAO;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageReceivedNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

/**
 * The class <code>com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.event_handlers.NewSentMessagesNotificationEventHandler</code> listen
 * and handle the event <code>NewNetworkServiceMessageReceivedNotificationEvent</code>
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 13/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NewReceiveMessagesNotificationEventHandler implements FermatEventHandler {

    private AssetTransmissionNetworkServicePluginRoot pluginRoot;

    /**
     * Constructor with parameter
     *
     * @param
     */
    public NewReceiveMessagesNotificationEventHandler(AssetTransmissionNetworkServicePluginRoot pluginRoot) {
        this.pluginRoot = pluginRoot;
    }

    /**
     * (non-Javadoc)
     *
     * @param platformEvent
     * @throws Exception
     * @see FermatEventHandler#handleEvent(FermatEvent)
     */
    @Override
    public void handleEvent(FermatEvent platformEvent) throws FermatException {
        if (platformEvent.getSource() == AssetTransmissionNetworkServicePluginRoot.EVENT_SOURCE) {
            System.out.println("NEW MESSAGE RECEIVED!!!");
            FermatEvent event = pluginRoot.getEventManager().getNewEvent(EventType.RECEIVE_NEW_DAP_MESSAGE);
            event.setSource(com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.AssetTransmissionNetworkServicePluginRoot.EVENT_SOURCE);
            pluginRoot.getEventManager().raiseEvent(event);
        }
    }

}
