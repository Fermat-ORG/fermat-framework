package com.bitdubai.fermat_art_api.layer.actor_connection.fan.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 01/05/16.
 */
public class ArtistConnectionRequestAcceptedEvent extends AbstractEvent {

    private String artistAcceptedPublicKey;

    public ArtistConnectionRequestAcceptedEvent(final FermatEventEnum eventType) {
        super(eventType);
    }

    public final String getArtistAcceptedPublicKey() {
        return artistAcceptedPublicKey;
    }

    public final void setArtistAcceptedPublicKey(String artistAcceptedPublicKey) {
        this.artistAcceptedPublicKey = artistAcceptedPublicKey;
    }
}