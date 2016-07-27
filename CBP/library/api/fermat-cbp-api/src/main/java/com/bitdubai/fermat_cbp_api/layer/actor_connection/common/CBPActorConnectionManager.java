package com.bitdubai.fermat_cbp_api.layer.actor_connection.common;

import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnsupportedActorTypeException;
import com.bitdubai.fermat_api.layer.actor_connection.common.interfaces.ActorConnectionManager;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes.ActorConnectionSearch;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes.LinkedActorIdentity;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_common_classes.ActorIdentityInformation;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/07/16.
 */
public interface CBPActorConnectionManager
        <T extends LinkedActorIdentity, Z extends CBPActorConnection<T>, M extends ActorConnectionSearch<T, Z>>
        extends ActorConnectionManager<T, Z, M> {

    /**
     * This method creates a request connection with location in the argument
     *
     * @param actorSending
     * @param actorReceiving
     * @param receivingLocation
     * @throws CantRequestActorConnectionException
     * @throws UnsupportedActorTypeException
     * @throws ConnectionAlreadyRequestedException
     */
    void requestConnection(
            final ActorIdentityInformation actorSending,
            final ActorIdentityInformation actorReceiving,
            final Location receivingLocation)
            throws CantRequestActorConnectionException,
            UnsupportedActorTypeException,
            ConnectionAlreadyRequestedException;

    /**
     * This method persist the location within an actor connection
     *
     * @param actorConnection
     * @throws CantUpdateRecordException
     */
    void persistLocation(Z actorConnection) throws CantUpdateRecordException;

}
