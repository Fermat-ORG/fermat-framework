package com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 *  The Class  <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.database.Crypto BrokerActorNetworkServiceDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class FanActorNetworkServiceDatabaseFactory {

    private final PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters to instantiate class
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public FanActorNetworkServiceDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Create the database
     *
     * @param ownerId      the owner id
     * @param databaseName the database name
     * @return Database
     * @throws CantCreateDatabaseException
     */
    protected Database createDatabase(final UUID   ownerId     ,
                                      final String databaseName) throws CantCreateDatabaseException {

        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {

            database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);

        } catch (final CantCreateDatabaseException e) {
            /**
             * I can not handle this situation.
             */
            throw new CantCreateDatabaseException(e, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseTableFactory table;
            com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Connection News table.
             */
            table = databaseFactory.newTableFactory(ownerId, FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_TABLE_NAME);

            table.addColumn(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME            , DatabaseDataType.STRING      ,  36, Boolean.TRUE );
            table.addColumn(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_PUBLIC_KEY_COLUMN_NAME     , DatabaseDataType.STRING      , 130, Boolean.FALSE);
            table.addColumn(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ACTOR_TYPE_COLUMN_NAME     , DatabaseDataType.STRING      ,  10, Boolean.FALSE);
            table.addColumn(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENDER_ALIAS_COLUMN_NAME          , DatabaseDataType.STRING      , 130, Boolean.FALSE);
            table.addColumn(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_DESTINATION_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING      , 130, Boolean.FALSE);
            table.addColumn(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_DESTINATION_ACTOR_TYPE_COLUMN_NAME, DatabaseDataType.STRING      ,  10, Boolean.FALSE);
            table.addColumn(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_TYPE_COLUMN_NAME          , DatabaseDataType.STRING      ,  10, Boolean.FALSE);
            table.addColumn(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME         , DatabaseDataType.STRING      ,  10, Boolean.FALSE);
            table.addColumn(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME        , DatabaseDataType.STRING      ,  10, Boolean.FALSE);
            table.addColumn(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_REQUEST_SENT_COUNT_COLUMN_NAME    , DatabaseDataType.INTEGER     ,  10, Boolean.FALSE);
            table.addColumn(FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_SENT_TIME_COLUMN_NAME             , DatabaseDataType.LONG_INTEGER,   0, Boolean.FALSE);

            table.addIndex (FanActorNetworkServiceDatabaseConstants.CONNECTION_NEWS_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            return database;

        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        }

    }

}