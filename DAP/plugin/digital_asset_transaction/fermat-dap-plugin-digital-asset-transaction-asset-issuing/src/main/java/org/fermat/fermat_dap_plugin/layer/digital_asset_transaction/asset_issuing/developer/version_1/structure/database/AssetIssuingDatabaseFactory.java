package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database;

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
 * The Class  <code>com.bitdubai.fermat_dap_api.layer.transaction.asset_issuing.developer.bitdubai.version_1.database.Asset IssuingtransactionDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 * <p/>
 * Created by Manuel Perez - (darkpriestrelative@gmail.com) on 08/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AssetIssuingDatabaseFactory {

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
    public AssetIssuingDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
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
            DatabaseTableFactory issuingTable;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Digital Asset Transaction table.
             */
            issuingTable = databaseFactory.newTableFactory(ownerId, AssetIssuingDatabaseConstants.ASSET_ISSUING_TABLE_NAME);

            issuingTable.addColumn(AssetIssuingDatabaseConstants.ASSET_ISSUING_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            issuingTable.addColumn(AssetIssuingDatabaseConstants.ASSET_ISSUING_ASSETS_TO_GENERATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            issuingTable.addColumn(AssetIssuingDatabaseConstants.ASSET_ISSUING_ASSETS_COMPLETED_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            issuingTable.addColumn(AssetIssuingDatabaseConstants.ASSET_ISSUING_ASSETS_PROCESSED_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            issuingTable.addColumn(AssetIssuingDatabaseConstants.ASSET_ISSUING_NETWORK_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            issuingTable.addColumn(AssetIssuingDatabaseConstants.ASSET_ISSUING_BTC_WALLET_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            issuingTable.addColumn(AssetIssuingDatabaseConstants.ASSET_ISSUING_ISSUER_WALLET_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            issuingTable.addColumn(AssetIssuingDatabaseConstants.ASSET_ISSUING_ISSUING_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            issuingTable.addColumn(AssetIssuingDatabaseConstants.ASSET_ISSUING_CREATION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            issuingTable.addColumn(AssetIssuingDatabaseConstants.ASSET_ISSUING_PROCESSING_COLUMN_NAME, DatabaseDataType.STRING, 15, Boolean.FALSE);

            issuingTable.addIndex(AssetIssuingDatabaseConstants.ASSET_ISSUING_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, issuingTable);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "Creating " + AssetIssuingDatabaseConstants.ASSET_ISSUING_TABLE_NAME + " table", "Exception not handled by the plugin, There is a problem and I cannot create the table.");
            }

            DatabaseTableFactory eventsRecorderTable = databaseFactory.newTableFactory(ownerId, AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_TABLE_NAME);

            eventsRecorderTable.addColumn(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_ID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.TRUE);
            eventsRecorderTable.addColumn(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_EVENT_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_SOURCE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);

            eventsRecorderTable.addIndex(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN_NAME);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, eventsRecorderTable);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "Creating " + AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_TABLE_NAME + " table", "Exception not handled by the plugin, There is a problem and I cannot create the table.");
            }

            DatabaseTableFactory metadataTable = databaseFactory.newTableFactory(ownerId, AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_TABLE);

            metadataTable.addColumn(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_ID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.TRUE);
            metadataTable.addColumn(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_OUTGOING_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            metadataTable.addColumn(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_GENESIS_TRANSACTION_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            metadataTable.addColumn(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_ISSUING_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            metadataTable.addColumn(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_CREATION_TIME_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);

            metadataTable.addIndex(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_ID_COLUMN_NAME);
            try {
                //Create the table
                databaseFactory.createTable(ownerId, metadataTable);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "Creating " + AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_TABLE + " table", "Exception not handled by the plugin, There is a problem and I cannot create the table.");
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
