package bitdubai.version_1.event_handlers.communication;

import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.interfaces.NetworkService;
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
    public NewReceiveMessagesNotificationEventHandler(NetworkService intraActorNetworkServicePluginRoot) {
        super(intraActorNetworkServicePluginRoot);
    }


    @Override
    public void processEvent(NewNetworkServiceMessageReceivedNotificationEvent event) {

            ns.handleNewMessages((FermatMessage)event.getData());
    }

}
