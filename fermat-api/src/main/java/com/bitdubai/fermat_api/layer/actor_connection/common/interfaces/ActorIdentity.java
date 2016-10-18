package com.bitdubai.fermat_api.layer.actor_connection.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import java.io.Serializable;

public interface ActorIdentity extends Serializable {

    String getPublicKey();

    Actors getActorType();

}
