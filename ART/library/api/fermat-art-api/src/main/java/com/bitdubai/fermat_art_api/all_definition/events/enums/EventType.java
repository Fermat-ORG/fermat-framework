package com.bitdubai.fermat_art_api.all_definition.events.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.events.ArtistActorConnectionNewConnectionEvent;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.events.ArtistConnectionRequestAcceptedEvent;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.events.ArtistConnectionRequestNewsEvent;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.events.ArtistConnectionRequestUpdatesEvent;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.events.FanConnectionRequestNewsEvent;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.events.FanConnectionRequestUpdatesEvent;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 31/03/16.
 */
public enum EventType implements FermatEventEnum {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */
    ARTIST_ACTOR_CONNECTION_NEW_CONNECTION_EVENT("AACNCE"){
        public final FermatEvent getNewEvent() {return new ArtistActorConnectionNewConnectionEvent(this);}
    },
    ARTIST_CONNECTION_REQUEST_ACCEPTED_EVENT("ACRAE"){
      public final FermatEvent getNewEvent() {return new ArtistConnectionRequestAcceptedEvent(this);}
    },
    ARTIST_CONNECTION_REQUEST_NEWS("ARCRN"){
        public final FermatEvent getNewEvent() { return new ArtistConnectionRequestNewsEvent(this); }
    },
    ARTIST_CONNECTION_REQUEST_UPDATES("ACRU"){
        public final FermatEvent getNewEvent(){ return new ArtistConnectionRequestUpdatesEvent(this);}
    },
    FAN_CONNECTION_REQUEST_NEWS("FCRN"){
        public final FermatEvent getNewEvent(){ return new FanConnectionRequestNewsEvent(this);}
    },
    FAN_CONNECTION_REQUEST_UPDATES("FCRU"){
        public final FermatEvent getNewEvent(){ return new FanConnectionRequestUpdatesEvent(this);}
    }
    ;

    private final String code;

    EventType(String code) {
        this.code = code;
    }

    @Override // by default
    public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) {
        return new GenericEventListener(this, fermatEventMonitor); }

    @Override
    public final String getCode() {
        return this.code;
    }

    @Override
    public final Platforms getPlatform() {
        return Platforms.ART_PLATFORM;
    }

}
