package com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.events.ChatConnectionRequestNewEvent;
import com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.ChatActorConnectionPluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.exceptions.ChatActorConnectionNotStartedException;
import com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.structure.ActorConnectionEventActions;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 06/04/16.
 */
public class ChatConnectionRequestNewsEventHandler implements FermatEventHandler<ChatConnectionRequestNewEvent> {
    private final ActorConnectionEventActions actorConnectionEventActions;
    private final ChatActorConnectionPluginRoot chatActorConnectionPluginRoot;

    public ChatConnectionRequestNewsEventHandler(final ActorConnectionEventActions actorConnectionEventActions,
                                                 final ChatActorConnectionPluginRoot chatActorConnectionPluginRoot) {

        this.actorConnectionEventActions = actorConnectionEventActions;
        this.chatActorConnectionPluginRoot = chatActorConnectionPluginRoot;
    }

    /**
     * FermatEventHandler interface implementation
     * <p/>
     * Plugin is started?
     * The event is the expected event?
     */
    @Override
    public void handleEvent(ChatConnectionRequestNewEvent fermatEvent) throws FermatException {

        if (this.chatActorConnectionPluginRoot.getStatus() == ServiceStatus.STARTED) {

            actorConnectionEventActions.handleNewsEvent();

        } else {
            throw new ChatActorConnectionNotStartedException("Plugin is not started.", "");
        }
    }
}
