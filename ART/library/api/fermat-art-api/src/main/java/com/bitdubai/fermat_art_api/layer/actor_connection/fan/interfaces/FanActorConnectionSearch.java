package com.bitdubai.fermat_art_api.layer.actor_connection.fan.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.database_abstract_classes.ActorConnectionDao;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes.ActorConnectionSearch;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.utils.FanActorConnection;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.utils.FanLinkedActorIdentity;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/03/16.
 */
public abstract class FanActorConnectionSearch extends
        ActorConnectionSearch<
                FanLinkedActorIdentity,
                FanActorConnection> {

    /**
     * Constructor with parameters.
     * @param actorIdentity
     * @param dao
     */
    public FanActorConnectionSearch(
            final FanLinkedActorIdentity actorIdentity,
            final ActorConnectionDao<
                    FanLinkedActorIdentity,
                    FanActorConnection> dao) {

        super(actorIdentity, dao);
    }

}
