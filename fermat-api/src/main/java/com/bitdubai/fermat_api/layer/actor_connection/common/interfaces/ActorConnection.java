package com.bitdubai.fermat_api.layer.actor_connection.common.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.abstract_classes.ActorIdentity;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.actor_connection.common.interfaces.ActorConnection</code>
 * represents a Connection with an Actor of the Fermat System.
 *
 * An Actor Connection contains all the basic information of the connection.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/11/2015.
 */
public interface ActorConnection {

    /**
     * @return an UUID instance representing the ID of the connection.
     */
    UUID getConnectionId();

    /**
     * @return an ActorIdentity instance representing the identity which is connected with the other actor.
     */
    ActorIdentity getLinkedIdentity();

    /**
     * @return a string representing the public key.
     */
    String getPublicKey();

    /**
     * @return an element of Actors enum representing the type of the actor.
     */
    Actors getActorType();

    /**
     * @return a string representing the alias exposed by the actor.
     */
    String getAlias();

    /**
     * @return an array of bytes with the image exposed by the actor.
     */
    byte[] getImage();

    /**
     * @return an element of ConnectionState enum representing the state of the connection.
     */
    ConnectionState getConnectionState();

}
