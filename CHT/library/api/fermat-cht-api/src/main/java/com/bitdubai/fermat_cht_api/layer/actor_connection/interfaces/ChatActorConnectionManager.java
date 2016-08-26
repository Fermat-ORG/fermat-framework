package com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.interfaces.ActorConnectionManager;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatActorConnection;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 05/04/16.
 */
public interface ChatActorConnectionManager extends ActorConnectionManager<ChatActorConnection, ChatActorConnectionSearch> {

   void updateActorConnection(ChatActorConnection chatActorConnection);
}
