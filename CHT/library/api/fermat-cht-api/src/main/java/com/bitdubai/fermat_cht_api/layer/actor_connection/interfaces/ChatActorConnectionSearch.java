package com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.database_abstract_classes.ActorConnectionDao;
import com.bitdubai.fermat_api.layer.actor_connection.common.interfaces.ActorIdentity;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes.ActorConnectionSearch;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatActorConnection;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 05/04/16.
 */
public abstract class ChatActorConnectionSearch extends ActorConnectionSearch<ChatActorConnection> {
    public ChatActorConnectionSearch(ActorIdentity actorIdentity, ActorConnectionDao<ChatActorConnection> dao) {
        super(actorIdentity, dao);
    }
}
