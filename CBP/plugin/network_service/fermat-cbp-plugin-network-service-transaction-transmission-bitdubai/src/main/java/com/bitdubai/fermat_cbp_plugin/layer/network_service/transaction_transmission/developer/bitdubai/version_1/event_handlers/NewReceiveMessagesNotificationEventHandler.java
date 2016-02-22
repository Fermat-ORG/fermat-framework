package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.TransactionTransmissionPluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractNewReceiveMessagesNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageReceivedNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 10/02/16.
 */
public class NewReceiveMessagesNotificationEventHandler implements FermatEventHandler {

    private TransactionTransmissionPluginRoot transactionTransmissionPluginRoot;
    /**
     * Constructor with parameter
     *
     * @param transactionTransmissionPluginRoot
     */
    public NewReceiveMessagesNotificationEventHandler(TransactionTransmissionPluginRoot transactionTransmissionPluginRoot) {
        this.transactionTransmissionPluginRoot = transactionTransmissionPluginRoot;
    }


    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (fermatEvent.getSource() == TransactionTransmissionPluginRoot.EVENT_SOURCE) {
            /*
                 * Get the message receive
                 */
            NewNetworkServiceMessageReceivedNotificationEvent newNetworkServiceMessageReceivedNotificationEvent = (NewNetworkServiceMessageReceivedNotificationEvent) fermatEvent;
            FermatMessage fermatMessageReceive = (FermatMessage) newNetworkServiceMessageReceivedNotificationEvent.getData();

            //System.out.println("Transaction Transmission - NewReceiveMessagesNotificationEventHandler - handleNewMessages =" + message.toString());

            transactionTransmissionPluginRoot.handleNewMessages(fermatMessageReceive);

        }
    }
}
