package com.bitdubai.fermat_cht_api.layer.actor_connection.utils;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes.ActorConnection;

import java.util.UUID;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 05/04/16.
 */
public final class ChatActorConnection extends ActorConnection<ChatLinkedActorIdentity> {
    public ChatActorConnection(UUID connectionId, ChatLinkedActorIdentity linkedIdentity, String publicKey, String alias, byte[] image, ConnectionState connectionState, long creationTime, long updateTime) {
        super(connectionId, linkedIdentity, publicKey, alias, image, connectionState, creationTime, updateTime);
    }
}
