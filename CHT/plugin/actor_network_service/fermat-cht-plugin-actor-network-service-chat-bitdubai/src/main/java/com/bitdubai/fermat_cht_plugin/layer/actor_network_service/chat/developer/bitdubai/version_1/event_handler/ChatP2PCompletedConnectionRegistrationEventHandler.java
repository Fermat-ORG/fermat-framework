package com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.ChatActorNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentRegistrationNotificationEvent;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 16/05/16.
 */
public class ChatP2PCompletedConnectionRegistrationEventHandler  implements FermatEventHandler<CompleteComponentRegistrationNotificationEvent> {

    private ChatActorNetworkServicePluginRoot chatActorNetworkServicePluginRoot;

    public ChatP2PCompletedConnectionRegistrationEventHandler(ChatActorNetworkServicePluginRoot chatActorNetworkServicePluginRoot) {

        this.chatActorNetworkServicePluginRoot = chatActorNetworkServicePluginRoot;

    }

    @Override
    public void handleEvent(CompleteComponentRegistrationNotificationEvent completeComponentRegistrationNotificationEvent) throws FermatException {

        if(chatActorNetworkServicePluginRoot.getStatus() == ServiceStatus.STARTED){

            if(chatActorNetworkServicePluginRoot.getNetworkServiceProfile() != null){

                if(completeComponentRegistrationNotificationEvent != null){

                    if(completeComponentRegistrationNotificationEvent.getPlatformComponentProfileRegistered().getPlatformComponentType() == PlatformComponentType.COMMUNICATION_CLOUD_CLIENT && completeComponentRegistrationNotificationEvent.getSource() == EventSource.WS_COMMUNICATION_CLOUD_CLIENT_PLUGIN){

                        chatActorNetworkServicePluginRoot.runExposeIdentityThread();

                    }

                }else{

                    System.out.println("######################\nwrong event");

                }

            }else{

                System.out.println("#####################################\nNetwork Service Profile Null");

            }

        }else{

            System.out.println("####################################\n"+ chatActorNetworkServicePluginRoot.getNetworkServiceProfile().getName()+" no started");

        }

    }
}
