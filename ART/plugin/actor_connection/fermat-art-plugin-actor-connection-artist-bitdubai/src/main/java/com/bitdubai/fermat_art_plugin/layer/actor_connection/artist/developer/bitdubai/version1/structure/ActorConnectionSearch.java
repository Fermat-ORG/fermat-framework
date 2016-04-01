package com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version1.structure;

import com.bitdubai.fermat_art_api.layer.actor_connection.artist.interfaces.ArtistActorConnectionSearch;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.utils.ArtistLinkedActorIdentity;
import com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version1.database.ArtistActorConnectionDao;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/03/16.
 */
public class ActorConnectionSearch extends ArtistActorConnectionSearch {

    /**
     * Constructor with parameters
     * @param actorIdentity
     * @param dao
     */
    public ActorConnectionSearch(final ArtistLinkedActorIdentity actorIdentity,
                                 final ArtistActorConnectionDao dao) {
        super(actorIdentity, dao);
    }
}
