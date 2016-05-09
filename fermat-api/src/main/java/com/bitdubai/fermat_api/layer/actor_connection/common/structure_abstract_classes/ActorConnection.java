package com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;

import org.apache.commons.lang.Validate;

import java.util.UUID;

/**
 * The interface <code>ActorConnection</code>
 * represents a Connection with an Actor of the Fermat System.
 *
 * An Actor Connection contains all the basic information of the connection.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/11/2015.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class ActorConnection<T extends LinkedActorIdentity> {

    private final UUID            connectionId   ;
    private final T               linkedIdentity ;
    private final String          publicKey      ;
    private final String          alias          ;
    private final byte[]          image          ;
    private       ConnectionState connectionState;
    private final long            creationTime   ;
    private final long            updateTime     ;

    protected ActorConnection(final UUID            connectionId   ,
                              final T               linkedIdentity ,
                              final String          publicKey      ,
                              final String          alias          ,
                              final byte[]          image          ,
                              final ConnectionState connectionState,
                              final long            creationTime   ,
                              final long            updateTime     ) {

        Validate.notNull(connectionId   , "The Connection ID can't be null."   );
        Validate.notNull(linkedIdentity , "The Linked Identity can't be null." );
        Validate.notNull(publicKey      , "The Public Key can't be null."      );
        Validate.notNull(alias          , "The Alias can't be null."           );
        Validate.notNull(image          , "The Image can't be null."           );
        Validate.notNull(connectionState, "The Connection State can't be null.");
        Validate.notNull(creationTime   , "The Creation Time can't be null."   );

        this.connectionId    = connectionId   ;
        this.linkedIdentity  = linkedIdentity ;
        this.publicKey       = publicKey      ;
        this.alias           = alias          ;
        this.image           = image          ;
        this.connectionState = connectionState;
        this.creationTime    = creationTime   ;

        // if the update time is 0 we'll assign like last update time the creation time.
        this.updateTime = updateTime != 0 ? updateTime : creationTime;
    }

    /**
     * @return an UUID instance representing the ID of the connection.
     */
    public final UUID getConnectionId() {
        return connectionId;
    }

    /**
     * @return an LinkedActorIdentity instance representing the identity which is connected with the other actor.
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

    @Override
    public String toString() {
        return "ActorConnection{" +
                "connectionId=" + connectionId +
                ", linkedIdentity=" + linkedIdentity +
                ", publicKey='" + publicKey + '\'' +
                ", alias='" + alias + '\'' +
                ", image=" + (image != null) +
                ", connectionState=" + connectionState +
                ", creationTime=" + creationTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
