package com.bitdubai.fermat_art_api.layer.actor_connection.fan.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.interfaces.ActorConnectionManager;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.utils.FanActorConnection;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.utils.FanLinkedActorIdentity;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/03/16.
 */
public interface FanActorConnectionManager extends
        ActorConnectionManager<
                FanLinkedActorIdentity,
                FanActorConnection,
                FanActorConnectionSearch> {

}
