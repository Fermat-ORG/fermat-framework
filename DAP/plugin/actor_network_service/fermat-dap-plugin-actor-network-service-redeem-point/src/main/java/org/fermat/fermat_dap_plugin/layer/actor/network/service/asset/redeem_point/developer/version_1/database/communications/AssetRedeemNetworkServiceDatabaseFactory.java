package org.fermat.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.version_1.database.communications;

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
 * Created by franklin on 17/10/15.
 */
public class AssetRedeemNetworkServiceDatabaseFactory {
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
    public AssetRedeemNetworkServiceDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
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

            /*
            * Create outgoing notification table.
            */
            table = databaseFactory.newTableFactory(ownerId, AssetRedeemNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TABLE_NAME);

            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 25, Boolean.FALSE);
            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 25, Boolean.FALSE);
            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 59, Boolean.FALSE);
            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, DatabaseDataType.STRING, 30, Boolean.FALSE);
            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_READ_MARK_COLUMN_NAME, DatabaseDataType.STRING, 6, Boolean.FALSE);
            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_SENT_COUNT_COLUMN_NAME, DatabaseDataType.INTEGER, 6, Boolean.FALSE);
            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME, DatabaseDataType.INTEGER, 50, Boolean.FALSE);
            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);


            table.addIndex(AssetRedeemNetworkServiceDatabaseConstants.OUTGOING_NOTIFICATION_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }
            /*
            * Create incoming messages table.
            */
            table = databaseFactory.newTableFactory(ownerId, AssetRedeemNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_TABLE_NAME);

            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_RECEIVER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 25, Boolean.FALSE);
            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_RECEIVER_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 25, Boolean.FALSE);
            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 50, Boolean.FALSE);
            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_SENDER_ALIAS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_PROTOCOL_STATE_COLUMN_NAME, DatabaseDataType.STRING, 6, Boolean.FALSE);
            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_READ_MARK_COLUMN_NAME, DatabaseDataType.STRING, 6, Boolean.FALSE);
            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(AssetRedeemNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_RESPONSE_TO_NOTIFICATION_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);

            table.addIndex(AssetRedeemNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_FIRST_KEY_COLUMN);

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
