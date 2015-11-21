package com.bitdubai.fermat_cbp_plugin.layer.network_service.crypto_broker.developer.bitdubai.version_1.communication.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.crypto_broker.developer.bitdubai.version_1.CryptoBrokerActorNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractNewReceiveMessagesNotificationEventHandler;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

/**
 * Created by lnacosta (laion.cj91@gmail.com) on 20/11/2015.
 */
public final class NewReceiveMessagesNotificationEventHandler extends AbstractNewReceiveMessagesNotificationEventHandler {

    public NewReceiveMessagesNotificationEventHandler(AbstractNetworkService cryptoAddressesNetworkServicePluginRoot) {
        super(cryptoAddressesNetworkServicePluginRoot);
    }

    @Override
    protected final void handleNewMessages(FermatMessage message) throws FermatException {
        ((CryptoBrokerActorNetworkServicePluginRoot)networkService).handleNewMessages(message);
    }
}
