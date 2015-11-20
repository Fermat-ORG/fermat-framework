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
public abstract class ActorConnection<T extends ActorIdentity> {

    private final UUID            connectionId   ;
    private final T               linkedIdentity ;
    private final String          publicKey      ;
    private final Actors          actorType      ;
    private final String          alias          ;
    private final byte[]          image          ;
    private final ConnectionState connectionState;
    private final long            creationTime   ;
    private final long            updateTime     ;

    public ActorConnection(final UUID            connectionId   ,
                           final T               linkedIdentity ,
                           final String          publicKey      ,
                           final Actors          actorType      ,
                           final String          alias          ,
                           final byte[]          image          ,
                           final ConnectionState connectionState,
                           final long            creationTime   ,
                           final long            updateTime     ) {

        this.connectionId    = connectionId   ;
        this.linkedIdentity  = linkedIdentity ;
        this.publicKey       = publicKey      ;
        this.actorType       = actorType      ;
        this.alias           = alias          ;
        this.image           = image          ;
        this.connectionState = connectionState;
        this.creationTime    = creationTime   ;
        this.updateTime      = updateTime     ;
    }

    /**
     * @return an UUID instance representing the ID of the connection.
     */
    public final UUID getConnectionId() {
        return connectionId;
    }

    /**
     * @return an ActorIdentity instance representing the identity which is connected with the other actor.
     */
    public final T getLinkedIdentity() {
        return linkedIdentity;
    }

    /**
     * @return a string representing the public key.
     */
    public final String getPublicKey() {
        return publicKey;
    }

    /**
     * @return an element of Actors enum representing the type of the actor.
     */
    public final Actors getActorType() {
        return actorType;
    }

    /**
     * @return a string representing the alias exposed by the actor.
     */
    public final String getAlias() {
        return alias;
    }

    /**
     * @return an array of bytes with the image exposed by the actor.
     */
    public final byte[] getImage() {
        return image;
    }

    /**
     * @return an element of ConnectionState enum representing the state of the connection.
     */
    public final ConnectionState getConnectionState() {
        return connectionState;
    }

    /**
     * @return a long indicating the creation time of the connection.
     */
    public final long getCreationTime() {
        return creationTime;
    }

    /**
     * @return a long indicating the last update time of the connection.
     */
    public final long getUpdateTime() {
        return updateTime;
    }
}
