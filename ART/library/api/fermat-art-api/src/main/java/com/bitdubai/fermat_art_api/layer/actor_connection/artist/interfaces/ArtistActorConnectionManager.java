package com.bitdubai.fermat_art_api.layer.actor_connection.artist.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.interfaces.ActorConnectionManager;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.utils.ArtistActorConnection;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.utils.ArtistLinkedActorIdentity;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/03/16.
 */
public interface ArtistActorConnectionManager extends
        ActorConnectionManager<
                ArtistLinkedActorIdentity,
                ArtistActorConnection,
                ArtistActorConnectionSearch> {

    /**
     * This interface can include new methods to implement, in this version, this interface has
     * the same method than the parent interface.
     */

}
