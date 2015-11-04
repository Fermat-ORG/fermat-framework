package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communication;

import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.CryptoTransmissionNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractCommunicationBaseEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageReceivedNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

/**
 * Created by Matias Furszyfer on 2015.10.09..
 */
public class NewReceiveMessagesNotificationEventHandler extends AbstractCommunicationBaseEventHandler<NewNetworkServiceMessageReceivedNotificationEvent> {

    /**
     * Constructor with parameter
     *
     * @param
     */
    public NewReceiveMessagesNotificationEventHandler(CryptoTransmissionNetworkServicePluginRoot cryptoTransmissionNetworkServicePluginRoot) {
        super(cryptoTransmissionNetworkServicePluginRoot);
    }


    @Override
    public void processEvent(NewNetworkServiceMessageReceivedNotificationEvent event) {

        System.out.println("CompleteComponentConnectionRequestNotificationEventHandler - handleEvent platformEvent =" + event.toString());

        System.out.print("NOTIFICACION EVENTO LLEGADA MENSAJE A CRYPTO TRANSMISSION NETWORK SERVICE!!!!");

        ((CryptoTransmissionNetworkServicePluginRoot)networkService).handleNewMessages((FermatMessage) event.getData());
    }

}
