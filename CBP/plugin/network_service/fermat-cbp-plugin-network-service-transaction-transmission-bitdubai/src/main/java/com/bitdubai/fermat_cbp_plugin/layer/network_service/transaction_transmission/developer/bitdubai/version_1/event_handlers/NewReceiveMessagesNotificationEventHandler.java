package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.TransactionTransmissionPluginRoot;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/11/15.
 */
public class NewReceiveMessagesNotificationEventHandler extends com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractNewReceiveMessagesNotificationEventHandler {
    /**
     * Constructor with parameter
     *
     * @param networkService
     */
    public NewReceiveMessagesNotificationEventHandler(TransactionTransmissionPluginRoot networkService) {
        super(networkService);
    }

    @Override
    protected void handleNewMessages(FermatMessage message) throws FermatException {

    }
}
