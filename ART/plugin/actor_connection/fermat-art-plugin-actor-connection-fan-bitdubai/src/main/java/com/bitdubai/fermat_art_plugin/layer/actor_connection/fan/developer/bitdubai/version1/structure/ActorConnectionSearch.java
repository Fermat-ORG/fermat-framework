package com.bitdubai.fermat_art_plugin.layer.actor_connection.fan.developer.bitdubai.version1.structure;

import com.bitdubai.fermat_art_api.layer.actor_connection.fan.interfaces.FanActorConnectionSearch;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.utils.FanLinkedActorIdentity;
import com.bitdubai.fermat_art_plugin.layer.actor_connection.fan.developer.bitdubai.version1.database.FanActorConnectionDao;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/04/16.
 */
public class ActorConnectionSearch extends FanActorConnectionSearch {

    /**
     * Default constructor with parameters.
     * @param actorIdentity
     * @param dao
     */
    public ActorConnectionSearch(
            final FanLinkedActorIdentity actorIdentity,
            final FanActorConnectionDao dao) {
        super(actorIdentity, dao);
    }
}
