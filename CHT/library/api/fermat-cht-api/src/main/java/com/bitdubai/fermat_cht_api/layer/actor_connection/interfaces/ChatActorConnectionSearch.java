package com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.database_abstract_classes.ActorConnectionDao;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes.ActorConnectionSearch;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatActorConnection;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatLinkedActorIdentity;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 05/04/16.
 */
public abstract class ChatActorConnectionSearch extends ActorConnectionSearch<ChatLinkedActorIdentity, ChatActorConnection> {
    public ChatActorConnectionSearch(ChatLinkedActorIdentity actorIdentity, ActorConnectionDao<ChatLinkedActorIdentity, ChatActorConnection> dao) {
        super(actorIdentity, dao);
    }
}
