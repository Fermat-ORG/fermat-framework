package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.CryptoPaymentRequestNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageSentNotificationEvent;

/**
 * The event handler <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.event_handlers.NewReceiveMessagesNotificationEventHandler</code>
 * handles the event of type new receive messages notifications for the network service crypto payment request..
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 12/10/2015.
 */
public class NewReceiveMessagesNotificationEventHandler implements FermatEventHandler {

    private final CryptoPaymentRequestNetworkServicePluginRoot cryptoPaymentRequestNetworkServicePluginRoot;

    public NewReceiveMessagesNotificationEventHandler(final CryptoPaymentRequestNetworkServicePluginRoot cryptoPaymentRequestNetworkServicePluginRoot) {

        this.cryptoPaymentRequestNetworkServicePluginRoot = cryptoPaymentRequestNetworkServicePluginRoot;
    }

    @Override
    public void handleEvent(FermatEvent platformEvent) throws FermatException {

        System.out.println("CompleteComponentConnectionRequestNotificationEventHandler - handleEvent platformEvent =" + platformEvent);


        if (platformEvent instanceof NewNetworkServiceMessageSentNotificationEvent) {

            NewNetworkServiceMessageSentNotificationEvent newNetworkServiceMessageSentNotificationEvent = (NewNetworkServiceMessageSentNotificationEvent) platformEvent;

            cryptoPaymentRequestNetworkServicePluginRoot.handleNewMessages((FermatMessage) newNetworkServiceMessageSentNotificationEvent.getData());

        }
    }
}
