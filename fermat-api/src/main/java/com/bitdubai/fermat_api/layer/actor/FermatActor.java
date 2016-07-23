package com.bitdubai.fermat_api.layer.actor;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

/**
 * Mandatory methods that all Fermat Actors must include.
 * Useful for low level components that may interact with any type of existing Actor.
 */
public interface FermatActor {
    /**
     * Get the actor public Key
     *
     * @return a String representing the public Key of the Actor.
     */
    String getPublicKey();

    /**
     * Gets the name of the Actor
     *
     * @return a String representing the given name for this actor.
     */
    String getName();

    /**
     * The Actor type this actor represents.
     *
     * @return An existing fermat actor type
     */
    Actors getType();

}
