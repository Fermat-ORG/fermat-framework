package com.bitdubai.fermat_cht_plugin.layer.network_service.developer.chat.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.developer.chat.version_1.ChatPluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractNewReceiveMessagesNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

/**
 * Created by  Gabriel Araujo on 05/01/16.
 */
public class NewReceiveMessagesNotificationEventHandler extends AbstractNewReceiveMessagesNotificationEventHandler {
    /**
     * Constructor with parameter
     *
     * @param networkService
     */
    public NewReceiveMessagesNotificationEventHandler(ChatPluginRoot networkService) {
        super(networkService);
    }

    @Override
    protected void handleNewMessages(FermatMessage message) throws FermatException {

        //System.out.println("Transaction Transmission - NewReceiveMessagesNotificationEventHandler - handleNewMessages =" + message.toString());

        ((ChatPluginRoot)networkService).handleNewMessages(message);

    }
}
