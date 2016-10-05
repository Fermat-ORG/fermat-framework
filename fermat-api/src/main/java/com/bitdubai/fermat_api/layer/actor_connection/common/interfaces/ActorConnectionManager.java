package com.bitdubai.fermat_api.layer.actor_connection.common.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantAcceptActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantCancelActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDisconnectFromActorException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ConnectionAlreadyRequestedException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnexpectedConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnsupportedActorTypeException;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes.ActorConnection;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes.ActorConnectionSearch;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes.LinkedActorIdentity;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_common_classes.ActorIdentityInformation;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.actor_connection.common.interfaces.ActorConnectionManager</code>
 * represents all the basic functionality of an actor connection plug-in.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/11/2015.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface ActorConnectionManager<T extends LinkedActorIdentity, Z extends ActorConnection<T>, M extends ActorConnectionSearch<T, Z>> extends FermatManager {

    /**
     * Through the method <code>getSearch</code> we can get a new instance of Actor Connection Search.
     * This Actor Connection search provides all the necessary functionality to make an Actor Connection Search.
     *
     * @return an ActorConnectionSearch instance.
     */
    M getSearch(final T actorIdentitySearching);

    /**
     * Through the method <code>requestConnection</code> we can request an actor for a connection.
     * When we're connected with an actor, we're enabled to interact with him.
     *
     * @param actorSending    the actor which is trying to connect.
     * @param actorReceiving  the actor which we're trying to connect with.
     *
     * @throws CantRequestActorConnectionException if something goes wrong.
     * @throws UnsupportedActorTypeException       if the requested kind of actor is not supported by the actor identity.
     */
    void requestConnection(final ActorIdentityInformation actorSending  ,
                           final ActorIdentityInformation actorReceiving) throws CantRequestActorConnectionException,
                                                                                 UnsupportedActorTypeException      ,
                                                                                 ConnectionAlreadyRequestedException;

    /**
     * Through the method <code>disconnect</code> we can disconnect from an actor.
     * If we don't want to interact anymore with the other actor, you can disconnect from him.
     *
     * @param connectionId   id of the actor connection to be disconnected.
     *
     * @throws CantDisconnectFromActorException   if something goes wrong.
     * @throws ActorConnectionNotFoundException   if we can't find an actor connection with this connection id.
     */
    void disconnect(final UUID connectionId) throws CantDisconnectFromActorException,
                                                    ActorConnectionNotFoundException,
            UnexpectedConnectionStateException;

    /**
     * Through the method <code>denyConnection</code> we can deny an actor connection.
     * The actor identity can deny a connection request if he doesn't trust in the counterpart.
     *
     * @param connectionId   id of the actor connection to be denied.
     *
     * @throws CantDenyActorConnectionRequestException   if something goes wrong.
     * @throws ActorConnectionNotFoundException          if we can't find an actor connection with this connection id.
     */
    void denyConnection(final UUID connectionId) throws CantDenyActorConnectionRequestException,
                                                        ActorConnectionNotFoundException       ,
            UnexpectedConnectionStateException;

    /**
     * Through the method <code>cancelConnection</code> we can cancel a connection request sent.
     * The actor could cancel a connection request previously sent.
     * We've to check the state of the connection here.
     *
     * @param connectionId   id of the actor connection request to be canceled.
     *
     * @throws CantCancelActorConnectionRequestException   if something goes wrong.
     * @throws ActorConnectionNotFoundException            if we can't find an actor connection with this connection id.
     */
    void cancelConnection(final UUID connectionId) throws CantCancelActorConnectionRequestException,
                                                          ActorConnectionNotFoundException         ,
            UnexpectedConnectionStateException;

    /**
     * Through the method <code>acceptConnection</code> we can accept a received connection request.
     *
     * @param connectionId   id of the actor connection to be accepted.
     *
     * @throws CantAcceptActorConnectionRequestException   if something goes wrong.
     * @throws ActorConnectionNotFoundException            if we can't find an actor connection with this connection id.
     */
    void acceptConnection(final UUID connectionId) throws CantAcceptActorConnectionRequestException,
                                                          ActorConnectionNotFoundException         ,
            UnexpectedConnectionStateException;

}
