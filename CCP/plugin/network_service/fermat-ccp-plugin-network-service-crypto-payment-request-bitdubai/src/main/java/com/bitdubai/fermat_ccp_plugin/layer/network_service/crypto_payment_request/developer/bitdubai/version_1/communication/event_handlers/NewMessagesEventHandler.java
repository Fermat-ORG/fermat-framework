package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.communication.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageReceivedNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

/**
 * Created by natalia on 26/01/16.
 */
public class NewMessagesEventHandler implements FermatEventHandler {

    private NetworkService networkService;

    public NewMessagesEventHandler(NetworkService networkService) {
        this.networkService = networkService;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        NewNetworkServiceMessageReceivedNotificationEvent event = (NewNetworkServiceMessageReceivedNotificationEvent) fermatEvent;

        if(event.getNetworkServiceTypeApplicant() == networkService.getNetworkServiceType()){

            networkService.handleNewMessages((FermatMessage) event.getData());

        }

    }
}