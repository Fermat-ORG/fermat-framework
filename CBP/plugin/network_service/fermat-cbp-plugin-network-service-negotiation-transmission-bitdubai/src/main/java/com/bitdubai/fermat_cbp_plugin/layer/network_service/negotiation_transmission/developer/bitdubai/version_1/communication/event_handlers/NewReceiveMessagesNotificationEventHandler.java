package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.communication.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.NetworkServiceNegotiationTransmissionPluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractNewReceiveMessagesNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

/**
 * Created by lnacosta (laion.cj91@gmail.com) on 21/11/2015.
 */
/*public final class NewReceiveMessagesNotificationEventHandler extends AbstractNewReceiveMessagesNotificationEventHandler {

    public NewReceiveMessagesNotificationEventHandler(AbstractNetworkService networkServiceNegotiationTransmissionPluginRoot) {
        super(networkServiceNegotiationTransmissionPluginRoot);
    }
}*/

public class NewReceiveMessagesNotificationEventHandler extends AbstractNewReceiveMessagesNotificationEventHandler {
    /**
     * Constructor with parameter
     *
     * @param networkService
     */
    public NewReceiveMessagesNotificationEventHandler(NetworkServiceNegotiationTransmissionPluginRoot networkService) {
        super(networkService);
    }

    @Override
    protected void handleNewMessages(FermatMessage message) throws FermatException {

        System.out.println("Negotiation Transmission - NewReceiveMessagesNotificationEventHandler - handleNewMessages =" + message.toString()+" end NT\n");

        ((NetworkServiceNegotiationTransmissionPluginRoot)networkService).handleNewMessages(message);

    }
}
