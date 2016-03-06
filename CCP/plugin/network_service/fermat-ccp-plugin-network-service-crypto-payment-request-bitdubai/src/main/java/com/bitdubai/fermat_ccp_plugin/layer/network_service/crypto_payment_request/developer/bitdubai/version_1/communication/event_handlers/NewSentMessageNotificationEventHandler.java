package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.communication.event_handlers;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractCommunicationBaseEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageSentNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

/**
 * Created by natalia on 26/01/16.
 */
public class NewSentMessageNotificationEventHandler extends AbstractCommunicationBaseEventHandler<NewNetworkServiceMessageSentNotificationEvent> {

    /**
     * Constructor with parameter
     *
     * @param
     */


    public NewSentMessageNotificationEventHandler(NetworkService networkService) {
        super(networkService);
    }

    /**
     * (non-Javadoc)
     *
     * @see FermatEventHandler#handleEvent(FermatEvent)
     *
     * @param event
     * @throws Exception
     */
    @Override
    public void processEvent(NewNetworkServiceMessageSentNotificationEvent event) {
        ns.handleNewSentMessageNotificationEvent((FermatMessage) event.getData());
    }
}
