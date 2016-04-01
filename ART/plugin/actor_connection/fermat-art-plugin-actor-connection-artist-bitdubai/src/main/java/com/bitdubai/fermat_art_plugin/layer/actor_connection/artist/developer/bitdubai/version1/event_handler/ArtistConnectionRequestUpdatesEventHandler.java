package com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_art_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.events.ArtistConnectionRequestUpdatesEvent;
import com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version1.ArtistActorConnectionPluginRoot;
import com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version1.exceptions.ArtistActorConnectionNotStartedException;
import com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version1.structure.ActorConnectionEventsActions;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/03/16.
 */
public class ArtistConnectionRequestUpdatesEventHandler implements FermatEventHandler {

    private final ActorConnectionEventsActions actorConnectionEventActions          ;
    private final ArtistActorConnectionPluginRoot artistActorConnectionPluginRoot;

    /**
     * Constructor with parameters
     * @param actorConnectionEventActions
     * @param artistActorConnectionPluginRoot
     */
    public ArtistConnectionRequestUpdatesEventHandler(
            final ActorConnectionEventsActions actorConnectionEventActions,
            final ArtistActorConnectionPluginRoot artistActorConnectionPluginRoot) {

        this.actorConnectionEventActions = actorConnectionEventActions;
        this.artistActorConnectionPluginRoot = artistActorConnectionPluginRoot;
    }

    /**
     * FermatEventHandler interface implementation
     *
     * Plugin is started?
     * The event is the expected event?
     */
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        if (this.artistActorConnectionPluginRoot.getStatus() == ServiceStatus.STARTED) {
            if (fermatEvent instanceof ArtistConnectionRequestUpdatesEvent) {
                actorConnectionEventActions.handleArtistUpdateEvent();
            } else {
                EventType eventExpected = EventType.ARTIST_CONNECTION_REQUEST_UPDATES;
                String context = "Event received: " + fermatEvent.getEventType().toString() + " - " + fermatEvent.getEventType().getCode()+"\n"+
                        "Event expected: " + eventExpected.toString()              + " - " + eventExpected.getCode();
                throw new UnexpectedEventException(context);
            }
        } else {
            throw new ArtistActorConnectionNotStartedException("Plugin is not started.");
        }
    }
}
