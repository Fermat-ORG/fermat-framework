package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database;

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
 *  The Class  <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.database.IntraActorNetworkServiceDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by natalia on 18/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IntraActorNetworkServiceDatabaseFactory {
    private final PluginDatabaseSystem pluginDatabaseSystem;

    public IntraActorNetworkServiceDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public Database createDatabase(UUID ownerId     ,
                                      String databaseName) throws CantCreateDatabaseException {

        try {

            Database database = this.pluginDatabaseSystem.createDatabase(
                    ownerId     ,
                    databaseName
            );

            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /*
            * Create outgoing notification table.
            */
            table = databaseFactory.newTableFactory(ownerId, IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_TABLE_NAME);

            table.addColumn(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 25, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 25, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 59, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, DatabaseDataType.STRING, 30, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_READ_MARK_COLUMN_NAME, DatabaseDataType.STRING, 6, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_SENT_COUNT_COLUMN_NAME, DatabaseDataType.INTEGER, 6, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_SENDER_PHRASE_COLUMN_NAME, DatabaseDataType.STRING, 200, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);


            table.addIndex(IntraActorNetworkServiceDataBaseConstants.OUTGOING_NOTIFICATION_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }
            /*
            * Create incoming messages table.
            */
            table = databaseFactory.newTableFactory(ownerId, IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_TABLE_NAME);

            table.addColumn(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 25, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 25, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 50, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, DatabaseDataType.STRING, 6, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_READ_MARK_COLUMN_NAME, DatabaseDataType.STRING, 6, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_SENDER_PHRASE_COLUMN_NAME, DatabaseDataType.STRING, 200, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);

            table.addIndex(IntraActorNetworkServiceDataBaseConstants.INCOMING_NOTIFICATION_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create Crypto Address Request table.
             */
            table = databaseFactory.newTableFactory(ownerId, IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_TABLE_NAME);

            table.addColumn(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_ID_COLUMN_NAME                          ,DatabaseDataType.STRING,  40, Boolean.TRUE );
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_PUBLIC_KEY_COLUMN_NAME             ,     DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_ALIAS_COLUMN_NAME             ,          DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_CITY_COLUMN_NAME      ,                  DatabaseDataType.STRING,  100, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_COUNTRY_COLUMN_NAME      ,               DatabaseDataType.STRING,  100, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_TIMESTAMP_COLUMN_NAME,                   DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_PHRASE_COLUMN_NAME,                      DatabaseDataType.STRING, 130, Boolean.FALSE);

            table.addIndex(IntraActorNetworkServiceDataBaseConstants.INTRA_ACTOR_ONLINE_CACHE_FIRST_KEY_COLUMN);

            try {

                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {

                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            return database;

        } catch (CantCreateDatabaseException cantCreateDatabaseException) {

            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        } catch (InvalidOwnerIdException invalidOwnerId) {

            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        }
    }
}

