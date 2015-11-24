package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * The final Class <code>cCommunicationNetworkServiceDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 * Created by lnacosta (laion.cj91@gmail.com) on 30/10/2015.
 *
 * @version 1.0
 */
public final class CommunicationNetworkServiceDatabaseFactory {

    private final PluginDatabaseSystem pluginDatabaseSystem;

    public CommunicationNetworkServiceDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public final Database createDatabase(final UUID   ownerId     ,
                                         final String databaseName) throws CantCreateDatabaseException {

        Database database;

        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);

        } catch (CantCreateDatabaseException cantCreateDatabaseException) {

            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            final DatabaseFactory databaseFactory = database.getDatabaseFactory();


            DatabaseTableFactory table;

            /*
             * Create incoming messages table.
             */
            table = databaseFactory.newTableFactory(ownerId, CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_NAME);

            table.addColumn(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_ID_COLUMN_NAME                , DatabaseDataType.STRING,  36, Boolean.TRUE );
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_SENDER_ID_COLUMN_NAME         , DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_RECEIVER_ID_COLUMN_NAME       , DatabaseDataType.STRING,  10, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TEXT_CONTENT_COLUMN_NAME      , DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TYPE_COLUMN_NAME              , DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_STATUS_COLUMN_NAME            , DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addIndex(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /*
             * Create outgoing messages table.
             */
            table = databaseFactory.newTableFactory(ownerId, CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_NAME);

            table.addColumn(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_ID_COLUMN_NAME                , DatabaseDataType.STRING,  36, Boolean.TRUE );
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SENDER_ID_COLUMN_NAME         , DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_ID_COLUMN_NAME       , DatabaseDataType.STRING,  10, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TEXT_CONTENT_COLUMN_NAME      , DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TYPE_COLUMN_NAME              , DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_SHIPPING_TIMESTAMP_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_DELIVERY_TIMESTAMP_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME            , DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addIndex(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /*
             * Create transaction hash table.
             */
            table = databaseFactory.newTableFactory(ownerId, CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TABLE_NAME);

            table.addColumn(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TRANSMISSION_ID_COLUMN_NAME       , DatabaseDataType.STRING,        36, Boolean.TRUE );
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_CONTRACT_HASH_COLUMN_NAME         , DatabaseDataType.STRING,       100, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_CONTRACT_STATUS_COLUMN_NAME       , DatabaseDataType.STRING,        10, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_SENDER_PUBLIC_KEY_COLUMN_NAME     , DatabaseDataType.STRING,       100, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_SENDER_TYPE_COLUMN_NAME           , DatabaseDataType.STRING,        10, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_RECEIVER_PUBLIC_KEY_COLUMN_NAME   , DatabaseDataType.STRING,       100, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_RECEIVER_TYPE_COLUMN_NAME         , DatabaseDataType.STRING,        10, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_CONTRACT_ID_COLUMN_NAME           , DatabaseDataType.STRING,       100, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_NEGOTIATION_ID_COLUMN_NAME        , DatabaseDataType.STRING,        10, Boolean.TRUE );
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TIMESTAMP_COLUMN_NAME             , DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            table.addColumn(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_STATE_COLUMN_NAME                 , DatabaseDataType.STRING,       100, Boolean.FALSE);

            table.addIndex(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            return database;

        } catch (InvalidOwnerIdException invalidOwnerId) {

            throw new CantCreateDatabaseException(invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        }

    }

}