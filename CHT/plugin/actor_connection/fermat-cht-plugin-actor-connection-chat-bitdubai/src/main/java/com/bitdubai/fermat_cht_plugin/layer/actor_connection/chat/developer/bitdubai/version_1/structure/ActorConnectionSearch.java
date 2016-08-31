package com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.database_abstract_classes.ActorConnectionDao;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.actor_connection.common.interfaces.ActorIdentity;
import com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces.ChatActorConnectionSearch;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatActorConnection;

import java.util.List;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 06/04/16.
 */
public class ActorConnectionSearch extends ChatActorConnectionSearch {
    public ActorConnectionSearch(ActorIdentity actorIdentity, ActorConnectionDao<ChatActorConnection> dao) {
        super(actorIdentity, dao);
    }
}
