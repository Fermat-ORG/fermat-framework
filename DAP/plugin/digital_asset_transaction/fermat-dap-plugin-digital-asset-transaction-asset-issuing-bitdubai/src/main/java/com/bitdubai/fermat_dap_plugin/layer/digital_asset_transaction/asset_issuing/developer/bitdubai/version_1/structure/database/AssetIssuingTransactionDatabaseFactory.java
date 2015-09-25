package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database;

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
 *  The Class  <code>com.bitdubai.fermat_dap_api.layer.transaction.asset_issuing.developer.bitdubai.version_1.database.Asset IssuingtransactionDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Manuel Perez - (darkpriestrelative@gmail.com) on 08/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AssetIssuingTransactionDatabaseFactory implements DealsWithPluginDatabaseSystem {

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
    public AssetIssuingTransactionDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
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
             * Create Digital Asset Transaction table.
             */
            table = databaseFactory.newTableFactory(ownerId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_TABLE_NAME);

            table.addColumn(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_LOCAL_STORAGE_PATH_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_TO_GENERATE_COLUMN_NAME, DatabaseDataType.INTEGER, 4, Boolean.FALSE);
            table.addColumn(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_GENERATED_COLUMN_NAME, DatabaseDataType.INTEGER, 4, Boolean.FALSE);
            table.addColumn(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME, DatabaseDataType.STRING,100, Boolean.FALSE);

            table.addIndex(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "Creating "+AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_TABLE_NAME+" table", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            DatabaseTableFactory transitionProtocolTable = databaseFactory.newTableFactory(ownerId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_TRANSITION_PROTOCOL_STATUS_TABLE_NAME);

            transitionProtocolTable.addColumn(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_TRANSITION_PROTOCOL_STATUS_TABLE_PROTOCOL_STATUS, DatabaseDataType.STRING,100, Boolean.TRUE);
            transitionProtocolTable.addColumn(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_TRANSITION_PROTOCOL_STATUS_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER,34, Boolean.FALSE);
            transitionProtocolTable.addColumn(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_TRANSITION_PROTOCOL_STATUS_OCCURRENCES_COLUMN_NAME,DatabaseDataType.INTEGER,4, Boolean.FALSE);

            transitionProtocolTable.addIndex(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_TRANSITION_PROTOCOL_STATUS_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, transitionProtocolTable);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "Creating "+AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_TRANSITION_PROTOCOL_STATUS_TABLE_NAME+" table", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            DatabaseTableFactory assetIssuingTable = databaseFactory.newTableFactory(ownerId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME);

            assetIssuingTable.addColumn(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.STRING,100,Boolean.TRUE);
            assetIssuingTable.addColumn(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_TRANSACTION_COLUMN_NAME, DatabaseDataType.STRING,100,Boolean.FALSE);
            assetIssuingTable.addColumn(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_ADDRESS_COLUMN_NAME, DatabaseDataType.STRING,100,Boolean.FALSE);
            assetIssuingTable.addColumn(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME,DatabaseDataType.STRING,100,Boolean.FALSE);
            assetIssuingTable.addColumn(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_PROTOCOL_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            assetIssuingTable.addColumn(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_DIGITAL_ASSET_HASH_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            assetIssuingTable.addColumn(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            assetIssuingTable.addIndex(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, assetIssuingTable);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "Creating "+AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME+" table", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
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
