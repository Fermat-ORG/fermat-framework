package com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_art_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.events.ArtistConnectionRequestNewsEvent;
import com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version_1.ArtistActorConnectionPluginRoot;
import com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version_1.exceptions.ArtistActorConnectionNotStartedException;
import com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version_1.structure.ActorConnectionEventsActions;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/03/16.
 */
public class ArtistConnectionRequestNewsEventHandler implements FermatEventHandler {

    private final ActorConnectionEventsActions actorConnectionEventActions          ;
    private final ArtistActorConnectionPluginRoot artistActorConnectionPluginRoot;

    /**
     * Constructor with parameters.
     * @param actorConnectionEventActions
     * @param artistActorConnectionPluginRoot
     */
    public ArtistConnectionRequestNewsEventHandler(
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
        EventSource eventSource;
        if (this.artistActorConnectionPluginRoot.getStatus() == ServiceStatus.STARTED) {
            if (fermatEvent instanceof ArtistConnectionRequestNewsEvent) {
                eventSource = fermatEvent.getSource();
                switch (eventSource){
                    case ACTOR_NETWORK_SERVICE_ARTIST:
                        actorConnectionEventActions.handleNewsEvent();
                        break;
                    case ACTOR_NETWORK_SERVICE_FAN:
                        //No action required
                        break;
                    default:
                        throw new FermatException(
                                "Unexpected Event source when trying to handling " +
                                        "ArtistConnectionRequestNewsEvent",
                                null,
                                "ArtistConnectionRequestNewsEventHandler",
                                "Unexpected event source: "+eventSource);
                }

            } else {
                EventType eventExpected = EventType.ARTIST_CONNECTION_REQUEST_NEWS;
                String context = "Event received: " + fermatEvent.getEventType().toString() + " - " + fermatEvent.getEventType().getCode()+"\n"+
                        "Event expected: " + eventExpected.toString()              + " - " + eventExpected.getCode();
                throw new UnexpectedEventException(context);
            }
        } else {
            throw new ArtistActorConnectionNotStartedException("Plugin is not started.");
        }
    }
}