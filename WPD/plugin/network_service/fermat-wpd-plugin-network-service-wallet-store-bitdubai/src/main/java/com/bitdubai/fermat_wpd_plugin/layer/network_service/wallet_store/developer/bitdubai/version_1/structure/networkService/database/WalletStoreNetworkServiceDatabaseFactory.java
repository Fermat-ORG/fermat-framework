package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.networkService.database;

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
 * Created by rodrigo on 8/11/15.
 */
public class WalletStoreNetworkServiceDatabaseFactory implements DealsWithPluginDatabaseSystem{

    /**
     * DealsWithPluginDatabaseSystem Interface member variables and implementation.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Constructor with parameter
     *
     * @param pluginDatabaseSystem the instance
     */
    public WalletStoreNetworkServiceDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Create the data base
     *
     * @param ownerId the owner id
     * @return Database object reference
     * @throws CantCreateDatabaseException error
     */
    public Database createDatabase(UUID ownerId) throws CantCreateDatabaseException {

        Database database;

        /**
         * I will create the database where going to store all the information of this network service.
         */
        try {

            database = this.pluginDatabaseSystem.createDatabase(ownerId, WalletStoreNetworkServiceDatabaseConstants.DATA_BASE_NAME);

        }
        catch (CantCreateDatabaseException cantCreateDatabaseException){

            /**
             * I can not handle this situation.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "Owner id:" + ownerId.toString() + ", DatabaseName:" + WalletStoreNetworkServiceDatabaseConstants.DATA_BASE_NAME, null );
        }


        /**
         * Next, I will add the needed tables.
         */
        try {

            /**
             * Configure the Incoming messages table.
             */
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            table = databaseFactory.newTableFactory(ownerId, WalletStoreNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_NAME);

            table.addColumn(WalletStoreNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_ID_COLUMN_NAME,                 DatabaseDataType.LONG_INTEGER,  50, Boolean.TRUE);
            table.addColumn(WalletStoreNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_SENDER_ID_COLUMN_NAME,          DatabaseDataType.STRING,       100, Boolean.FALSE);
            table.addColumn(WalletStoreNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_RECEIVER_ID_COLUMN_NAME,        DatabaseDataType.STRING,       100, Boolean.FALSE);
            table.addColumn(WalletStoreNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_TEXT_CONTENT_COLUMN_NAME,       DatabaseDataType.STRING,       255, Boolean.FALSE);
            table.addColumn(WalletStoreNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_TYPE_COLUMN_NAME,               DatabaseDataType.STRING,       100, Boolean.FALSE);
            table.addColumn(WalletStoreNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_SHIPPING_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER,  50, Boolean.FALSE);
            table.addColumn(WalletStoreNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_DELIVERY_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER,  50, Boolean.FALSE);
            table.addColumn(WalletStoreNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_STATUS_COLUMN_NAME,             DatabaseDataType.STRING,        10, Boolean.FALSE);

            try {

                //Create the incoming messages table.
                databaseFactory.createTable(ownerId, table);
            }
            catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "TableName:" + WalletStoreNetworkServiceDatabaseConstants.INCOMING_MESSAGES_TABLE_NAME, null);
            }

            /**
             * Configure the Outgoing messages table.
             */
            table = databaseFactory.newTableFactory(ownerId, WalletStoreNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_NAME);

            table.addColumn(WalletStoreNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_ID_COLUMN_NAME,                 DatabaseDataType.LONG_INTEGER,  50, Boolean.TRUE);
            table.addColumn(WalletStoreNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_SENDER_ID_COLUMN_NAME,          DatabaseDataType.STRING,       100, Boolean.FALSE);
            table.addColumn(WalletStoreNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_RECEIVER_ID_COLUMN_NAME,        DatabaseDataType.STRING,       100, Boolean.FALSE);
            table.addColumn(WalletStoreNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_TEXT_CONTENT_COLUMN_NAME,       DatabaseDataType.STRING,       255, Boolean.FALSE);
            table.addColumn(WalletStoreNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_TYPE_COLUMN_NAME,               DatabaseDataType.STRING,       100, Boolean.FALSE);
            table.addColumn(WalletStoreNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_SHIPPING_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER,  50, Boolean.FALSE);
            table.addColumn(WalletStoreNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_DELIVERY_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER,  50, Boolean.FALSE);
            table.addColumn(WalletStoreNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_STATUS_COLUMN_NAME,             DatabaseDataType.STRING,        10, Boolean.FALSE);

            try {

                //Create the outgoing messages table.
                databaseFactory.createTable(ownerId, table);
            }
            catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "TableName:" + WalletStoreNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_TABLE_NAME, null);
            }

        }
        catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException();
        }

        return database;
    }
}
