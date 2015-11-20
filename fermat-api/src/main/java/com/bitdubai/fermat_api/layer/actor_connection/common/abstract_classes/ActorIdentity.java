package com.bitdubai.fermat_api.layer.actor_connection.common.abstract_classes;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import org.apache.commons.lang.Validate;

/**
 * The abstract class <code>com.bitdubai.fermat_api.layer.actor_connection.common.abstract_classes.ActorIdentity</code>
 * represents an actor identity.
 *
 * An Actor Identity contains all the information related with the identity which is connected with other actors.
 *
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/11/2015.
 */
public abstract class ActorIdentity {

    private final String publicKey;
    private final Actors actorType;

    public ActorIdentity(final String publicKey,
                         final Actors actorType) {

        Validate.notNull(publicKey, "The Public Key can't be null.");
        Validate.notNull(actorType, "The Actor Type can't be null.");

        this.publicKey = publicKey;
        this.actorType = actorType;
    }

    /**
     * @return a string representing the public key.
     */
    public final String getPublicKey() {
        return publicKey;
    }

    /**
     * @return an element of Actors enum representing the type of the actor identity.
     */
    public final Actors getActorType() {
        return actorType;
    }

}
