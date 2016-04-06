package com.bitdubai.fermat_cht_api.layer.actor_connection.utils;

import com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes.LinkedActorIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 05/04/16.
 */
public class ChatLinkedActorIdentity extends LinkedActorIdentity {
    public ChatLinkedActorIdentity(String publicKey, Actors actorType) {
        super(publicKey, actorType);
    }
}
