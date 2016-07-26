package com.bitdubai.fermat_cbp_api.layer.actor_connection.common;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes.ActorConnection;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes.LinkedActorIdentity;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/07/16.
 */
public abstract class CBPActorConnection<T extends LinkedActorIdentity> extends ActorConnection<T> {

    /**
     * Represents the actor last known connection
     */
    private final Location location;

    /**
     * Default constructor with parameters
     *
     * @param connectionId
     * @param linkedIdentity
     * @param publicKey
     * @param alias
     * @param image
     * @param connectionState
     * @param creationTime
     * @param updateTime
     * @param location
     */
    protected CBPActorConnection(
            UUID connectionId,
            T linkedIdentity,
            String publicKey,
            String alias,
            byte[] image,
            ConnectionState connectionState,
            long creationTime,
            long updateTime,
            Location location) {
        super(
                connectionId,
                linkedIdentity,
                publicKey,
                alias,
                image,
                connectionState,
                creationTime,
                updateTime);
        this.location = location;
    }

    /**
     * This method returns the ActorConnection Location
     *
     * @return
     */
    public Location getLocation() {
        return location;
    }
}
