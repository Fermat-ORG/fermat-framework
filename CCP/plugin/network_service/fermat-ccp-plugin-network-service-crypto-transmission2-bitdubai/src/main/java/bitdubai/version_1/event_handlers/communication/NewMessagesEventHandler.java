package bitdubai.version_1.event_handlers.communication;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageReceivedNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

/**
 * Created by mati on 2016.01.14..
 */
public class NewMessagesEventHandler implements FermatEventHandler {

    private NetworkService intraActorNetworkServicePluginRoot;

    public NewMessagesEventHandler(NetworkService intraActorNetworkServicePluginRoot) {
        this.intraActorNetworkServicePluginRoot = intraActorNetworkServicePluginRoot;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (((AbstractPlugin)this.intraActorNetworkServicePluginRoot).getStatus() == ServiceStatus.STARTED) {

            NewNetworkServiceMessageReceivedNotificationEvent event = (NewNetworkServiceMessageReceivedNotificationEvent) fermatEvent;

            if(event.getNetworkServiceTypeApplicant() == intraActorNetworkServicePluginRoot.getNetworkServiceType()){

                intraActorNetworkServicePluginRoot.handleNewMessages((FermatMessage) event.getData());

            }


        }

    }
}
