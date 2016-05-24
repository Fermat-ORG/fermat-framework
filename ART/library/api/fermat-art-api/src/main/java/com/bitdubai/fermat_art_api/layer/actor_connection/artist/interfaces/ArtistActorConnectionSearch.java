package com.bitdubai.fermat_art_api.layer.actor_connection.artist.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.database_abstract_classes.ActorConnectionDao;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes.ActorConnectionSearch;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.utils.ArtistActorConnection;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.utils.ArtistLinkedActorIdentity;

import java.io.Serializable;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/03/16.
 */
public abstract class ArtistActorConnectionSearch extends
        ActorConnectionSearch<
                ArtistLinkedActorIdentity,
                ArtistActorConnection>
        implements Serializable {

    /**
     * Constructor with parameters.
     * @param actorIdentity
     * @param dao
     */
    public ArtistActorConnectionSearch(
            final ArtistLinkedActorIdentity actorIdentity,
            final ActorConnectionDao<
                    ArtistLinkedActorIdentity,
                    ArtistActorConnection> dao) {
        super(
                actorIdentity,
                dao);
    }

}
