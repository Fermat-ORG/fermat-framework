package com.bitdubai.fermat_art_api.layer.actor_connection.fan.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.interfaces.ActorConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.utils.FanActorConnection;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.utils.FanLinkedActorIdentity;

import java.util.List;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/03/16.
 */
public interface FanActorConnectionManager extends
        ActorConnectionManager<
                FanLinkedActorIdentity,
                FanActorConnection,
                FanActorConnectionSearch> {

        /**
         * This method checks if an actor connection exists.
         * @param linkedIdentityPublicKey
         * @param linkedIdentityActorType
         * @param actorPublicKey
         * @return
         * @throws CantGetActorConnectionException
         */
        List<FanActorConnection> getRequestActorConnections(
                String linkedIdentityPublicKey,
                Actors linkedIdentityActorType,
                String actorPublicKey) throws CantGetActorConnectionException;

}
