package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractCommunicationBaseEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentConnectionRequestNotificationEvent;

/**
 * Created by Gabriel Araujo on 05/01/16.
 */
public class CommunicationBaseEventHandler extends AbstractCommunicationBaseEventHandler<CompleteComponentConnectionRequestNotificationEvent> {
    /**
     * Constructor with parameter
     *
     * @param networkService
     */
    public CommunicationBaseEventHandler(NetworkService networkService) {
        super(networkService);
    }

    @Override
    public void processEvent(CompleteComponentConnectionRequestNotificationEvent event) {
        //TODO: to process
        //System.out.println("Transaction Transmission - CommunicationBaseEventHandler:\n"+event);
    }
}
