package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;

import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public class AssetSellerDatabaseFactory implements DealsWithPluginDatabaseSystem {
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
    public AssetSellerDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Create the database
     *
     * @param ownerId the owner id
     * @return Database
     * @throws CantCreateDatabaseException
     */
    public Database createDatabase(UUID ownerId) throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, AssetSellerDatabaseConstants.ASSET_SELLER_DATABASE);
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
             * Create Asset Seller table.
             */
            table = databaseFactory.newTableFactory(ownerId, AssetSellerDatabaseConstants.ASSET_SELLER_TABLE_NAME);

            table.addColumn(AssetSellerDatabaseConstants.ASSET_SELLER_ENTRY_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(AssetSellerDatabaseConstants.ASSET_SELLER_GENESIS_TRANSACTION_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetSellerDatabaseConstants.ASSET_SELLER_NETWORK_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetSellerDatabaseConstants.ASSET_SELLER_BUYER_PUBLICKEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetSellerDatabaseConstants.ASSET_SELLER_SELL_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetSellerDatabaseConstants.ASSET_SELLER_NEGOTIATION_REFERENCE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetSellerDatabaseConstants.ASSET_SELLER_TRANSACTION_COLUMN_NAME, DatabaseDataType.STRING, Validate.MAX_SIZE_STRING_COLUMN, Boolean.FALSE);
            table.addColumn(AssetSellerDatabaseConstants.ASSET_SELLER_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);

            table.addIndex(AssetSellerDatabaseConstants.ASSET_SELLER_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create Negotiation table.
             */
            table = databaseFactory.newTableFactory(ownerId, AssetSellerDatabaseConstants.ASSET_SELLER_NEGOTIATION_TABLE_NAME);

            table.addColumn(AssetSellerDatabaseConstants.ASSET_SELLER_NEGOTIATION_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(AssetSellerDatabaseConstants.ASSET_SELLER_NEGOTIATION_OBJECT_XML_COLUMN_NAME, DatabaseDataType.STRING, Validate.MAX_SIZE_STRING_COLUMN, Boolean.FALSE);
            table.addColumn(AssetSellerDatabaseConstants.ASSET_SELLER_NEGOTIATION_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(AssetSellerDatabaseConstants.ASSET_SELLER_NEGOTIATION_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addIndex(AssetSellerDatabaseConstants.ASSET_SELLER_NEGOTIATION_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            DatabaseTableFactory eventsRecorderTable = databaseFactory.newTableFactory(ownerId, AssetSellerDatabaseConstants.ASSET_SELLER_EVENTS_RECORDED_TABLE_NAME);

            eventsRecorderTable.addColumn(AssetSellerDatabaseConstants.ASSET_SELLER_EVENTS_RECORDED_ID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.TRUE);
            eventsRecorderTable.addColumn(AssetSellerDatabaseConstants.ASSET_SELLER_EVENTS_RECORDED_EVENT_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetSellerDatabaseConstants.ASSET_SELLER_EVENTS_RECORDED_SOURCE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetSellerDatabaseConstants.ASSET_SELLER_EVENTS_RECORDED_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetSellerDatabaseConstants.ASSET_SELLER_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);

            eventsRecorderTable.addIndex(AssetSellerDatabaseConstants.ASSET_SELLER_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, eventsRecorderTable);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "Creating " + AssetSellerDatabaseConstants.ASSET_SELLER_EVENTS_RECORDED_TABLE_NAME + " table", "Exception not handled by the plugin, There is a problem and I cannot create the table.");
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
