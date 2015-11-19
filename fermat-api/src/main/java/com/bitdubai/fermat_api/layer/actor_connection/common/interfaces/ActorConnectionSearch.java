package com.bitdubai.fermat_api.layer.actor_connection.common.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.actor_connection.common.interfaces.ActorConnectionSearch</code>
 * contains all the methods to search an Actor Connection.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/11/2015.
 */
public interface ActorConnectionSearch {

    /**
     * Through the method <code>addAlias</code> we can add an alias of actor to search.
     *
     * @param alias of the actor.
     */
    void addAlias(final String alias);

    /**
     * Through the method <code>addActorType</code> we can add a type of actor to search.
     *
     * @param actorType of the actor.
     */
    void addActorType(final Actors actorType);

    /**
     * Through the method <code>addConnectionState</code> we can add a connection state of actor connections to search.
     *
     * @param connectionState of the actor connection.
     */
    void addConnectionState(final ConnectionState connectionState);

    /**
     * Through the method <code>getResult</code> we can get the results of the search,
     * Like we're not setting max and offset we will return all the crypto brokers that match
     * with the parameters set.
     *
     * @return a list of crypto brokers with their information.
     *
     * @throws CantListActorConnectionsException  if something goes wrong.
     */
    List<ActorConnection> getResult() throws CantListActorConnectionsException;

    /**
     * Through the method <code>getResult</code> we can get the results of the search,
     * filtered by the parameters set.
     * We'll receive at most the quantity of @max set. If null by default the max will be 100.
     * We'll receive the results since @offset set. If null by default the offset will be 0.
     *
     * @param max     maximum quantity of results expected.
     * @param offset  position to start bringing the results.
     *
     * @return a list of crypto brokers with their information.
     *
     * @throws CantListActorConnectionsException  if something goes wrong.
     */
    List<ActorConnection> getResult(final Integer max   ,
                                    final Integer offset) throws CantListActorConnectionsException;

    /**
     * Through the method <code>resetFilters</code> you can reset the filters set,
     */
    void resetFilters();

}
