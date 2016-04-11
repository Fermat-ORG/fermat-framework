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
public class ArtistConnectionRequestUpdatesEventHandler implements FermatEventHandler {

    private final ActorConnectionEventActions actorConnectionEventActions          ;
    private final FanActorConnectionPluginRoot fanActorConnectionPluginRoot;

    /**
     * Default constructor with parameters.
     * @param actorConnectionEventActions
     * @param fanActorConnectionPluginRoot
     */
    public ArtistConnectionRequestUpdatesEventHandler(
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
            if (fermatEvent instanceof ArtistConnectionRequestUpdatesEvent) {
                actorConnectionEventActions.handleArtistUpdateEvent();
            } else {
                EventType eventExpected = EventType.ARTIST_CONNECTION_REQUEST_UPDATES;
                String context = "Event received: " + fermatEvent.getEventType().toString() + " - " + fermatEvent.getEventType().getCode()+"\n"+
                        "Event expected: " + eventExpected.toString()              + " - " + eventExpected.getCode();
                throw new UnexpectedEventException(context);
            }
        } else {
            throw new FanActorConnectionNotStartedException("Plugin is not started.");
        }
    }
}
