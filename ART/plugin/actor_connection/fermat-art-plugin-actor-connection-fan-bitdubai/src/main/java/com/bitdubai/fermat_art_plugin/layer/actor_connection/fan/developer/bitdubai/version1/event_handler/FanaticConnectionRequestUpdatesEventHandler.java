package com.bitdubai.fermat_art_plugin.layer.actor_connection.fan.developer.bitdubai.version1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_art_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.events.ArtistConnectionRequestUpdatesEvent;
import com.bitdubai.fermat_art_plugin.layer.actor_connection.fan.developer.bitdubai.version1.FanActorConnectionPluginRoot;
import com.bitdubai.fermat_art_plugin.layer.actor_connection.fan.developer.bitdubai.version1.exceptions.FanActorConnectionNotStartedException;
import com.bitdubai.fermat_art_plugin.layer.actor_connection.fan.developer.bitdubai.version1.structure.ActorConnectionEventActions;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/04/16.
 */
public class FanaticConnectionRequestUpdatesEventHandler implements FermatEventHandler {

    private final ActorConnectionEventActions actorConnectionEventActions          ;
    private final FanActorConnectionPluginRoot fanActorConnectionPluginRoot;

    /**
     * Default constructor with parameters.
     * @param actorConnectionEventActions
     * @param fanActorConnectionPluginRoot
     */
    public FanaticConnectionRequestUpdatesEventHandler(
            final ActorConnectionEventActions actorConnectionEventActions,
            final FanActorConnectionPluginRoot fanActorConnectionPluginRoot) {
        this.actorConnectionEventActions = actorConnectionEventActions            ;
        this.fanActorConnectionPluginRoot = fanActorConnectionPluginRoot;
    }

    /**
     * FermatEventHandler interface implementation
     *
     * Plugin is started?
     * The event is the expected event?
     */
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (this.fanActorConnectionPluginRoot.getStatus() == ServiceStatus.STARTED) {
            //To void useless exception
            switch(fermatEvent.getSource()){
                case ACTOR_NETWORK_SERVICE_FAN:
                    if (fermatEvent instanceof ArtistConnectionRequestUpdatesEvent) {
                        actorConnectionEventActions.handleUpdateEvent(fermatEvent.getSource());
                    } else {
                        EventType eventExpected = EventType.ARTIST_CONNECTION_REQUEST_UPDATES;
                        String context = "Event received: " + fermatEvent.getEventType().toString() + " - " + fermatEvent.getEventType().getCode()+"\n"+
                                "Event expected: " + eventExpected.toString()              + " - " + eventExpected.getCode();
                        throw new UnexpectedEventException(context);
                    }
                    break;
                case ACTOR_NETWORK_SERVICE_ARTIST:
                    break;
            }

        } else {
            throw new FanActorConnectionNotStartedException("Plugin is not started.");
        }
    }
}
