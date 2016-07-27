package com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.events.ChatConnectionRequestUpdatesEvent;
import com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.ChatActorConnectionPluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.exceptions.ChatActorConnectionNotStartedException;
import com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.structure.ActorConnectionEventActions;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 06/04/16.
 */
public class ChatConnectionRequestUpdatesEventHandler implements FermatEventHandler {
    private final ActorConnectionEventActions actorConnectionEventActions;
    private final ChatActorConnectionPluginRoot chatActorConnectionPluginRoot;

    public ChatConnectionRequestUpdatesEventHandler(final ActorConnectionEventActions actorConnectionEventActions,
                                                    final ChatActorConnectionPluginRoot cryptoBrokerActorConnectionPluginRoot) {

        this.actorConnectionEventActions = actorConnectionEventActions;
        this.chatActorConnectionPluginRoot = cryptoBrokerActorConnectionPluginRoot;
    }

    /**
     * FermatEventHandler interface implementation
     * <p/>
     * Plugin is started?
     * The event is the expected event?
     */
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        if (this.chatActorConnectionPluginRoot.getStatus() == ServiceStatus.STARTED) {

            if (fermatEvent instanceof ChatConnectionRequestUpdatesEvent) {

                actorConnectionEventActions.handleUpdateEvent();

            } else {
                EventType eventExpected = EventType.CHAT_ACTOR_CONNECTION_UPDATE_CONNECTION;
                String context = new StringBuilder().append("Event received: ").append(fermatEvent.getEventType().toString()).append(" - ").append(fermatEvent.getEventType().getCode()).append("\n").append("Event expected: ").append(eventExpected.toString()).append(" - ").append(eventExpected.getCode()).toString();
                throw new UnexpectedEventException(context);
            }
        } else {
            throw new ChatActorConnectionNotStartedException("Plugin is not started.", "");
        }
    }
}
