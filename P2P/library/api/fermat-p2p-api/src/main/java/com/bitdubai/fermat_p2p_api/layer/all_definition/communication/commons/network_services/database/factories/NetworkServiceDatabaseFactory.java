package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.factories;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.INCOMING_MESSAGES_CONTENT_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.INCOMING_MESSAGES_CONTENT_TYPE_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.INCOMING_MESSAGES_ID_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.INCOMING_MESSAGES_RECEIVER_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.INCOMING_MESSAGES_SENDER_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.INCOMING_MESSAGES_STATUS_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_CONTENT_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_CONTENT_TYPE_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_FAIL_COUNT_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_ID_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_IS_BETWEEN_ACTORS_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SENDER_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.QUERIES_BROADCAST_CODE_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.QUERIES_DISCOVERY_QUERY_PARAMS_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.QUERIES_EXECUTION_TIME_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.QUERIES_ID_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.QUERIES_STATUS_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.QUERIES_TABLE_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.QUERIES_TYPE_COLUMN_NAME;

/**
 *  The Class  <code>com.bitdubai.fermat_p2p_api.layer.network_service.p2p.developer.bitdubai.version_1.database.P2PNetworkServiceDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/05/16.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkServiceDatabaseFactory {

    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters to instantiate class
     * .
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public NetworkServiceDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Create the database
     *
     * @param ownerId      the owner id
     * @param databaseName the database name
     *
     * @return Database
     * 
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
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Incoming Messages table.
             */
            table = databaseFactory.newTableFactory(ownerId, INCOMING_MESSAGES_TABLE_NAME);

            table.addColumn(INCOMING_MESSAGES_ID_COLUMN_NAME                 , DatabaseDataType.STRING,  36, Boolean.TRUE );
            table.addColumn(INCOMING_MESSAGES_SENDER_PUBLIC_KEY_COLUMN_NAME  , DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(INCOMING_MESSAGES_RECEIVER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME , DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME , DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(INCOMING_MESSAGES_CONTENT_TYPE_COLUMN_NAME       , DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(INCOMING_MESSAGES_STATUS_COLUMN_NAME             , DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(INCOMING_MESSAGES_CONTENT_COLUMN_NAME            , DatabaseDataType.STRING, 255, Boolean.FALSE);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create Outgoing Messages table.
             */
            table = databaseFactory.newTableFactory(ownerId, OUTGOING_MESSAGES_TABLE_NAME);

            table.addColumn(OUTGOING_MESSAGES_ID_COLUMN_NAME                        , DatabaseDataType.STRING ,  36, Boolean.TRUE );
            table.addColumn(OUTGOING_MESSAGES_SENDER_PUBLIC_KEY_COLUMN_NAME         , DatabaseDataType.STRING , 130, Boolean.FALSE);
            table.addColumn(OUTGOING_MESSAGES_RECEIVER_PUBLIC_KEY_COLUMN_NAME       , DatabaseDataType.STRING , 130, Boolean.FALSE);
            table.addColumn(OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME        , DatabaseDataType.STRING , 100, Boolean.FALSE);
            table.addColumn(OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME        , DatabaseDataType.STRING , 255, Boolean.FALSE);
            table.addColumn(OUTGOING_MESSAGES_CONTENT_TYPE_COLUMN_NAME              , DatabaseDataType.STRING , 100, Boolean.FALSE);
            table.addColumn(OUTGOING_MESSAGES_STATUS_COLUMN_NAME                    , DatabaseDataType.STRING , 100, Boolean.FALSE);
            table.addColumn(OUTGOING_MESSAGES_IS_BETWEEN_ACTORS_COLUMN_NAME         , DatabaseDataType.INTEGER,   0, Boolean.FALSE);
            table.addColumn(OUTGOING_MESSAGES_FAIL_COUNT_COLUMN_NAME                , DatabaseDataType.INTEGER,  10, Boolean.FALSE);
            table.addColumn(OUTGOING_MESSAGES_CONTENT_COLUMN_NAME                   , DatabaseDataType.STRING , 255, Boolean.FALSE);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create Queries table.
             */
            table = databaseFactory.newTableFactory(ownerId, QUERIES_TABLE_NAME);

            table.addColumn(QUERIES_ID_COLUMN_NAME                     , DatabaseDataType.STRING       ,  36, Boolean.TRUE );
            table.addColumn(QUERIES_BROADCAST_CODE_COLUMN_NAME         , DatabaseDataType.STRING       ,  20, Boolean.FALSE);
            table.addColumn(QUERIES_DISCOVERY_QUERY_PARAMS_COLUMN_NAME , DatabaseDataType.STRING       , 255, Boolean.FALSE);
            table.addColumn(QUERIES_EXECUTION_TIME_COLUMN_NAME         , DatabaseDataType.LONG_INTEGER ,   0, Boolean.FALSE);
            table.addColumn(QUERIES_TYPE_COLUMN_NAME                   , DatabaseDataType.STRING       ,   2, Boolean.FALSE);
            table.addColumn(QUERIES_STATUS_COLUMN_NAME                 , DatabaseDataType.STRING       ,   2, Boolean.FALSE);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        }
        return database;
    }
    
}

