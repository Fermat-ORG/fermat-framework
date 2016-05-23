package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database;

import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public final class AssetBuyerDatabaseFactory {
    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    /**
     * Constructor with parameters to instantiate class
     * .
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public AssetBuyerDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * Create the database
     *
     * @return Database
     * @throws CantCreateDatabaseException
     */
    public Database createDatabase() throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(pluginId, AssetBuyerDatabaseConstants.ASSET_BUYER_DATABASE);
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
             * Create Asset Reception table.
             */
            table = databaseFactory.newTableFactory(pluginId, AssetBuyerDatabaseConstants.ASSET_BUYER_TABLE_NAME);

            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_ENTRY_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_NETWORK_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_SELLER_PUBLICKEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_SELL_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_SELLER_CRYPTO_ADDRESS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_BUYER_CRYPTO_ADDRESS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_CRYPTO_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_REFERENCE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_BUYER_TRANSACTION_COLUMN_NAME, DatabaseDataType.STRING, 200, Boolean.FALSE);
            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_BUYER_VALUE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_SELLER_TRANSACTION_COLUMN_NAME, DatabaseDataType.STRING, 200, Boolean.FALSE);
            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_SELLER_VALUE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_SELLER_ENTRY_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_OUTGOING_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_TX_HASH_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);

            table.addIndex(AssetBuyerDatabaseConstants.ASSET_BUYER_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(pluginId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create Negotiation table.
             */
            table = databaseFactory.newTableFactory(pluginId, AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_TABLE_NAME);

            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_OBJECT_XML_COLUMN_NAME, DatabaseDataType.STRING, Validate.MAX_SIZE_STRING_COLUMN, Boolean.FALSE);
            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_SELLER_PUBLICKEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_ASSET_PUBLICKEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_BTC_WALLET_PK_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_NETWORK_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_FOR_PROCESS_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);

            table.addIndex(AssetBuyerDatabaseConstants.ASSET_BUYER_NEGOTIATION_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(pluginId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            DatabaseTableFactory eventsRecorderTable = databaseFactory.newTableFactory(pluginId, AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_TABLE_NAME);

            eventsRecorderTable.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_ID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.TRUE);
            eventsRecorderTable.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_EVENT_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_SOURCE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);

            eventsRecorderTable.addIndex(AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(pluginId, eventsRecorderTable);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "Creating " + AssetBuyerDatabaseConstants.ASSET_BUYER_EVENTS_RECORDED_TABLE_NAME + " table", "Exception not handled by the plugin, There is a problem and I cannot create the table.");
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


    public boolean databaseExists() {
        try {
            pluginDatabaseSystem.openDatabase(pluginId, AssetBuyerDatabaseConstants.ASSET_BUYER_DATABASE);
            return true;
        } catch (CantOpenDatabaseException | DatabaseNotFoundException e) {
            return false;
        }
    }
}
