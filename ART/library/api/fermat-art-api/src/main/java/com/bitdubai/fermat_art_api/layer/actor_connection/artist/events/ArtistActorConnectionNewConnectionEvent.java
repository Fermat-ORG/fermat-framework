package com.bitdubai.fermat_art_api.layer.actor_connection.artist.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/03/16.
 */
public class ArtistActorConnectionNewConnectionEvent extends AbstractEvent {

    /**
     * Constructor with parameters
     * @param eventType
     */
    public ArtistActorConnectionNewConnectionEvent(
            final FermatEventEnum eventType) {
        super(eventType);
    }
}