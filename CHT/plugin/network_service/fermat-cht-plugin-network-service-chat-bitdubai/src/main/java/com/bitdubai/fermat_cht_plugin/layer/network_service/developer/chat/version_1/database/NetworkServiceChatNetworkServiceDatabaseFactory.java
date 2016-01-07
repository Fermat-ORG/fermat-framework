
package com.bitdubai.fermat_cht_plugin.layer.network_service.developer.chat.version_1.database;

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

/**
 *  The Class  <code>com.bitdubai.fermat_cht_plugin.layer.network_service.network_service_chat.developer.bitdubai.version_1.database.Network Service ChatNetworkServiceDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Gabriel Araujo - (gabe_512@hotmail.com) on 06/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NetworkServiceChatNetworkServiceDatabaseFactory implements DealsWithPluginDatabaseSystem {

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
    public NetworkServiceChatNetworkServiceDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
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
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Outgoing Chat table.
             */
            table = databaseFactory.newTableFactory(ownerId, NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_TABLE_NAME);

            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_IDCHAT_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.TRUE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_IDOBJECTO_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_LOCALACTORTYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_LOCALACTORPUBKEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_REMOTEACTORTYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_REMOTEACTORPUBKEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_CHATNAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_CHATSTATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_DATE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_IDMENSAJE_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_DISTRIBUTIONSTATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addIndex(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create Incoming Chat table.
             */
            table = databaseFactory.newTableFactory(ownerId, NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_TABLE_NAME);

            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_IDCHAT_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.TRUE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_IDOBJECTO_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_LOCALACTORTYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_LOCALACTORPUBKEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_REMOTEACTORTYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_REMOTEACTORPUBKEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_CHATNAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_CHATSTATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_DATE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_IDMENSAJE_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_DISTRIBUTIONSTATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addIndex(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create Incoming Chat Message table.
             */
            table = databaseFactory.newTableFactory(ownerId, NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_TABLE_NAME);

            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_IDMENSAJE_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.TRUE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_IDCHAT_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.TRUE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_MESSAGE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_DATE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_DISTRIBUTIONSTATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addIndex(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create Outgoing Chat Message table.
             */
            table = databaseFactory.newTableFactory(ownerId, NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_TABLE_NAME);

            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_IDMENSAJE_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.TRUE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_IDCHAT_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.TRUE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_MESSAGE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_DATE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_DISTRIBUTIONSTATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addIndex(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create Incoming Chat Message Notification Status table.
             */
            table = databaseFactory.newTableFactory(ownerId, NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_NOTIFICATION_STATUS_TABLE_NAME);

            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_NOTIFICATION_STATUS_IDMENSAJE_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.TRUE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_NOTIFICATION_STATUS_IDCHAT_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.TRUE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_NOTIFICATION_STATUS_DATE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_NOTIFICATION_STATUS_DISTRIBUTIONSTATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addIndex(NetworkServiceChatNetworkServiceDatabaseConstants.INCOMING_CHAT_MESSAGE_NOTIFICATION_STATUS_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create Outgoing Chat Message Notification Status table.
             */
            table = databaseFactory.newTableFactory(ownerId, NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_NOTIFICATION_STATUS_TABLE_NAME);

            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_NOTIFICATION_STATUS_IDMENSAJE_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.TRUE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_NOTIFICATION_STATUS_IDCHAT_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.TRUE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_NOTIFICATION_STATUS_DATE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_NOTIFICATION_STATUS_DISTRIBUTIONSTATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addIndex(NetworkServiceChatNetworkServiceDatabaseConstants.OUTGOING_CHAT_MESSAGE_NOTIFICATION_STATUS_FIRST_KEY_COLUMN);

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

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}