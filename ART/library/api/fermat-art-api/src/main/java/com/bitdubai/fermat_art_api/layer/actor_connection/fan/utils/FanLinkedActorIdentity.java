package com.bitdubai.fermat_art_api.layer.actor_connection.fan.utils;

import com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes.LinkedActorIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import java.io.Serializable;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/03/16.
 */
public final class FanLinkedActorIdentity extends LinkedActorIdentity implements Serializable {

    /**
     * Constructor with parameters
     * @param publicKey
     * @param actorType
     */
    public FanLinkedActorIdentity(
            final String publicKey,
            final Actors actorType) {
        super(
                publicKey,
                actorType);
    }

}