package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers.communication;

import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.IntraActorNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractCommunicationBaseEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageReceivedNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

/**
 * Created by mati on 2015.10.09..
 */
public class NewReceiveMessagesNotificationEventHandler extends AbstractCommunicationBaseEventHandler<NewNetworkServiceMessageReceivedNotificationEvent> {

    /**
     * Constructor with parameter
     *
     * @param
     */
    public NewReceiveMessagesNotificationEventHandler(IntraActorNetworkServicePluginRoot intraActorNetworkServicePluginRoot) {
        super(intraActorNetworkServicePluginRoot);
    }


    @Override
    public void processEvent(NewNetworkServiceMessageReceivedNotificationEvent event) {

        System.out.println("CompleteComponentConnectionRequestNotificationEventHandler - handleEvent platformEvent =" + event.toString());

        System.out.print("NOTIFICACION EVENTO LLEGADA MENSAJE A INTRA ACTOR NETWORK SERVICE!!!!");

        NewNetworkServiceMessageReceivedNotificationEvent newNetworkServiceMessageSentNotificationEvent = (NewNetworkServiceMessageReceivedNotificationEvent) event;

        ((IntraActorNetworkServicePluginRoot)networkService).handleNewMessages((FermatMessage) newNetworkServiceMessageSentNotificationEvent.getData());
    }

}
