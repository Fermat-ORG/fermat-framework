package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.CryptoAddressesNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageSentNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

/**
 * The event handler <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.event_handlers.NewReceiveMessagesNotificationEventHandler</code>
 * handles the event of type new receive messages notifications for the network service crypto addresses..
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/10/2015.
 */
public class NewReceiveMessagesNotificationEventHandler implements FermatEventHandler {

    private final CryptoAddressesNetworkServicePluginRoot cryptoAddressesNetworkServicePluginRoot;

    public NewReceiveMessagesNotificationEventHandler(final CryptoAddressesNetworkServicePluginRoot cryptoAddressesNetworkServicePluginRoot) {

        this.cryptoAddressesNetworkServicePluginRoot = cryptoAddressesNetworkServicePluginRoot;
    }

    @Override
    public void handleEvent(FermatEvent platformEvent) throws FermatException {

        System.out.println("CompleteComponentConnectionRequestNotificationEventHandler - handleEvent platformEvent =" + platformEvent);


        if (platformEvent instanceof NewNetworkServiceMessageSentNotificationEvent) {

            NewNetworkServiceMessageSentNotificationEvent newNetworkServiceMessageSentNotificationEvent = (NewNetworkServiceMessageSentNotificationEvent) platformEvent;

            cryptoAddressesNetworkServicePluginRoot.handleNewMessages((FermatMessage) newNetworkServiceMessageSentNotificationEvent.getData());

        }
    }
}
