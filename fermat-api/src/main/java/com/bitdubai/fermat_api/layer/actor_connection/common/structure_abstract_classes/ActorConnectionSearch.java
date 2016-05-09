package com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes;

import com.bitdubai.fermat_api.layer.actor_connection.common.database_abstract_classes.ActorConnectionDao;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantListActorConnectionsException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;

import java.util.List;

import static com.bitdubai.fermat_api.layer.actor_connection.common.database_common_classes.ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_ALIAS_COLUMN_NAME;
import static com.bitdubai.fermat_api.layer.actor_connection.common.database_common_classes.ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_STATE_COLUMN_NAME;
import static com.bitdubai.fermat_api.layer.actor_connection.common.database_common_classes.ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_ACTOR_TYPE_COLUMN_NAME;
import static com.bitdubai.fermat_api.layer.actor_connection.common.database_common_classes.ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME;

/**
 * The interface <code>ActorConnectionSearch</code>
 * contains all the methods to search an Actor Connection.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/11/2015.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class ActorConnectionSearch<Z extends LinkedActorIdentity, T extends ActorConnection<Z>> {

    protected final Z                        actorIdentity;
    protected final ActorConnectionDao<Z, T> dao          ;
    protected       DatabaseTable            databaseTable;

    public ActorConnectionSearch(final Z                        actorIdentity,
                                 final ActorConnectionDao<Z, T> dao          ) {

        this.actorIdentity = actorIdentity;
        this.dao           = dao;

        this.resetFilters();
    }

    /**
     * Through the method <code>resetFilters</code> you can reset the filters set,
     */
    public final void resetFilters() {

        this.databaseTable = dao.getActorConnectionsTable();
        databaseTable.addStringFilter(ACTOR_CONNECTIONS_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, actorIdentity.getPublicKey(), DatabaseFilterType.EQUAL);
    }

    /**
     * Through the method <code>addAlias</code> we can add an alias of actor to search.
     *
     * @param alias of the actor.
     */
    public final void addAlias(final String alias) {

        databaseTable.addStringFilter(
                ACTOR_CONNECTIONS_ALIAS_COLUMN_NAME,
                alias,
                DatabaseFilterType.LIKE
        );
    }

    /**
     * Through the method <code>addActorType</code> we can add a type of actor to search.
     *
     * @param actorType of the actor.
     */
    public final void addActorType(final Actors actorType){

        databaseTable.addFermatEnumFilter(
                ACTOR_CONNECTIONS_LINKED_IDENTITY_ACTOR_TYPE_COLUMN_NAME,
                actorType,
                DatabaseFilterType.EQUAL
        );
    }

    /**
     * Through the method <code>addConnectionState</code> we can add a connection state of actor connections to search.
     *
     * @param connectionState of the actor connection.
     */
    public final void addConnectionState(final ConnectionState connectionState) {

        databaseTable.addFermatEnumFilter(
                ACTOR_CONNECTIONS_CONNECTION_STATE_COLUMN_NAME,
                connectionState,
                DatabaseFilterType.EQUAL
        );
    }

    /**
     * Through the method <code>getResult</code> we can get the results of the search,
     * Like we're not setting max and offset we will return all the crypto brokers that match
     * with the parameters set.
     *
     * @return a list of crypto brokers with their information.
     *
     * @throws CantListActorConnectionsException  if something goes wrong.
     */
    public List<T> getResult() throws CantListActorConnectionsException {

        return dao.listActorConnections(databaseTable);
    }

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
    public List<T> getResult(final Integer max   ,
                             final Integer offset) throws CantListActorConnectionsException {

        databaseTable.setFilterTop(max.toString());
        databaseTable.setFilterOffSet(offset.toString());

        return dao.listActorConnections(databaseTable);
    }

}
