package com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.FanActorNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentRegistrationNotificationEvent;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 11/05/16.
 */
public class FanCustomeP2PCompletedConnectionRegistrationEventHandler implements FermatEventHandler<CompleteComponentRegistrationNotificationEvent> {

    private FanActorNetworkServicePluginRoot fanActorNetworkServicePluginRoot;

    public FanCustomeP2PCompletedConnectionRegistrationEventHandler(FanActorNetworkServicePluginRoot fanActorNetworkServicePluginRoot) {
        this.fanActorNetworkServicePluginRoot = fanActorNetworkServicePluginRoot;
    }

    @Override
    public void handleEvent(CompleteComponentRegistrationNotificationEvent completeComponentRegistrationNotificationEvent) throws FermatException {
        if(fanActorNetworkServicePluginRoot.getStatus() == ServiceStatus.STARTED){
            if(fanActorNetworkServicePluginRoot.getProfile() != null){
                if(completeComponentRegistrationNotificationEvent != null){
                    if(completeComponentRegistrationNotificationEvent.getPlatformComponentProfileRegistered().getPlatformComponentType() == PlatformComponentType.COMMUNICATION_CLOUD_CLIENT && completeComponentRegistrationNotificationEvent.getSource() == EventSource.WS_COMMUNICATION_CLOUD_CLIENT_PLUGIN){
                        //fanActorNetworkServicePluginRoot.runExposeIdentityThread();
                    }
                }else{
                    System.out.println("######################\nwrong event");
                }
            }else{
                System.out.println("#####################################\nNetwork Service Profile Null");
            }
        }else{
            System.out.println("####################################\n"+ fanActorNetworkServicePluginRoot.getProfile().getNetworkServiceType()+" no started");
        }
    }
}
