package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.structures;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentRegistrationNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteUpdateActorNotificationEvent;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.SubAppFermatMonitorNetworkServicePluginRoot;

/**
 * Created by mati on 2016.03.31..
 */
public class EventHandlerRouter implements FermatEventHandler<FermatEvent<P2pEventType>> {

    private final SubAppFermatMonitorNetworkServicePluginRoot subAppFermatMonitorNetworkServicePluginRoot;

    public EventHandlerRouter(SubAppFermatMonitorNetworkServicePluginRoot subAppFermatMonitorNetworkServicePluginRoot) {
        this.subAppFermatMonitorNetworkServicePluginRoot = subAppFermatMonitorNetworkServicePluginRoot;
    }


    @Override
    public void handleEvent(FermatEvent<P2pEventType> fermatEvent) throws FermatException {
        switch (fermatEvent.getEventType()) {

            case COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION:

                break;
            case COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION:
                CompleteComponentRegistrationNotificationEvent completeComponentRegistrationNotificationEvent = (CompleteComponentRegistrationNotificationEvent) fermatEvent;
                subAppFermatMonitorNetworkServicePluginRoot.saveComponentRegistration(completeComponentRegistrationNotificationEvent);
                break;
            case COMPLETE_UPDATE_ACTOR_NOTIFICATION:
                CompleteUpdateActorNotificationEvent completeUpdateActorNotificationEvent = (CompleteUpdateActorNotificationEvent) fermatEvent;
                subAppFermatMonitorNetworkServicePluginRoot.updateActor(fermatEvent);
                break;

        }
    }
}
