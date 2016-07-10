package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.*;

/**
 *  The Class  <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 24/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CommunicationsNetworkNodeP2PDatabaseFactory implements DealsWithPluginDatabaseSystem {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters to instantiate class
     * .
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public CommunicationsNetworkNodeP2PDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
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
    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            /**
             * I can not handle this situation.
             */
            throw new CantCreateDatabaseException(cantCreateDatabaseException, "databaseName="+databaseName, "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create actor catalog table.
             */
            table = databaseFactory.newTableFactory(ownerId, ACTOR_CATALOG_TABLE_NAME);

            table.addColumn(ACTOR_CATALOG_IDENTITY_PUBLIC_KEY_COLUMN_NAME       , DatabaseDataType.STRING      ,  255, Boolean.TRUE );
            table.addColumn(ACTOR_CATALOG_NAME_COLUMN_NAME                      , DatabaseDataType.STRING      ,  100, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_ALIAS_COLUMN_NAME                     , DatabaseDataType.STRING      ,  100, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_ACTOR_TYPE_COLUMN_NAME                , DatabaseDataType.STRING      ,  255, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_PHOTO_COLUMN_NAME                     , DatabaseDataType.STRING      , 2500, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_THUMBNAIL_COLUMN_NAME                 , DatabaseDataType.STRING      , 2500, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_LAST_LATITUDE_COLUMN_NAME             , DatabaseDataType.REAL        ,   50, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_LAST_LONGITUDE_COLUMN_NAME            , DatabaseDataType.REAL        ,   50, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_EXTRA_DATA_COLUMN_NAME                , DatabaseDataType.STRING      ,  255, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_HOSTED_TIMESTAMP_COLUMN_NAME          , DatabaseDataType.LONG_INTEGER,  100, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_LAST_UPDATE_TIME_COLUMN_NAME          , DatabaseDataType.LONG_INTEGER,  100, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_LAST_CONNECTION_COLUMN_NAME          , DatabaseDataType.LONG_INTEGER,  100, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_NODE_IDENTITY_PUBLIC_KEY_COLUMN_NAME  , DatabaseDataType.STRING      ,  255, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING      ,  255, Boolean.FALSE);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create actor catalog transaction table.
             */
            table = databaseFactory.newTableFactory(ownerId, ACTOR_CATALOG_TRANSACTION_TABLE_NAME);

            table.addColumn(ACTOR_CATALOG_TRANSACTION_HASH_ID_COLUMN_NAME                   , DatabaseDataType.STRING      ,  100, Boolean.TRUE );
            table.addColumn(ACTOR_CATALOG_TRANSACTION_IDENTITY_PUBLIC_KEY_COLUMN_NAME       , DatabaseDataType.STRING      ,  255, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_NAME_COLUMN_NAME                      , DatabaseDataType.STRING      ,  100, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_ALIAS_COLUMN_NAME                     , DatabaseDataType.STRING      ,  100, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_ACTOR_TYPE_COLUMN_NAME                , DatabaseDataType.STRING      ,  255, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_PHOTO_COLUMN_NAME                     , DatabaseDataType.STRING      , 2500, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_THUMBNAIL_COLUMN_NAME                 , DatabaseDataType.STRING      , 2500, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_LAST_LATITUDE_COLUMN_NAME             , DatabaseDataType.REAL        ,   50, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_LAST_LONGITUDE_COLUMN_NAME            , DatabaseDataType.REAL        ,   50, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_EXTRA_DATA_COLUMN_NAME                , DatabaseDataType.STRING      ,  255, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_HOSTED_TIMESTAMP_COLUMN_NAME          , DatabaseDataType.LONG_INTEGER,  100, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_LAST_CONNECTION_COLUMN_NAME          , DatabaseDataType.LONG_INTEGER,  100, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_NODE_IDENTITY_PUBLIC_KEY_COLUMN_NAME  , DatabaseDataType.STRING      ,  255, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING      ,  255, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_TRANSACTION_TYPE_COLUMN_NAME          , DatabaseDataType.STRING      ,   10, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_GENERATION_TIME_COLUMN_NAME           , DatabaseDataType.LONG_INTEGER,  100, Boolean.FALSE);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }
            /**
             * Create actors catalog transactions pending for propagation table.
             */
            table = databaseFactory.newTableFactory(ownerId, ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_TABLE_NAME);

            table.addColumn(ACTOR_CATALOG_TRANSACTION_HASH_ID_COLUMN_NAME                   , DatabaseDataType.STRING      ,  100, Boolean.TRUE );
            table.addColumn(ACTOR_CATALOG_TRANSACTION_IDENTITY_PUBLIC_KEY_COLUMN_NAME       , DatabaseDataType.STRING      ,  255, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_NAME_COLUMN_NAME                      , DatabaseDataType.STRING      ,  100, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_ALIAS_COLUMN_NAME                     , DatabaseDataType.STRING      ,  100, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_ACTOR_TYPE_COLUMN_NAME                , DatabaseDataType.STRING      ,  255, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_PHOTO_COLUMN_NAME                     , DatabaseDataType.STRING      , 2500, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_THUMBNAIL_COLUMN_NAME                 , DatabaseDataType.STRING      , 2500, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_LAST_LATITUDE_COLUMN_NAME             , DatabaseDataType.REAL        ,   50, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_LAST_LONGITUDE_COLUMN_NAME            , DatabaseDataType.REAL        ,   50, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_EXTRA_DATA_COLUMN_NAME                , DatabaseDataType.STRING      ,  255, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_HOSTED_TIMESTAMP_COLUMN_NAME          , DatabaseDataType.LONG_INTEGER,  100, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_LAST_CONNECTION_COLUMN_NAME          , DatabaseDataType.LONG_INTEGER,  100, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_NODE_IDENTITY_PUBLIC_KEY_COLUMN_NAME  , DatabaseDataType.STRING      ,  255, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING      ,  255, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_TRANSACTION_TYPE_COLUMN_NAME          , DatabaseDataType.STRING      ,   10, Boolean.FALSE);
            table.addColumn(ACTOR_CATALOG_TRANSACTION_GENERATION_TIME_COLUMN_NAME           , DatabaseDataType.LONG_INTEGER,  100, Boolean.FALSE);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create calls log table.
             */
            table = databaseFactory.newTableFactory(ownerId, CALLS_LOG_TABLE_NAME);

            table.addColumn(CALLS_LOG_CALL_ID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.TRUE);
            table.addColumn(CALLS_LOG_CALL_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            table.addColumn(CALLS_LOG_FINISH_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            table.addColumn(CALLS_LOG_START_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 255, Boolean.FALSE);
            table.addColumn(CALLS_LOG_STEP_COLUMN_NAME, DatabaseDataType.STRING, 2500, Boolean.FALSE);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create checked in actor table.
             */
            table = databaseFactory.newTableFactory(ownerId, CHECKED_IN_ACTOR_TABLE_NAME);

            table.addColumn(CHECKED_IN_ACTOR_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.TRUE);
            table.addColumn(CHECKED_IN_ACTOR_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CHECKED_IN_ACTOR_ALIAS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CHECKED_IN_ACTOR_ACTOR_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CHECKED_IN_ACTOR_PHOTO_COLUMN_NAME, DatabaseDataType.STRING, 2500, Boolean.FALSE);
            table.addColumn(CHECKED_IN_ACTOR_LATITUDE_COLUMN_NAME, DatabaseDataType.REAL, 50, Boolean.FALSE);
            table.addColumn(CHECKED_IN_ACTOR_LONGITUDE_COLUMN_NAME, DatabaseDataType.REAL, 50, Boolean.FALSE);
            table.addColumn(CHECKED_IN_ACTOR_EXTRA_DATA_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CHECKED_IN_ACTOR_CHECKED_IN_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            table.addColumn(CHECKED_IN_ACTOR_NS_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CHECKED_IN_ACTOR_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create checked in clients table.
             */
            table = databaseFactory.newTableFactory(ownerId, CHECKED_IN_CLIENTS_TABLE_NAME);

            table.addColumn(CHECKED_IN_CLIENTS_IDENTITY_PUBLIC_KEY_COLUMN_NAME , DatabaseDataType.STRING      , 255, Boolean.TRUE );
            table.addColumn(CHECKED_IN_CLIENTS_LATITUDE_COLUMN_NAME            , DatabaseDataType.REAL        ,  50, Boolean.FALSE);
            table.addColumn(CHECKED_IN_CLIENTS_LONGITUDE_COLUMN_NAME           , DatabaseDataType.REAL        ,  50, Boolean.FALSE);
            table.addColumn(CHECKED_IN_CLIENTS_DEVICE_TYPE_COLUMN_NAME         , DatabaseDataType.STRING      ,  50, Boolean.FALSE);
            table.addColumn(CHECKED_IN_CLIENTS_CHECKED_IN_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create checked in network service table.
             */
            table = databaseFactory.newTableFactory(ownerId, CHECKED_IN_NETWORK_SERVICE_TABLE_NAME);

            table.addColumn(CHECKED_IN_NETWORK_SERVICE_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.TRUE);
            table.addColumn(CHECKED_IN_NETWORK_SERVICE_LATITUDE_COLUMN_NAME, DatabaseDataType.REAL, 50, Boolean.FALSE);
            table.addColumn(CHECKED_IN_NETWORK_SERVICE_LONGITUDE_COLUMN_NAME, DatabaseDataType.REAL, 50, Boolean.FALSE);
            table.addColumn(CHECKED_IN_NETWORK_SERVICE_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CHECKED_IN_NETWORK_SERVICE_NETWORK_SERVICE_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(CHECKED_IN_NETWORK_SERVICE_CHECKED_IN_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create checked actors history table.
             */
            table = databaseFactory.newTableFactory(ownerId, CHECKED_ACTORS_HISTORY_TABLE_NAME);

            table.addColumn(CHECKED_ACTORS_HISTORY_UUID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(CHECKED_ACTORS_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CHECKED_ACTORS_HISTORY_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CHECKED_ACTORS_HISTORY_ALIAS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CHECKED_ACTORS_HISTORY_ACTOR_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CHECKED_ACTORS_HISTORY_PHOTO_COLUMN_NAME, DatabaseDataType.STRING, 2500, Boolean.FALSE);
            table.addColumn(CHECKED_ACTORS_HISTORY_LAST_LATITUDE_COLUMN_NAME, DatabaseDataType.REAL, 50, Boolean.FALSE);
            table.addColumn(CHECKED_ACTORS_HISTORY_LAST_LONGITUDE_COLUMN_NAME, DatabaseDataType.REAL, 50, Boolean.FALSE);
            table.addColumn(CHECKED_ACTORS_HISTORY_CLIENT_IDENTITY_PUBLICKEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CHECKED_ACTORS_HISTORY_EXTRA_DATA_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CHECKED_ACTORS_HISTORY_CHECKED_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            table.addColumn(CHECKED_ACTORS_HISTORY_CHECK_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create Clients Registration History table.
             */
            table = databaseFactory.newTableFactory(ownerId, CLIENTS_REGISTRATION_HISTORY_TABLE_NAME);

            table.addColumn(CLIENTS_REGISTRATION_HISTORY_ID_COLUMN_NAME                 , DatabaseDataType.STRING      , 100, Boolean.TRUE );
            table.addColumn(CLIENTS_REGISTRATION_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING      , 255, Boolean.FALSE);
            table.addColumn(CLIENTS_REGISTRATION_HISTORY_LAST_LATITUDE_COLUMN_NAME      , DatabaseDataType.REAL        ,  50, Boolean.FALSE);
            table.addColumn(CLIENTS_REGISTRATION_HISTORY_LAST_LONGITUDE_COLUMN_NAME     , DatabaseDataType.REAL        ,  50, Boolean.FALSE);
            table.addColumn(CLIENTS_REGISTRATION_HISTORY_DEVICE_TYPE_COLUMN_NAME        , DatabaseDataType.STRING      ,  50, Boolean.FALSE);
            table.addColumn(CLIENTS_REGISTRATION_HISTORY_CHECKED_TIMESTAMP_COLUMN_NAME  , DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            table.addColumn(CLIENTS_REGISTRATION_HISTORY_TYPE_COLUMN_NAME               , DatabaseDataType.STRING      ,  10, Boolean.FALSE);
            table.addColumn(CLIENTS_REGISTRATION_HISTORY_RESULT_COLUMN_NAME             , DatabaseDataType.STRING      ,  10, Boolean.FALSE);
            table.addColumn(CLIENTS_REGISTRATION_HISTORY_DETAIL_COLUMN_NAME             , DatabaseDataType.STRING      , 100, Boolean.FALSE);

            table.addIndex (CLIENTS_REGISTRATION_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create checked network service history table.
             */
            table = databaseFactory.newTableFactory(ownerId, CHECKED_NETWORK_SERVICE_HISTORY_TABLE_NAME);

            table.addColumn(CHECKED_NETWORK_SERVICE_HISTORY_UUID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(CHECKED_NETWORK_SERVICE_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CHECKED_NETWORK_SERVICE_HISTORY_LAST_LATITUDE_COLUMN_NAME, DatabaseDataType.REAL, 50, Boolean.FALSE);
            table.addColumn(CHECKED_NETWORK_SERVICE_HISTORY_LAST_LONGITUDE_COLUMN_NAME, DatabaseDataType.REAL, 50, Boolean.FALSE);
            table.addColumn(CHECKED_NETWORK_SERVICE_HISTORY_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CHECKED_NETWORK_SERVICE_HISTORY_NETWORK_SERVICE_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(CHECKED_NETWORK_SERVICE_HISTORY_CHECKED_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            table.addColumn(CHECKED_NETWORK_SERVICE_HISTORY_CHECK_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create clients connections history table.
             */
            table = databaseFactory.newTableFactory(ownerId, CLIENTS_CONNECTIONS_HISTORY_TABLE_NAME);

            table.addColumn(CLIENTS_CONNECTIONS_HISTORY_UUID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(CLIENTS_CONNECTIONS_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CLIENTS_CONNECTIONS_HISTORY_LAST_LATITUDE_COLUMN_NAME, DatabaseDataType.REAL, 50, Boolean.FALSE);
            table.addColumn(CLIENTS_CONNECTIONS_HISTORY_LAST_LONGITUDE_COLUMN_NAME, DatabaseDataType.REAL, 50, Boolean.FALSE);
            table.addColumn(CLIENTS_CONNECTIONS_HISTORY_DEVICE_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(CLIENTS_CONNECTIONS_HISTORY_CONNECTION_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            table.addColumn(CLIENTS_CONNECTIONS_HISTORY_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create nodes connections history table.
             */
            table = databaseFactory.newTableFactory(ownerId, NODES_CONNECTIONS_HISTORY_TABLE_NAME);

            table.addColumn(NODES_CONNECTIONS_HISTORY_UUID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(NODES_CONNECTIONS_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(NODES_CONNECTIONS_HISTORY_IP_COLUMN_NAME, DatabaseDataType.STRING, 32, Boolean.FALSE);
            table.addColumn(NODES_CONNECTIONS_HISTORY_DEFAULT_PORT_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);
            table.addColumn(NODES_CONNECTIONS_HISTORY_LAST_LATITUDE_COLUMN_NAME, DatabaseDataType.REAL, 50, Boolean.FALSE);
            table.addColumn(NODES_CONNECTIONS_HISTORY_LAST_LONGITUDE_COLUMN_NAME, DatabaseDataType.REAL, 50, Boolean.FALSE);
            table.addColumn(NODES_CONNECTIONS_HISTORY_CONNECTION_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            table.addColumn(NODES_CONNECTIONS_HISTORY_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create method calls history table.
             */
            table = databaseFactory.newTableFactory(ownerId, METHOD_CALLS_HISTORY_TABLE_NAME);

            table.addColumn(METHOD_CALLS_HISTORY_UUID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(METHOD_CALLS_HISTORY_METHOD_NAME_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(METHOD_CALLS_HISTORY_PARAMETERS_COLUMN_NAME, DatabaseDataType.STRING, 1000, Boolean.FALSE);
            table.addColumn(METHOD_CALLS_HISTORY_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(METHOD_CALLS_HISTORY_CREATE_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create nodes catalog table.
             */
            table = databaseFactory.newTableFactory(ownerId, NODES_CATALOG_TABLE_NAME);

            table.addColumn(NODES_CATALOG_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.TRUE);
            table.addColumn(NODES_CATALOG_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_IP_COLUMN_NAME, DatabaseDataType.STRING, 32, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_DEFAULT_PORT_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_LAST_LATITUDE_COLUMN_NAME, DatabaseDataType.REAL, 50, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_LAST_LONGITUDE_COLUMN_NAME, DatabaseDataType.REAL, 50, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_LATE_NOTIFICATION_COUNTER_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_OFFLINE_COUNTER_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_REGISTERED_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create nodes catalog transaction table.
             */
            table = databaseFactory.newTableFactory(ownerId, NODES_CATALOG_TRANSACTION_TABLE_NAME);

            table.addColumn(NODES_CATALOG_TRANSACTION_HASH_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(NODES_CATALOG_TRANSACTION_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_TRANSACTION_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_TRANSACTION_IP_COLUMN_NAME, DatabaseDataType.STRING, 32, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_TRANSACTION_DEFAULT_PORT_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_TRANSACTION_LAST_LATITUDE_COLUMN_NAME, DatabaseDataType.REAL, 50, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_TRANSACTION_LAST_LONGITUDE_COLUMN_NAME, DatabaseDataType.REAL, 50, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_TRANSACTION_REGISTERED_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_TRANSACTION_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_TRANSACTION_TRANSACTION_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create nodes catalog transactions pending for propagation table.
             */
            table = databaseFactory.newTableFactory(ownerId, NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_TABLE_NAME);

            table.addColumn(NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_HASH_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_IP_COLUMN_NAME, DatabaseDataType.STRING, 32, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_DEFAULT_PORT_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_LAST_LATITUDE_COLUMN_NAME, DatabaseDataType.REAL, 50, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_LAST_LONGITUDE_COLUMN_NAME, DatabaseDataType.REAL, 50, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_REGISTERED_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            table.addColumn(NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_TRANSACTION_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }
        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        }
        return database;
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}
