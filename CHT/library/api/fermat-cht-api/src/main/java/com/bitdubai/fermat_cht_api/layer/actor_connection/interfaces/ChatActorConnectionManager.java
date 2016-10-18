package com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantUpdateActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.interfaces.ActorConnectionManager;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatActorConnection;

import java.util.UUID;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 05/04/16.
 */
public interface ChatActorConnectionManager extends ActorConnectionManager<ChatActorConnection, ChatActorConnectionSearch> {

   /**
    * Through the method <code>updateAlias</code> we can update the alias of a connection.
    *
    * @param connectionId id of the actor connection to be changed.
    * @param alias        to change.
    *
    * @throws CantUpdateActorConnectionException        if something goes wrong.
    * @throws ActorConnectionNotFoundException          if we can't find an actor connection with this connection id.
    */
   void updateAlias(final UUID   connectionId,
                    final String alias       ) throws CantUpdateActorConnectionException,
                                                      ActorConnectionNotFoundException;

   /**
    * Through the method <code>updateAlias</code> we can update the image of a connection.
    *
    * @param connectionId id of the actor connection to be changed.
    * @param image       new image of the actor connection to be changed.
    *
    * @throws CantUpdateActorConnectionException        if something goes wrong.
    * @throws ActorConnectionNotFoundException          if we can't find an actor connection with this connection id.
    */
   void updateImage(final UUID   connectionId,
                    final byte[] image       ) throws CantUpdateActorConnectionException,
                                                      ActorConnectionNotFoundException;
}
