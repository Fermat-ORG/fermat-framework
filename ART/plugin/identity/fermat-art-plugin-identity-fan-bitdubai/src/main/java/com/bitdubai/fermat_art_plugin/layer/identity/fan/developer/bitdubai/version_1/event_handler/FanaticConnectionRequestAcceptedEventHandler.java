package com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_art_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.events.ArtistConnectionRequestAcceptedEvent;
import com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.FanaticPluginRoot;
import com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.exceptions.FanIdentityNotStartedException;
import com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.structure.FanIdentityEventActions;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 01/05/16.
 */
public class FanaticConnectionRequestAcceptedEventHandler implements FermatEventHandler {

    private final FanIdentityEventActions fanIdentityEventActions;
    private final FanaticPluginRoot fanaticPluginRoot;

    /**
     * Default constructor with parameters.
     * @param fanIdentityEventActions
     * @param fanaticPluginRoot
     */
    public FanaticConnectionRequestAcceptedEventHandler(
            final FanIdentityEventActions fanIdentityEventActions,
            final FanaticPluginRoot fanaticPluginRoot) {
        this.fanIdentityEventActions = fanIdentityEventActions;
        this.fanaticPluginRoot = fanaticPluginRoot;
    }

    /**
     * FermatEventHandler interface implementation
     *
     * Plugin is started?
     * The event is the expected event?
     */
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (this.fanaticPluginRoot.getStatus() == ServiceStatus.STARTED) {
            if (fermatEvent instanceof ArtistConnectionRequestAcceptedEvent) {
                ArtistConnectionRequestAcceptedEvent event =
                        (ArtistConnectionRequestAcceptedEvent) fermatEvent;
                fanIdentityEventActions.handleArtistRequestConnectionAccepted(
                        event.getArtistAcceptedPublicKey());
            } else{
                EventType eventExpected = EventType.ARTIST_CONNECTION_REQUEST_ACCEPTED_EVENT;
                String context = "Event received: " + fermatEvent.getEventType().toString() + " - " + fermatEvent.getEventType().getCode()+"\n"+
                        "Event expected: " + eventExpected.toString()              + " - " + eventExpected.getCode();
                throw new UnexpectedEventException(context);
            }
        } else {
            throw new FanIdentityNotStartedException("Plugin is not started.");
        }
    }
}
